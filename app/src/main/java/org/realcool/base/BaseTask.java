package org.realcool.base;

import android.util.Log;

import org.realcool.utils.IdUtils;

public abstract class BaseTask {

    protected final String id;

    private BaseTask parent;

    private boolean working;

    protected long delay;

    protected boolean leaf;

    protected boolean loop;

    private boolean open;

    protected TaskLine taskLine;

    private OnFinished finished;

    public void setOnFinished(OnFinished finished) {
        this.finished = finished;
    }

    public BaseTask() {
        this.id = IdUtils.genId();
        this.open = true;
    }

    public BaseTask getParent() {
        return parent;
    }

    public void setParent(BaseTask parent) {
        this.parent = parent;
    }

    /**
     * 是否开启开启
     * @return
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * 设置是否开启任务
     * @param open
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * 是否循环执行
     * @return
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * 获取本任务是否是执行指令任务(CommandTask)
     * @return
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * 获取本任务是否在运行
     * @return
     */
    public boolean isWorking() {
        return working;
    }

    public long getDelay() {
        return delay;
    }

    /**
     * 获取任务线的id
     * @return
     */
    public String getId() {
        return id;
    }

    protected void run() {
        Log.e(getClass().getName(), "run");
        getTaskLine().waitRun();
        working = true;
        waitExec();
        Log.e(getClass().getName(), "开始执行");
        exec();
        if (finished != null) finished.finished();
        Log.e(getClass().getName(), "执行完毕");
        if (loop && open) run();
        working = false;
    }

    protected void waitExec() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void exec();

    /**
     * 让本任务的任务线(taskLine)运行
     */
    public void startLine() {
        getTaskLine().start();
    }

    public TaskLine getTaskLine(){
        return getRootParent().taskLine;
    }

    public BaseTask getRootParent(){
        return getRootParent(this);
    }

    private BaseTask getRootParent(BaseTask note){
        BaseTask p = note.getParent();
        if (p != null) return getRootParent(p);
        else return note;
    }

    /**
     * 让本任务的任务线停止运行
     */
    public void stopLine() {
        getTaskLine().stop();
    }

    public interface OnFinished{
        void finished();
    }
}
