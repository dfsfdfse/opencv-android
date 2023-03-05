package org.realcool.base.min;

import org.realcool.base.MinTask;

public class SearchTextTask extends MinTask {
    private String text;
    public SearchTextTask(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
