package org.realcool.base.msg;

import com.benjaminwan.ocrlibrary.Point;

public class PointMsg extends BaseMsg{
    private final double ax;
    private final double ay;
    private final double bx;
    private final double by;
    private final double cx;
    private final double cy;
    private final double dx;
    private final double dy;

    public PointMsg(double[] pointA, double[] pointB, double[] pointC, double[] pointD) {
        super();
        ax = pointA[0];
        ay = pointA[1];
        bx = pointB[0];
        by = pointB[1];
        cx = pointC[0];
        cy = pointC[1];
        dx = pointD[0];
        dy = pointD[1];
    }

    public PointMsg(Point a,Point b,Point c,Point d) {
        super();
        ax = a.getX();
        ay = a.getY();
        bx = b.getX();
        by = b.getY();
        cx = c.getX();
        cy = c.getY();
        dx = d.getX();
        dy = d.getY();
    }

    public int getX() {
        return (int) Math.round((ax + bx + cx + dx) / 4);
    }

    public int getY() {
        return (int) Math.round((ay + by + cy + dy) / 4);
    }

    public int getRX(int x) {
        return (int) Math.round((ax + bx + cx + dx) / 4) + x;
    }

    public int getBY(int y) {
        return (int) Math.round((ay + by + cy + dy) / 4) + y;
    }
}
