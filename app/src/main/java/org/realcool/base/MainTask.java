package org.realcool.base;

import java.util.LinkedList;

public class MainTask {
    private final LinkedList<BaseTask> children;
    private final TaskLine taskLine;
    private int index;
    public MainTask() {
        children = new LinkedList<>();
        taskLine = new TaskLine(this);
        new Thread(taskLine).start();
    }

    public void run(){
        if (children.size() == 0) taskLine.stop();
        taskLine.waitRun();
        if (children.size() > index){
            children.get(index).run();
            index++;
        } else {
            index = 0;
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
