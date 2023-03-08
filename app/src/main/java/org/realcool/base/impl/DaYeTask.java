package org.realcool.base.impl;

import android.util.Log;

import org.realcool.base.CollectTask;
import org.realcool.base.CommandTask;
import org.realcool.base.min.GetCurrentPageTask;
import org.realcool.base.msg.BaseMsg;
import org.realcool.base.msg.CurrentPageMsg;
import org.realcool.bean.Page;

import java.util.List;

public class DaYeTask extends CollectTask {
    public DaYeTask(List<Page> pageList){
        super();
        delay = 1000;
        GetCurrentPageTask getCurrentPageTask = new GetCurrentPageTask(pageList);
        getCurrentPageTask.addOnFinished(()->{
            BaseMsg msg = getCurrentPageTask.getMsg();
            if (msg != null) {
                /*Log.e("当前页面", ((CurrentPageMsg)getCurrentPageTask.getMsg()).getCurrentPage().getName());
                setOpen(false);*/
                //计算路径


            }
        });
        add(getCurrentPageTask);
    }
}
