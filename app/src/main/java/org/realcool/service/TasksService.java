package org.realcool.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.realcool.base.MainTask;
import org.realcool.base.impl.DaYeTask;
import org.realcool.service.event.TaskEvent;

public class TasksService extends Service {

    private MainTask task;
    private DaYeTask daYeTask;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        task = new MainTask();
        daYeTask = new DaYeTask();
        daYeTask.setOpen(false);
        task.add(daYeTask);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(TaskEvent event) {
        String exe = "";
        switch (event.getType()) {
            case TaskEvent.ADD_CAIJI:
                exe = "开启采集";break;
            case TaskEvent.ADD_DAYE:
                exe = "开启打野";
                daYeTask.setOpen(true);break;
            case TaskEvent.STOP_CAIJI:
                exe = "关闭采集";break;
            case TaskEvent.STOP_DAYE:
                exe = "关闭打野";
                daYeTask.setOpen(false);break;
            case TaskEvent.START:
                exe = "开始任务";
                task.start();break;
            case TaskEvent.STOP:
                exe = "停止任务";
                task.stop();break;
        }
        Log.e(getClass().getName(), "执行指令--" + exe);
    }

}
