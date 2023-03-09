package org.realcool.base.min;

import org.realcool.base.CommandTask;

public class SearchImgTask extends CommandTask {
    private final String img;
    public SearchImgTask(String img){
        super();
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}
