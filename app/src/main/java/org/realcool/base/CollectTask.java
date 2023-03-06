package org.realcool.base;

import android.util.Log;

import java.util.LinkedList;

public abstract class CollectTask extends BaseTask {
    public static final Integer DAYE_TYPE = 1;
    public static final Integer CAIJI_TYPE = 2;

    protected final LinkedList<BaseTask> children;

    protected int index;

    public CollectTask() {
        super();
        children = new LinkedList<>();
    }

    @Override
    public void exec() {
        if (children.size() > index){
            children.get(index).run();
            index++;
            exec();
        } else {
            index = 0;
        }
    }

    public void add(int index, BaseTask task) {
        task.setParent(this);
        children.add(index, task);
    }

    public void add(BaseTask task) {
        task.setParent(this);
        children.addLast(task);
    }

    public void remove(BaseTask task) {
        children.remove(task);
    }
}
