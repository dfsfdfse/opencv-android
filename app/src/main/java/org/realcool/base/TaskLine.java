package org.realcool.base;

import android.util.Log;

import org.realcool.bean.Page;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskLine implements Runnable{

    private final ReentrantLock lock;
    private final Condition pos;
    private boolean running;
    private final MainTask task;
    private Page currentPage;
    private List<Page> pageList;

    public TaskLine(MainTask task) {
        super();
        this.task = task;
        this.lock = new ReentrantLock();
        this.pos = lock.newCondition();
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        Log.e(getClass().getName(), "开始运行run");
        task.run();
    }

    public void waitRun(){
        lock.lock();
        try {
            while (!running) {
                Log.e(getClass().getName(), "等待中...");
                pos.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void stopWait(){
        running = false;
        waitRun();
    }

    /**
     * 当前正在运行的线程运行完成后 下一个任务即停止
     */
    public void stop(){
        running = false;
    }

    public void start(){
        lock.lock();
        try {
            running = true;
            pos.signal();
        } finally {
            lock.unlock();
        }
    }
}