package org.realcool.base.min;

import org.realcool.base.CommandTask;
import org.realcool.bean.Page;

import java.util.List;

public class GetCurrentPageTask extends CommandTask {
    private final List<Page> pageList;
    public GetCurrentPageTask(List<Page> pageList) {
        super();
        this.pageList = pageList;
    }

    public List<Page> getPageList() {
        return pageList;
    }
}
