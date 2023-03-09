package org.realcool.base;

import android.util.Log;

import org.realcool.bean.Page;
import org.realcool.utils.IdUtils;

import java.util.LinkedList;

public abstract class BaseTask {

    protected final String id;

    private BaseTask parent;

    private boolean working;

    protected long delay;

    protected boolean leaf;

    protected boolean loop;

    private boolean open;

    private boolean waitOther;

    private BaseTask rootParent;

    protected TaskLine taskLine;

    private final LinkedList<OnFinished> finished;

    public BaseTask() {
        this.id = IdUtils.genId();
        this.open = true;
        this.finished = new LinkedList<>();
    }

    public void setCurrentPage(Page currentPage){
        getTaskLine().setCurrentPage(currentPage);
    }

    public Page getCurrentPage(){
        return getTaskLine().getCurrentPage();
    }

    public LinkedList<OnFinished> getFinished() {
        return finished;
    }

    public void setWaitOther(boolean waitOther) {
        this.waitOther = waitOther;
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

    public BaseTask addOnFinished(OnFinished finish) {
        this.finished.addLast(finish);
        return this;
    }

    protected void run() {
        getTaskLine().waitRun();
        working = true;
        waitExec();
        exec();
        if (finished.size() > 0){
            if (waitOther){
                Log.e(getClass().getName(), "等待指令接受结果");
                getTaskLine().stopWait();
            }
            for (OnFinished onFinished : finished) {
                onFinished.finished(this);
            }
        }
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
        return taskLine != null ? taskLine : (taskLine = getRootParent().taskLine);
    }

    public BaseTask getRootParent(){
        return rootParent != null ? rootParent : (rootParent = getRootParent(this));
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
        Log.e(getClass().getName(), "发送停止指令");
        getTaskLine().stop();
    }

    public interface OnFinished{
        void finished(BaseTask task);
    }
}
