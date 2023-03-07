package org.realcool.base;

import android.util.Log;

import java.util.LinkedList;

public abstract class CollectTask extends BaseTask {
    public static final Integer DAYE_TYPE = 1;
    public static final Integer CAIJI_TYPE = 2;

    protected final LinkedList<BaseTask> children;

    protected int index;

    private int closeChild;

    public CollectTask() {
        super();
        children = new LinkedList<>();
    }

    @Override
    public void exec() {
        int size = children.size();
        if (size > index) {
            BaseTask task = children.get(index);
            if (task.isOpen()) task.run();
            else closeChild++;
            index++;
        } else {
            closeChild = 0;
            index = 0;
        }
        if (size == closeChild) {
            setOpen(false);
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
