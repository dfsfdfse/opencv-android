package org.realcool.bean;

import android.util.Log;

import org.realcool.base.CollectTask;
import org.realcool.base.CommandTask;
import org.realcool.base.min.SearchImgTask;
import org.realcool.base.min.SearchPointTask;
import org.realcool.base.min.SearchTextTask;
import org.realcool.base.min.SwipeTask;
import org.realcool.base.min.TapTask;
import org.realcool.base.msg.PointMsg;

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

    private Integer outX;

    private Integer enterY;

    private Integer outY;

    private Integer tapOffsetX;

    private Integer tapOffsetOutX;

    private Integer tapOffsetY;

    private Integer tapOffsetOutY;

    private Integer swipeXStart;

    private Integer swipeYStart;

    private Integer swipeXEnd;

    private Integer swipeYEnd;

    private Integer swipeDuration;

    private List<String> enterText;

    private List<String> outText;

    private List<String> enterImage;

    private List<String> outImage;
    //其他进入方式执行
    private ExtEnter enter;

    private ExtOut out;

    private ExtEnterSwipe swipe;

    @Override
    public String toString() {
        return "Page{" +
                "name='" + name + '\'' +
                '}';
    }

    public Page(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.suitFeatureImageNum = 1;
        this.suitFeatureTextNum = 1;
    }

    public Integer getSwipeXStart() {
        return swipeXStart;
    }

    public void setSwipeXStart(Integer swipeXStart) {
        this.swipeXStart = swipeXStart;
    }

    public Integer getSwipeYStart() {
        return swipeYStart;
    }

    public void setSwipeYStart(Integer swipeYStart) {
        this.swipeYStart = swipeYStart;
    }

    public Integer getSwipeXEnd() {
        return swipeXEnd;
    }

    public void setSwipeXEnd(Integer swipeXEnd) {
        this.swipeXEnd = swipeXEnd;
    }

    public Integer getSwipeYEnd() {
        return swipeYEnd;
    }

    public void setSwipeYEnd(Integer swipeYEnd) {
        this.swipeYEnd = swipeYEnd;
    }

    public Integer getSwipeDuration() {
        return swipeDuration;
    }

    public void setSwipeDuration(Integer swipeDuration) {
        this.swipeDuration = swipeDuration;
    }

    public List<String> getEnterText() {
        return enterText;
    }

    public void setEnterText(List<String> enterText) {
        this.enterText = enterText;
    }

    public List<String> getOutText() {
        return outText;
    }

    public void setOutText(List<String> outText) {
        this.outText = outText;
    }

    public List<String> getEnterImage() {
        return enterImage;
    }

    public void setEnterImage(List<String> enterImage) {
        this.enterImage = enterImage;
    }

    public List<String> getOutImage() {
        return outImage;
    }

    public void setOutImage(List<String> outImage) {
        this.outImage = outImage;
    }

    public ExtEnterSwipe getSwipe() {
        return swipe;
    }

    public void setSwipe(ExtEnterSwipe swipe) {
        this.swipe = swipe;
    }

    public Integer getOutX() {
        return outX;
    }

    public void setOutX(Integer outX) {
        this.outX = outX;
    }

    public Integer getOutY() {
        return outY;
    }

    public void setOutY(Integer outY) {
        this.outY = outY;
    }

    public Integer getTapOffsetOutX() {
        return tapOffsetOutX;
    }

    public void setTapOffsetOutX(Integer tapOffsetOutX) {
        this.tapOffsetOutX = tapOffsetOutX;
    }

    public Integer getTapOffsetOutY() {
        return tapOffsetOutY;
    }

    public void setTapOffsetOutY(Integer tapOffsetOutY) {
        this.tapOffsetOutY = tapOffsetOutY;
    }

    public ExtOut getOut() {
        return out;
    }

    public void setOut(ExtOut out) {
        this.out = out;
    }

    public Integer getTapOffsetX() {
        return tapOffsetX;
    }

    public void setTapOffsetX(Integer tapOffsetX) {
        this.tapOffsetX = tapOffsetX;
    }

    public Integer getTapOffsetY() {
        return tapOffsetY;
    }

    public void setTapOffsetY(Integer tapOffsetY) {
        this.tapOffsetY = tapOffsetY;
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
        page.setParent(this);
        children.add(page);
    }

    public void swipeEnter(CollectTask task) {
        if (swipeXStart != null && swipeYStart != null && swipeXEnd != null && swipeYEnd != null) {
            task.add(new SwipeTask(swipeXStart, swipeYStart, swipeXEnd, swipeYEnd, swipeDuration));
        } else if (swipe != null) {
            swipe.execSwipe(task);
        }
    }

    /**
     * 进入当前页面的方式
     */
    public void enter(CollectTask task) {
        swipeEnter(task);
        // 通过点击坐标点的按钮
        if (enterX != null && enterY != null) {
            task.add(new TapTask(enterX, enterY));
        } else if (enterText != null || enterImage != null) {
            task.add(new SearchPointTask(enterImage, enterText).addOnFinished(res -> {
                CommandTask r = (CommandTask) res;
                addTapTask(task, r);
            }));
        } else if (enter != null) {
            enter.execExt();
        } else {
            Log.e(getClass().getName(), "无进入页面的方法");
        }
    }

    private void addTapTask(CollectTask task, CommandTask r) {
        PointMsg msg = null;
        if (r.getMsg() != null) {
            msg = (PointMsg) r.getMsg();
            enterX = msg.getX();
            enterY = msg.getY();
            task.add(new TapTask(enterX + tapOffsetX, enterY + tapOffsetY));
        } else {
            //todo 未获取到坐标
            Log.e(getClass().getName(), "未获取到坐标");
        }
    }

    /**
     * 返回父级
     */
    public void out(CollectTask task) {
        // 通过点击坐标点的按钮
        if (outX != null && outY != null) {
            task.add(new TapTask(outX, outY));
        } else if (outText != null) {
            task.add(new SearchPointTask(outImage, outText).addOnFinished(res -> {
                CommandTask r = (CommandTask) res;
                addTapTask(task, r);
            }));
        } else if (out != null) {
            out.execOut();
        } else {
            Log.e(getClass().getName(), "无返回上级方法");
        }
    }

    //当前页面到目标页面
    public void toTargetPage(CollectTask task, Page target) {
        toTargetPage(task, this, target, null);
    }

    private void toTargetPage(CollectTask task, Page current, Page target, List<Page> routes) {
        if (current == target) return;
        if (routes == null) {
            routes = startToEnd(current, target);
            if (routes.size() == 0) return;
        }
        int i = routes.indexOf(current);
        if (i != -1 && i < routes.size() - 1) {
            Page node = routes.get(i + 1);
            if (current.parent == node) {
                current.out(task);
            } else {
                node.enter(task);
            }
            toTargetPage(task, node, target, routes);
        } else {
            //重新找路
            toTargetPage(task, current, target, null);
        }
    }

    /**
     * 获取最短路径
     *
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
        int index = 0;
        List<Page> res = new ArrayList<>();
        for (int i = endRoute.size() - 1; i >= 0; i--) {
            Page page = endRoute.get(i);
            if (!startRoute.contains(page)) {
                res.add(page);
                if (index == 0) index = i + 1;
            }
        }
        List<Page> pages = startRoute.subList(0, index);
        pages.addAll(res);
        Log.e("获得路径", pages.toString());
        return pages;
    }

    public List<Page> toTargetRoutes(Page target) {
        return startToEnd(this, target);
    }

    public List<Page> targetToCurrentRoutes(Page target) {
        return startToEnd(target, this);
    }

    private void getRouteToRoot(Page note, List<Page> list) {
        if (note != null) {
            list.add(note);
            getRouteToRoot(note.getParent(), list);
        }
    }

    public interface ExtEnterSwipe {
        void execSwipe(CollectTask task);
    }

    public interface ExtEnter {
        void execExt();
    }

    public interface ExtOut {
        void execOut();
    }
}
