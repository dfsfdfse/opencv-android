package org.realcool.base;

import org.realcool.base.impl.CaiJiTask;
import org.realcool.base.impl.DaYeTask;

public class Task extends MainTask{

    public Task() {
        super();
        setStop(true);
        setLoop(true);
        DaYeTask daYeTask = new DaYeTask();
        CaiJiTask caiJiTask = new CaiJiTask();
        add(daYeTask);
        add(caiJiTask);
        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();
    }
}