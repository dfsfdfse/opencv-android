package org.realcool.base.impl;

import android.util.Log;

import org.realcool.base.MainTask;

public class CaiJiTask extends MainTask {
    public CaiJiTask() {
        super();
        setType(MainTask.CAIJI_TYPE);
    }

    @Override
    public void exec() {
        Log.e("cj","开始采集任务");
        super.exec();
        Log.e("cj","采集任务执行完成");
    }

    @Override
    public void stop() {
        super.stop();
        Log.e("cj","采集任务停止");
    }
}
