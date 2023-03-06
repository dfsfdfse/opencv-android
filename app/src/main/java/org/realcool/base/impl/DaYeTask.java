package org.realcool.base.impl;

import org.realcool.base.CollectTask;

public class DaYeTask extends CollectTask {

    public DaYeTask(){
        super();
        add(new SearchTextTapTask("日历"));
    }
}
