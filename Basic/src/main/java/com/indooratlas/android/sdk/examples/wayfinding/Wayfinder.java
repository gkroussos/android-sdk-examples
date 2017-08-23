package com.indooratlas.android.sdk.examples.wayfinding;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import no.wtw.android.dijkstra.DijkstraAlgorithm;
import no.wtw.android.dijkstra.exception.PathNotFoundException;
import no.wtw.android.dijkstra.model.Graph;
import no.wtw.android.dijkstra.model.Vertex;

/**
 * Created by Topias on 23.8.2017.
 */

public class Wayfinder {
    private List<no.wtw.android.dijkstra.model.Edge> edges;
    private List<Node> nodes;
    private Graph graph;
    private DijkstraAlgorithm algorithm;
    private boolean running = false;
    private AsyncTask task = null;

    Wayfinder(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = convertEdges(edges);
        this.graph = new Graph(this.edges);
        this.algorithm = new DijkstraAlgorithm(this.graph);
    }

    public boolean isRunning() {
        return this.running;
    }

    private List<Node> getPath(Node src, Node dst) throws PathNotFoundException {
        List<Vertex> path = this.algorithm.execute(wrapNodeToVertex(src)).getPath(wrapNodeToVertex(dst));
        ArrayList<Node> nodePath = new ArrayList<>();

        for(Vertex<Node> v : path) {
            nodePath.add(v.getPayload());
        }
        return nodePath;
    }

    public void updatePathAsync(Node src, Node dst, WayfindingActivity activity) {
        if(!this.running) {
            this.running = true;
            this.task = new PathGenerationTask(activity).execute(src, dst);
        }
    }

    public boolean cancelRunningTask() {
        if (this.task != null && this.task.isCancelled() == false) {
            if (this.task.cancel(true)) {
                this.running = false;
                return true;
            }
        }
        return false;
    }

    private List<no.wtw.android.dijkstra.model.Edge> convertEdges(List<Edge> edges) {
        ArrayList<no.wtw.android.dijkstra.model.Edge> wEdges = new ArrayList<>();

        for(Edge e : edges) {
            wEdges.add(convertEdge(e));
        }

        return wEdges;
    }

    private no.wtw.android.dijkstra.model.Edge convertEdge(Edge edge) {
        Vertex<Node> src = new Vertex<>(edge.getNodeA());
        Vertex<Node> dst = new Vertex<>(edge.getNodeB());
        int weight = (int)(100*edge.getWeight());

        return new no.wtw.android.dijkstra.model.Edge(src, dst, weight);
    }

    private Vertex<Node> wrapNodeToVertex(Node node) {
        return new Vertex<>(node);
    }


    public Node getNearestNode(double[] latLon) {
        if (nodes.size() == 0)
            return null;
        if (nodes.size() == 1)
            return nodes.get(0);

        Node nearestNode = nodes.get(0);
        double dist = wgsDistance(latLon, nearestNode.getLatLon());

        for(Node n : nodes) {
            double currDist = wgsDistance(latLon, n.getLatLon());
            if (currDist < dist) {
                dist = currDist;
                nearestNode = n;
            }
        }

        return nearestNode;
    }

    /**
     * Calculates distance between estimates in meters. Uses
     * Haversine formula, should provide ~0.5% accuracy
     * @return Distance in meters
     */
    private double wgsDistance(double[] wgs1, double[] wgs2) {
        double latDelta  = Math.toRadians((wgs2[0] - wgs1[0]));
        double lngDelta = Math.toRadians((wgs2[1] - wgs1[1]));
        double lat0Rad = Math.toRadians(wgs1[0]);
        double lat1Rad   = Math.toRadians(wgs2[0]);
        double a = Math.pow(Math.sin(latDelta / 2), 2) + Math.cos(lat0Rad)
                * Math.cos(lat1Rad) * Math.pow(Math.sin(lngDelta / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6378137 * c;
    }

    private class PathGenerationTask extends AsyncTask<Node, String, List<Node>> {
        WayfindingActivity activity;

        PathGenerationTask(WayfindingActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<Node> doInBackground(Node... nodes) {
            Node src = nodes[0];
            Node dst = nodes[1];

            List<Node> path = null;
            try {
                path = getPath(src, dst);
            } catch (PathNotFoundException e) {
                Log.e("Wayfinder", "Path not found");
            }

            return path;
        }

        protected void onPostExecute(List<Node> result) {
            activity.setPath(result);
            running = false;
        }
    }
}
