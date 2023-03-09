package org.realcool.base.impl;

import android.util.Log;

import org.realcool.base.CollectTask;
import org.realcool.base.MainTask;
import org.realcool.base.min.GetCurrentPageTask;
import org.realcool.bean.Page;

public class DaYeTask extends CollectTask {
    public DaYeTask(MainTask task, Page target) {
        super();
        delay = 1000;
        add(new GetCurrentPageTask(task.getPageList()).addOnFinished(cur -> {
            //得到当前页面
            Page currentPage = cur.getCurrentPage();
            if (currentPage!= null){
                Log.e(currentPage.getName(), "当前页面");
                currentPage.toTargetPage(this, target);
                addOnFinished(_this -> {
                    Log.e("DaYeTask", "完成");
                    //回到第一个任务
                    leftOne();
                });
            }
            setOpen(false);
        }));
    }

    private void leftOne() {
        children.removeIf(item -> children.getFirst() != item);
    }


}
