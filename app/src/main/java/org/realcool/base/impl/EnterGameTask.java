package org.realcool.base.impl;

import org.realcool.base.MainTask;

public class EnterGameTask extends MainTask {
    public EnterGameTask(){
        super();
        add(new SearchTextTapTask("英雄杀", 0, -50));
        add(new SearchTextTapTask("联盟",0, -50));
        add(new SearchTextTapTask("联",0, 0));
        add(new SearchTextTapTask("联盟成员", 0, 0));
        add(new SearchTextTapTask("realcool", 0, 0));
    }
}
