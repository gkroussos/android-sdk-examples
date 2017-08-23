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
}
