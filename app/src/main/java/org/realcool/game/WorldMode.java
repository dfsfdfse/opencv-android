package org.realcool.game;

import android.util.Log;

import org.realcool.base.CollectTask;
import org.realcool.base.CommandTask;
import org.realcool.base.min.GetAllTextTask;
import org.realcool.base.msg.PicTextMsg;
import org.realcool.bean.Page;

public class WorldMode extends Page {

    public WorldMode() {
        super("世界模式");
    }

    private Integer myMainCityX;

    private Integer myMainCityY;

    public Integer[] getMyMainCityPos(){
        if (myMainCityX == null || myMainCityY == null){
            return null;
        } else {
            return new Integer[]{myMainCityX, myMainCityY};
        }
    }

    public void getCurrentPos(CollectTask task){
        task.add(new GetAllTextTask().addOnFinished(res->{
            PicTextMsg msg = (PicTextMsg) ((CommandTask) res).getMsg();
            String currentPos = msg.getFullText("当前位置");
            Log.e("当前位置", currentPos+"");
        }));
    }
}
