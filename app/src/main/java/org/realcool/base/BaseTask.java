package org.realcool.base;

import android.util.Log;

import org.realcool.base.msg.BaseMsg;
import org.realcool.service.event.TaskEvent;
import org.realcool.utils.IdUtils;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BaseTask {
    protected final String id;

    protected BaseTask parent;

    protected final LinkedList<BaseTask> children;

    private Integer type;

    private boolean working;

    private long delay;

    private boolean leaf;

    private boolean stop;

    private boolean result;

    protected boolean loop;

    protected Listener listener;

    private final ReentrantLock lock;

    private final Condition notEmpty;

    private final Condition notResult;

    public BaseTask() {
        this.id = IdUtils.genId();
        this.children = new LinkedList<>();
        this.working = true;
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notResult = lock.newCondition();
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public Listener getListener() {
        return listener;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getId() {
        return id;
    }

    public BaseTask getParent() {
        return parent;
    }

    public LinkedList<BaseTask> getChildren() {
        return children;
    }

    public BaseTask setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    //开启自循环
    protected void headLoop(){
        if (this instanceof MainTask) {
            ((MainTask)(this)).setExeIndex(0);
            for (int i = 0; i < children.size(); i++) {
                BaseTask task = children.get(i);
                if (task instanceof MainTask) {
                    ((MainTask) task).headLoop();
                }
            }
        }
    }

    public BaseTask getNext() {
        int index = 0;
        for (int i = 0; i < parent.children.size(); i++) {
            BaseTask item = parent.children.get(i);
            if (Objects.equals(item.id, id)) {
                index += i;
                break;
            }
        }
        return parent.children.size() > index ? parent.children.get(index) : null;
    }

    public void notifyStart() {
        if (working){
            try {
                lock.lock();
                stop = false;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void notifyResult() {
        try {
            lock.lock();
            result = true;
            notResult.signal();
        } finally {
            lock.unlock();
        }
    }

    public void start() {
        working = true;
        waitStart();
        long delay = getDelay();
        if (delay != 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        exec();
        if (leaf) {
            waitResult();
            Log.e("result", "true");
        }
        if (loop) {
            headLoop();
            working = false;
            start();
        }
    }

    public void waitStart(){
        lock.lock();
        try {
            while (stop) {
                Log.e(getClass().getName(),"waitStart");
                notEmpty.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void waitResult(){
        lock.lock();
        try {
            while (!result) {
                Log.e("result", "false");
                notResult.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public abstract void exec();

    public interface Listener {
        //完成时回调
        void onFinished(BaseMsg t);
    }
}
