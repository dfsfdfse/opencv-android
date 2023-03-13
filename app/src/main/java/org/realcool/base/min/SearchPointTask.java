package org.realcool.base.min;

import org.realcool.base.CommandTask;

import java.util.List;

public class SearchPointTask extends CommandTask {
    private final List<String> imgs;

    private final List<String> texts;

    public SearchPointTask(List<String> imgs, List<String> texts){
        super();
        this.imgs = imgs;
        this.texts = texts;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public List<String> getTexts() {
        return texts;
    }
}
