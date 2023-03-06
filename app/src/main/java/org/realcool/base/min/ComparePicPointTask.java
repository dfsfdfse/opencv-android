package org.realcool.base.min;

import android.graphics.Bitmap;

import org.realcool.base.CommandTask;

public class ComparePicPointTask extends CommandTask {
    private int img;

    private Bitmap screen;

    public ComparePicPointTask() {
        super();
    }

    public ComparePicPointTask(int img) {
        super();
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Bitmap getScreen() {
        return screen;
    }

    public void setScreen(Bitmap screen) {
        this.screen = screen;
    }
}
