package org.realcool.base.min;

import org.realcool.base.CommandTask;

public class SwipeTask extends CommandTask {
    private int sx;

    private int sy;

    private int ex;

    private int ey;

    private long duration;

    public int getSx() {
        return sx;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }

    public int getSy() {
        return sy;
    }

    public void setSy(int sy) {
        this.sy = sy;
    }

    public int getEx() {
        return ex;
    }

    public void setEx(int ex) {
        this.ex = ex;
    }

    public int getEy() {
        return ey;
    }

    public void setEy(int ey) {
        this.ey = ey;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public SwipeTask() {
        super();
    }


    /**
     *
     * @param sx 滑动初始坐标x
     * @param sy 滑动初始坐标y
     * @param ex 滑动结束坐标x
     * @param ey 滑动结束坐标x
     * @param duration 滑动持续时间
     */
    public SwipeTask(int sx, int sy, int ex, int ey, long duration) {
        super();
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.duration = duration;
    }
    /**
     *
     * @param sx 滑动初始坐标x
     * @param sy 滑动初始坐标y
     * @param ex 滑动结束坐标x
     * @param ey 滑动结束坐标x
     * @param duration 滑动持续时间
     * @param delay 滑动前延迟时间
     */
    public SwipeTask(int sx, int sy, int ex, int ey, long duration, long delay) {
        super();
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.duration = duration;
        this.delay = delay;
    }
}
