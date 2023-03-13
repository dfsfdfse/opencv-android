package org.realcool.base.impl;

import android.util.Log;

import org.realcool.base.CollectTask;
import org.realcool.base.MainTask;
import org.realcool.base.min.GetCurrentPageTask;
import org.realcool.bean.Page;
import org.realcool.bean.PageLoader;
import org.realcool.game.WorldMode;

import java.util.List;

public class DaYeTask extends CollectTask {
    public DaYeTask(MainTask task) {
        super();
        delay = 1000;
        add(new GetCurrentPageTask(PageLoader.getInstance().getPageList()).addOnFinished(cur -> {
            //得到当前页面
            Page currentPage = cur.getCurrentPage();
            if (currentPage!= null){
                Log.e(currentPage.getName(), "当前页面");
                WorldMode worldMode = PageLoader.getInstance().getWorldMode();
                Log.e(worldMode.toString(), "世界模式");
                currentPage.toTargetPage(this, worldMode);
                worldMode.getCurrentPos(this);
                addOnFinished(_this -> {
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
