package org.realcool.base.min;

import org.realcool.base.MinTask;

public class TapTask extends MinTask {
    public static final int PRESS_LONG = 500;

    public static final int TAP_SHORT = 50;

    private int x;

    private int y;

    private long duration;

    public TapTask() {
        super();
        this.duration = TAP_SHORT;
    }

    public TapTask(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.duration = TAP_SHORT;
    }

    public TapTask(int x, int y, long duration) {
        super();
        this.x = x;
        this.y = y;
        this.duration = duration;
    }

    public TapTask(int x, int y, long duration, long delay) {
        super();
        this.x = x;
        this.y = y;
        this.duration = duration;
        setDelay(delay);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
