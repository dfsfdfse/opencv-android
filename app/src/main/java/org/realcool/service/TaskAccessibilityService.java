package org.realcool.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.realcool.base.BaseTask;
import org.realcool.base.MinTask;
import org.realcool.base.min.SwipeTask;
import org.realcool.base.min.TapTask;
import org.realcool.base.msg.BaseMsg;

public class TaskAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        EventBus.getDefault().register(this);
        Log.e("无障碍","服务连接了...");
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onTap(TapTask task){
        Log.e(getClass().getName(),"模拟点击事件, x: " + task.getX()+",y:" + task.getY()+",duration:"+task.getDuration());
        BaseTask.Listener listener = task.getListener();
        int x = task.getX();
        int y = task.getY();
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path p = new Path();
        if (x > 0 && y > 0){
            p.moveTo(x , y);
            p.lineTo(x , y);
            builder.addStroke(new GestureDescription.StrokeDescription(p, 0, TapTask.TAP_SHORT));
            GestureDescription gesture = builder.build();
            dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    if (listener != null) listener.onFinished(new BaseMsg(BaseMsg.FINISH));
                    Log.e("Tag", "onCompleted: 完成..........");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                }
            }, null);
        } else {
            if (listener != null) listener.onFinished(new BaseMsg(BaseMsg.FAIL, "图片位置获取有问题"));
        }
        task.notifyResult();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onSwipe(SwipeTask task){
        Log.e(getClass().getName(),"开始swipe...");
        BaseTask.Listener listener = task.getListener();
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path p = new Path();
        p.moveTo(task.getSx() , task.getSy());
        p.lineTo(task.getEx() , task.getEy());
        builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, task.getDuration()));
        GestureDescription gesture = builder.build();
        dispatchGesture(gesture, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                if (listener != null) listener.onFinished(new BaseMsg(BaseMsg.FINISH));
                Log.e("Tag", "onCompleted: 完成..........");
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Log.e("Tag", "onCompleted: 取消..........");

            }
        }, null);
        task.notifyResult();
    }
}
