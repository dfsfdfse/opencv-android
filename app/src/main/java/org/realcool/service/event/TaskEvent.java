package org.realcool.service.event;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class TaskEvent {
    public static final int ADD_DAYE = 1;
    public static final int STOP_DAYE = 3;
    public static final int ADD_CAIJI = 2;
    public static final int STOP_CAIJI = 4;
    public static final int START = 5;
    public static final int STOP = 6;

    private int type;
    public TaskEvent() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TaskEvent(int type){
        this.type = type;
    }

    public static void postStartAction(){
        postAction(new TaskEvent(START));
    }

    public static void postTaskAction(int type){
        postAction(new TaskEvent(type));
    }

    public static void postAction(Object event) {
        EventBus.getDefault().post(event);
    }
}
