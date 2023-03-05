package org.realcool.base.msg;

import com.benjaminwan.ocrlibrary.Point;
import com.benjaminwan.ocrlibrary.TextBlock;

import java.util.List;

public class PicTextMsg extends BaseMsg{
    private final List<TextBlock> textList;

    public PicTextMsg(List<TextBlock> textList){
        super();
        this.textList = textList;
    }

    public List<Point> getTextPoints(String text){
        for (TextBlock textBlock : textList) {
            if (textBlock.getText().contains(text)) {
                return textBlock.getBoxPoint();
            }
        }
        return null;
    }

    public String getFullText(String text){
        for (TextBlock textBlock : textList) {
            if (textBlock.getText().contains(text)) {
                return textBlock.getText();
            }
        }
        return null;
    }
}
