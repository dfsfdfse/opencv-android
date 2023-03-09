package org.realcool.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.realcool.base.CollectTask;
import org.realcool.base.MainTask;
import org.realcool.base.impl.DaYeTask;
import org.realcool.bean.Page;
import org.realcool.bean.PageLoader;
import org.realcool.service.event.CheckedEvent;
import org.realcool.service.event.TaskEvent;

import java.util.List;

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
        List<Page> pageList = PageLoader.loadPage(this, "test.yaml");
        EventBus aDefault = EventBus.getDefault();
        if (!aDefault.isRegistered(this)){
            aDefault.register(this);
        }
        task = new MainTask();
        task.setPageList(pageList);
        daYeTask = new DaYeTask(task, findPageByName(pageList, "设置"));
        daYeTask.setOpen(false);
        task.add(daYeTask);
    }

    private Page findPageByName(List<Page> pageList, String name){
        for (int i = 0; i < pageList.size(); i++) {
            Page page = pageList.get(i);
            if (page.getName().equals(name)){
                return page;
            }
        }
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(TaskEvent event) {
        switch (event.getType()) {
            case TaskEvent.ADD_CAIJI:
                break;
            case TaskEvent.ADD_DAYE:
                daYeTask.setOpen(true);
                break;
            case TaskEvent.STOP_CAIJI:
                break;
            case TaskEvent.STOP_DAYE:
                daYeTask.setOpen(false);
                break;
            case TaskEvent.START:
                task.start();
                break;
            case TaskEvent.STOP:
                task.stop();
                break;
        }
    }
}
