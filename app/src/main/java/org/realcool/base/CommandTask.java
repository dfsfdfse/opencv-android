package org.realcool.base;

import android.util.Log;

import org.realcool.base.msg.BaseMsg;
import org.realcool.service.event.TaskEvent;

import java.util.LinkedList;

public abstract class CommandTask extends BaseTask{
    private BaseMsg msg;

    public CommandTask(){
        super();
        leaf = true;

    }

    public BaseMsg getMsg() {
        return msg;
    }

    public void result(BaseMsg msg){
        this.msg = msg;
        Log.e(getClass().getName(), "获得结果:");
        startLine();
    }

    @Override
    public void exec() {
        Log.e(getClass().getName(),"发送执行指令");
        TaskEvent.postAction(this);
        setWaitOther(true);
    }

}
