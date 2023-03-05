package org.realcool.base.impl;

import org.realcool.base.MainTask;

public class DaYeTask extends MainTask {

    public DaYeTask(){
        super();
        setType(MainTask.DAYE_TYPE);
        EnterGameTask task = new EnterGameTask();
        task.setDelay(2000);
        add(task);
    }
}
