package org.realcool.bean;

import java.util.List;

public class Page {
    public static final Integer ENTER_PIC = 1;
    public static final Integer ENTER_TEXT = 2;
    //页面图片资源id
    private Integer source;
    //页面id
    private Integer id;

    private boolean enterType;
    //父页面id todo 可能会有2个入口暂不处理
    private Integer parentId;
    //可进入的子页面id
    private List<Integer> children;
    //页面描述
    private String description;
    //从父节点进入的坐标
    private Integer[] enterPosition;

}
