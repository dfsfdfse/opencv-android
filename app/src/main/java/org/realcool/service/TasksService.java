package org.realcool.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.realcool.base.MainTask;
import org.realcool.base.Task;
import org.realcool.service.event.TaskEvent;

public class TasksService extends Service {
    private Task task;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        task = new Task();
        //初始的时候不执行
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(TaskEvent event) {
        String exe = "";
        switch (event.getType()) {
            case TaskEvent.ADD_CAIJI:
                exe = "开启采集";
                ((MainTask)task.getByType(MainTask.CAIJI_TYPE)).setStop(false);break;
            case TaskEvent.ADD_DAYE:
                exe = "开启打野";
                ((MainTask)(task.getByType(MainTask.DAYE_TYPE))).setStop(false);break;
            case TaskEvent.STOP_CAIJI:
                exe = "关闭采集";
                task.getByType(MainTask.CAIJI_TYPE).setStop(true);break;
            case TaskEvent.STOP_DAYE:
                exe = "关闭打野";
                task.getByType(MainTask.DAYE_TYPE).setStop(true);break;
            case TaskEvent.START:
                exe = "开始任务";
                task.notifyStart();break;
            case TaskEvent.STOP:
                exe = "停止任务";
                //todo 停止正在working的任务 (目前不支持 对循环任务的停止)
                task.setStop(true);
        }
        Log.e(getClass().getName(), "执行指令--" + exe);
    }

}
