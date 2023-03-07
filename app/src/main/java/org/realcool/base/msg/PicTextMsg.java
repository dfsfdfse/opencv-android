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

    /**
     *
     * @param texts 特征文字
     * @param num 达标数量
     * @return 是否符合
     */
    public boolean suitImg(List<String> texts, int num){
        int index = 0;
        for (int i = 0; i < textList.size(); i++) {
            TextBlock textBlock = textList.get(i);
            String text = textBlock.getText();
            for (int j = 0; j < texts.size(); j++) {
                if (text.contains(texts.get(j))){
                    index++;
                    if (index >= num) return true;
                }
            }
        }
        return false;
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
