package org.realcool.base;

import android.util.Log;

import org.realcool.service.event.TaskEvent;

public abstract class MinTask extends BaseTask{
    public MinTask(){
        super();
        setLeaf(true);
    }

    @Override
    public void exec() {
        Log.e(getClass().getName(),"发送指令");
        setResult(false);
        TaskEvent.postAction(this);
    }
}
