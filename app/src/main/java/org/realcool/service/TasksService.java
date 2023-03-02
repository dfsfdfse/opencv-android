package org.realcool.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.realcool.base.MainTask;
import org.realcool.base.impl.CaiJiTask;
import org.realcool.base.impl.DaYeTask;
import org.realcool.service.event.TaskEvent;

import java.util.ArrayList;
import java.util.List;

public class TasksService extends Service {
    private List<MainTask> tasks;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tasks = new ArrayList<MainTask>();
        EventBus.getDefault().register(this);
        Log.e("taskService","创建了");
    }

    private void exec() {
        Log.e("task", "开始执行任务中");
        if (tasks != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (MainTask task : tasks) {
                        task.exec();
                    }
                }
            }).start();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(TaskEvent event) {
        if (tasks != null) {
            switch (event.getType()) {
                case TaskEvent.ADD_CAIJI:
                    tasks.add(new CaiJiTask());
                    break;
                case TaskEvent.ADD_DAYE:
                    tasks.add(new DaYeTask());
                    break;
                case TaskEvent.REMOVE_CAIJI:
                    removeTask(MainTask.CAIJI_TYPE);
                    break;
                case TaskEvent.REMOVE_DAYE:
                    removeTask(MainTask.DAYE_TYPE);
                    break;
                case TaskEvent.START:
                    exec();
                    break;
            }
        }
    }

    private void removeTask(int type) {
        if (tasks != null) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getType() == type){
                    tasks.remove(i);
                    break;
                }
            }
        }
    }
}
