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

    private Integer suitFeatureImageNum;

    private Integer suitFeatureTextNum;
    //点击的坐标欸子
    private Integer enterX;

    private Integer enterY;

    private List<String> enterText;

    private List<String> enterImage;
    //其他进入方式执行
    private ExtEnter enter;

    private CheckCurrent boolCurrent;

    public Page(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.suitFeatureImageNum = 1;
        this.suitFeatureTextNum = 1;
    }

    public List<String> getEnterText() {
        return enterText;
    }

    public void setEnterText(List<String> enterText) {
        this.enterText = enterText;
    }

    public List<String> getEnterImage() {
        return enterImage;
    }

    public void setEnterImage(List<String> enterImage) {
        this.enterImage = enterImage;
    }

    public CheckCurrent getBoolCurrent() {
        return boolCurrent;
    }

    public void setBoolCurrent(CheckCurrent boolCurrent) {
        this.boolCurrent = boolCurrent;
    }

    public Integer getSuitFeatureImageNum() {
        return suitFeatureImageNum;
    }

    public void setSuitFeatureImageNum(Integer suitFeatureImageNum) {
        this.suitFeatureImageNum = suitFeatureImageNum;
    }

    public Integer getSuitFeatureTextNum() {
        return suitFeatureTextNum;
    }

    public void setSuitFeatureTextNum(Integer suitFeatureTextNum) {
        this.suitFeatureTextNum = suitFeatureTextNum;
    }

    public Integer getEnterX() {
        return enterX;
    }

    public void setEnterX(Integer enterX) {
        this.enterX = enterX;
    }

    public Integer getEnterY() {
        return enterY;
    }

    public void setEnterY(Integer enterY) {
        this.enterY = enterY;
    }

    public ExtEnter getEnter() {
        return enter;
    }

    public void setEnter(ExtEnter enter) {
        this.enter = enter;
    }

    public Page getParent() {
        return parent;
    }

    public void setParent(Page parent) {
        this.parent = parent;
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

    public void add(Page page) {
        children.add(page);
    }

    /**
     * 进入当前页面的方式
     */
    public void enter(){
        // 通过点击坐标点的按钮
        if (enterX != null && enterY != null){

        } else if (enterText != null){

        } else if (enterImage != null){

        } else if (enter != null) {
            enter.execExt();
        }
    }

    /**
     * 获取最短路径
     * @param start
     * @param end
     * @return
     */
    public List<Page> startToEnd(Page start, Page end) {
        List<Page> startRoute = new ArrayList<>();
        List<Page> endRoute = new ArrayList<>();
        getRouteToRoot(start, startRoute);
        getRouteToRoot(end, endRoute);
        Page intersect = null;
        boolean flag = false;
        List<Page> res = new ArrayList<>();
        for (int i = startRoute.size() - 1; i >= 0; i--) {
            Page page = startRoute.get(i);
            if (flag || endRoute.contains(intersect = page)){
                flag = true;
                res.add(page);
            }
        }
        res.addAll(endRoute.subList(endRoute.indexOf(intersect) + 1, endRoute.size()));
        return res;
    }

    public List<Page> toTarget(Page target){
        return startToEnd(this, target);
    }

    public List<Page> toPage(Page target){
        return startToEnd(target, this);
    }

    private void getRouteToRoot(Page note, List<Page> list) {
        if (note != null) {
            list.add(note);
            getRouteToRoot(note.getParent(), list);
        }
    }

    public interface CheckCurrent{
        /**
         * 校验Page是否是当前页面
         * @return
         */
        boolean isCurrent();
    }

    public interface ExtEnter{

        void execExt();
    }
}
