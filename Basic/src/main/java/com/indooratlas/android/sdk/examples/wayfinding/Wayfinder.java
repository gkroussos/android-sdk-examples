package com.indooratlas.android.sdk.examples.wayfinding;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Graph graph;
    private DijkstraAlgorithm algorithm;

    Wayfinder(List<Edge> edges) {
        this.edges = convertEdges(edges);
        this.graph = new Graph(this.edges);
        this.algorithm = new DijkstraAlgorithm(this.graph);
    }

    public List<Node> getPath(Node src, Node dst) throws PathNotFoundException {
        List<Vertex> path = this.algorithm.execute(wrapNodeToVertex(src)).getPath(wrapNodeToVertex(dst));
        ArrayList<Node> nodePath = new ArrayList<>();

        for(Vertex<Node> v : path) {
            nodePath.add(v.getPayload());
        }
        return nodePath;
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
}
