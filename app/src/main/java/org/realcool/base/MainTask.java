package org.realcool.base;

import android.util.Log;

import java.util.LinkedList;

public class MainTask {
    private final LinkedList<BaseTask> children;
    private final TaskLine taskLine;
    private int index;
    private int closeChild;
    public MainTask() {
        children = new LinkedList<>();
        taskLine = new TaskLine(this);
        new Thread(taskLine).start();
    }

    public void run(){
        int size = children.size();
        Log.e("size", "size:"+size +",closeChild:"+ closeChild);
        if (size == closeChild) {
            taskLine.stop();
            taskLine.waitRun();
        }
        if (size > index){
            BaseTask task = children.get(index);
            if (task.isOpen()) task.run();
            else closeChild++;
            index++;
        } else {
            index = 0;
            closeChild = 0;
        }
        run();
    }

    public void start(){
        taskLine.start();
    }

    public void stop(){
        taskLine.stop();
    }

    public void add(int index, BaseTask task) {
        task.taskLine = taskLine;
        children.add(index, task);
        if (children.size() == 1 && taskLine.isRunning()) taskLine.start();
    }

    public void add(BaseTask task) {
        task.taskLine = taskLine;
        children.addLast(task);
        if (children.size() == 1 && taskLine.isRunning()) taskLine.start();
    }
}
