package org.realcool.base.impl;

import android.util.Log;

import org.realcool.base.BaseTask;
import org.realcool.base.MainTask;
import org.realcool.base.min.SearchTextTask;
import org.realcool.base.min.TapTask;
import org.realcool.base.msg.BaseMsg;
import org.realcool.base.msg.PointMsg;

public class SearchTextTapTask extends MainTask {
    public SearchTextTapTask(String searchText, int x, int y){
        super();
        setDelay(2000);
        TapTask tapTask1 = new TapTask();
        BaseTask task = new SearchTextTask(searchText).setListener(new Listener() {
            @Override
            public void onFinished(BaseMsg o) {
                if (o.getCode() == BaseMsg.FINISH) {
                    PointMsg m = (PointMsg) o;
                    tapTask1.setX(m.getRX(x));
                    tapTask1.setY(m.getBY(y));
                    setLoop(false);
                } else {
                    setLoop(true);
                }
                Log.e(getClass().getName(), o.getMsg());
            }
        });
        add(task);
        add(tapTask1);
    }
}
