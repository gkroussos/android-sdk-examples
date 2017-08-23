package com.indooratlas.android.sdk.examples.wayfinding;

/**
 * Created by Topias on 23.8.2017.
 */

public class Edge {
    private Node nodeA;
    private Node nodeB;
    private float weight;

    Edge(Node a, Node b, float w) {
        this.nodeA = a;
        this.nodeB = b;
        this.weight = w;
    }

    public Node getNodeA() {
        return nodeA;
    }

    public void setNodeA(Node nodeA) {
        this.nodeA = nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }

    public void setNodeB(Node nodeB) {
        this.nodeB = nodeB;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
