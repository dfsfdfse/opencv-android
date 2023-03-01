package org.realcool.bean;

import com.benjaminwan.ocrlibrary.Point;

import java.util.ArrayList;


/**
 * A----B
 * D----C
 */
public class SearchPoint {
    private double[] pointA;
    private double[] pointB;
    private double[] pointC;
    private double[] pointD;

    public SearchPoint(double[] pointA, double[] pointB, double[] pointC, double[] pointD) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
        this.pointD = pointD;
    }

    public SearchPoint(ArrayList<Point> points){
        this(points.get(0), points.get(1), points.get(2), points.get(3));
    }

    /**
     * 通过 OCR 检测到的数据和当前坐标系对应关系0-a,1-b,2-c,3-d
     * @param a 下标0
     * @param b 下标1
     * @param c 下标2
     * @param d 下标3
     */
    public SearchPoint(com.benjaminwan.ocrlibrary.Point a, com.benjaminwan.ocrlibrary.Point b, com.benjaminwan.ocrlibrary.Point c, com.benjaminwan.ocrlibrary.Point d){
        pointA = new double[2];
        pointA[0] = a.getX();
        pointA[1] = a.getY();

        pointB = new double[2];
        pointB[0] = b.getX();
        pointB[1] = b.getY();

        pointC = new double[2];
        pointC[0] = c.getX();
        pointC[1] = c.getY();

        pointD = new double[2];
        pointD[0] = d.getX();
        pointD[1] = d.getY();
    }

    @Override
    public String toString() {
        return "SearchPoint{" +
                "pointA=[" + (int)pointA[0] + "," + (int)pointA[1] + "]" +
                //", pointB=[" + (int)pointB[0] + "," + (int)pointB[1] + "]" +
                ", pointC=[" + (int)pointC[0] + "," + (int)pointC[1] + "]" +
                //", pointD=[" + (int)pointD[0] + "," + (int)pointD[1] + "]" +
                '}';
    }

    private double random(double x, double y){
        double c = Math.abs(x-y);
        return Math.random()*c - (c/2);
    }

    public float getClickX(){
        return (float)((pointA[0]+pointC[0])/2 + random(pointA[0], pointC[0]));
    }

    public float getClickY(){
        return (float)((pointA[1]+pointC[1])/2 + random(pointA[1], pointC[1]));
    }
}
