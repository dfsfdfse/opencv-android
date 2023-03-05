package org.realcool.base;

import android.util.Log;

import java.util.Objects;

public abstract class MainTask extends BaseTask {
    public static final Integer DAYE_TYPE = 1;
    public static final Integer CAIJI_TYPE = 2;

    private int exeIndex;

    public MainTask() {
        super();
        this.exeIndex = 0;
    }

    public void add(int index, BaseTask task) {
        this.children.add(index, task);
    }

    public void add(BaseTask task) {
        this.children.addLast(task);
    }

    public void remove(int index){
        this.children.remove(index);
    }

    public void remove(BaseTask task){
        this.children.remove(task);
    }

    public int getExeIndex() {
        return exeIndex;
    }

    public void setExeIndex(int exeIndex) {
        this.exeIndex = exeIndex;
    }

    @Override
    public void exec() {
        if (children.size() > exeIndex) {
            BaseTask baseTask = children.get(exeIndex);
            baseTask.start();
            Log.e(baseTask.getClass().getName(), "执行完成指令");
            exeIndex++;
            exec();
        }
    }

    public void remove(String id){
        for (int i = 0; i < children.size(); i++) {
            BaseTask task = children.get(i);
            if (Objects.equals(task.getId(), id)){
                children.remove(i);
                break;
            }
        }
    }

    public BaseTask getByType(Integer type) {
        for (int i = 0; i < children.size(); i++) {
            BaseTask task = children.get(i);
            if (Objects.equals(type, task.getType())) {
                return task;
            }
        }
        return null;
    }
    @Override
    public void notifyStart(){
        super.notifyStart();
        for (int i = 0; i < children.size(); i++) {
            BaseTask task = children.get(i);
            if (!task.isStop() && task.isWorking()) {
                task.notifyStart();
                break;
            }
        }
    }

}
