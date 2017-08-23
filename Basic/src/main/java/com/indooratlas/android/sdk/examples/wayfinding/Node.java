package com.indooratlas.android.sdk.examples.wayfinding;

/**
 * Created by Topias on 23.8.2017.
 */

public class Node {
    private double x;
    private double y;

    Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Node))
            return false;
        Node n = (Node)other;
        if (n.getX() == x && n.getY() == y) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new Double(x).hashCode() ^ new Double(y).hashCode();
    }
}
