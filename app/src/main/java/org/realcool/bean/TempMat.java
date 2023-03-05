package org.realcool.bean;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

public class TempMat {
    private Mat original;

    private Mat temp;

    public TempMat(Mat original, Mat temp) {
        this.original = original;
        this.temp = temp;
    }

    public Mat getOriginal() {
        return original;
    }

    public void setOriginal(Mat original) {
        this.original = original;
    }

    public Mat getTemp() {
        return temp;
    }

    public void setTemp(Mat temp) {
        this.temp = temp;
    }
}
