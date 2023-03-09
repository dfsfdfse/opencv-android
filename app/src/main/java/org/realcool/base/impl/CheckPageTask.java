package org.realcool.base.impl;

import org.realcool.base.CommandTask;
import org.realcool.bean.Page;

/**
 * 判断当前所在页面是不是指定页面
 */
public class CheckPageTask extends CommandTask {
    private final Page page;

    public CheckPageTask(Page page) {
        super();
        this.page = page;
    }

    public Page getPage() {
        return page;
    }
}
