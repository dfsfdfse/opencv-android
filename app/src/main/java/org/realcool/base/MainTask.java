package org.realcool.base;

import org.realcool.bean.Page;
import org.realcool.bean.PageLoader;

import java.util.LinkedList;
import java.util.List;

public class MainTask {
    private final LinkedList<BaseTask> children;
    private final TaskLine taskLine;
    private int index;
    private int closeChild;
    private Page currentPage;
    private List<Page> pageList;

    public MainTask() {
        children = new LinkedList<>();
        taskLine = new TaskLine(this);
        new Thread(taskLine).start();
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public void run(){
        int size = children.size();
        if (size > index){
            BaseTask task = children.get(index);
            if (task.isOpen()) task.run();
            else closeChild++;
            index++;
        } else {
            index = 0;
            closeChild = 0;
        }
        if (size == closeChild) {
            taskLine.stop();
            taskLine.waitRun();
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
