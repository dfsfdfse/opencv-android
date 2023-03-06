package org.realcool.base.impl;

import org.realcool.base.CollectTask;
import org.realcool.base.min.SearchTextTask;
import org.realcool.base.min.TapTask;
import org.realcool.base.msg.PointMsg;

public class SearchTextTapTask extends CollectTask {
    public SearchTextTapTask(String searchText){
        super();
        SearchTextTask searchTextTask = new SearchTextTask(searchText);
        searchTextTask.setOnFinished(() -> {
            PointMsg msg = (PointMsg) searchTextTask.getMsg();
            if (msg != null){
                add(new TapTask(msg.getX(), msg.getY()));
                loop = false;
            } else {
                loop = true;
            }
        });
        add(searchTextTask);
    }
}
