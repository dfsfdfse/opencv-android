package org.realcool.bean;

import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

import java.util.LinkedList;

public class MatchPoint {
    public static final int TYPE_ORB = 1;

    public static final int TYPE_SIFT = 2;
    //比对的时候 ndr ratio值
    public static final float NDR_RATIO = 0.45f;
    //特征点符合数
    public static final int MATCH_NUM = 5;
    //是否图片符合
    private boolean match;

    private Mat temp;

    private Mat origin;

    private MatOfKeyPoint tempK;

    private MatOfKeyPoint originK;

    private LinkedList<DMatch> goodMatch;

    private int detectType;

    public MatchPoint() {
        this.temp = new Mat();
        this.origin = new Mat();
        this.tempK = new MatOfKeyPoint();
        this.originK = new MatOfKeyPoint();
        this.goodMatch = new LinkedList<>();
        this.detectType = TYPE_SIFT;
    }

    public MatchPoint(boolean match, Mat temp, Mat origin, MatOfKeyPoint tempK, MatOfKeyPoint originK) {
        this.match = match;
        this.temp = temp;
        this.origin = origin;
        this.tempK = tempK;
        this.originK = originK;
    }

    public int getDetectType() {
        return detectType;
    }

    public void setDetectType(int detectType) {
        this.detectType = detectType;
    }

    public LinkedList<DMatch> getGoodMatch() {
        return goodMatch;
    }

    public void setGoodMatch(LinkedList<DMatch> goodMatch) {
        this.goodMatch = goodMatch;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public Mat getTemp() {
        return temp;
    }

    public void setTemp(Mat temp) {
        this.temp = temp;
    }

    public Mat getOrigin() {
        return origin;
    }

    public void setOrigin(Mat origin) {
        this.origin = origin;
    }

    public MatOfKeyPoint getTempK() {
        return tempK;
    }

    public void setTempK(MatOfKeyPoint tempK) {
        this.tempK = tempK;
    }

    public MatOfKeyPoint getOriginK() {
        return originK;
    }

    public void setOriginK(MatOfKeyPoint originK) {
        this.originK = originK;
    }
}
