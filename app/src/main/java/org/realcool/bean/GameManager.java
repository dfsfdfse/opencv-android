package org.realcool.bean;

import org.realcool.base.CollectTask;

import java.util.List;

public class GameManager {
    private static final GameManager INSTANCE = new GameManager();
    //功能任务集合
    private List<CollectTask> funTaskList;
    //当前所在页面
    private Page currentPage;
    //当前所运行的任务
    private CollectTask currentTask;

    public static GameManager getInstance(){
        return INSTANCE;
    }

}
