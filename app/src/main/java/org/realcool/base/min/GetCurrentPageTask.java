package org.realcool.base.min;

import android.util.Log;

import org.realcool.base.CommandTask;
import org.realcool.bean.Page;

import java.util.List;

public class GetCurrentPageTask extends CommandTask {
    private final List<Page> pageList;
    public GetCurrentPageTask(List<Page> pageList) {
        super();
        this.pageList = pageList;
        Log.e("pageList:", pageList.size()+"");
    }

    public List<Page> getPageList() {
        return pageList;
    }
}
