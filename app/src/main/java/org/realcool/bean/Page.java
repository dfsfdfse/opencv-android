package org.realcool.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Page {
    //页面
    private final String name;
    //父页面 todo 可能会有2个入口暂不处理
    private Page parent;

    private final List<Page> children;
    //特征文字
    private List<String> featureText;
    //特征图片
    private List<String> featureImage;
    //入口数据
    private Map<String, List<String>> enter;
    //点击的坐标欸子
    private Integer enterX;

    private Integer enterY;

    public Page(String name){
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getFeatureText() {
        return featureText;
    }

    public void setFeatureText(List<String> featureText) {
        this.featureText = featureText;
    }

    public List<String> getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(List<String> featureImage) {
        this.featureImage = featureImage;
    }

    public List<Page> getChildren() {
        return children;
    }

    public void add(Page page){
        children.add(page);
    }

    public List<Page> startToEnd(Page start, Page end){

        return null;
    }
}
