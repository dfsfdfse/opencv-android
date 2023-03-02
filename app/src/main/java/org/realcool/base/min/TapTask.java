package org.realcool.base.min;

import android.util.Log;

import org.realcool.base.MinTask;
import org.realcool.service.event.TaskEvent;

public class TapTask extends MinTask {
    private int x;

    private int y;

    private long duration;

    public TapTask() {
    }

    public TapTask(int x, int y, long duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
    }

    public TapTask(int x, int y, long duration, boolean newThread) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        setNewThread(newThread);
    }

    public TapTask(int x, int y, long duration, boolean newThread, long delay) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        setNewThread(newThread);
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

    @Override
    protected void doTask() {
        Log.e("tapTask","开始执行tap任务");
        TaskEvent.postAction(this);
    }
}
