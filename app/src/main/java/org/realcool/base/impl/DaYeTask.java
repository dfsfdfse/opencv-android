package org.realcool.base.impl;

import android.util.Log;

import org.realcool.base.MainTask;
import org.realcool.base.min.TapTask;

public class DaYeTask extends MainTask {
    public DaYeTask() {
        super();
        setType(MainTask.DAYE_TYPE);
        addMinTask(new TapTask(206, 2058, 10, true, 2000));
    }

    @Override
    public void exec() {
        Log.e("dy","开始打野任务");
        super.exec();
        Log.e("dy","打野任务执行完成");
    }

    @Override
    public void stop() {
        super.stop();
        Log.e("dy","打野任务停止");
    }
}
