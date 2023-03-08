package org.realcool.base.msg;

import org.realcool.bean.Page;

public class CurrentPageMsg extends BaseMsg{
    private final Page currentPage;

    public CurrentPageMsg(Page currentPage){
        super();
        this.currentPage = currentPage;
    }

    public Page getCurrentPage() {
        return currentPage;
    }
}
