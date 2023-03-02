package org.realcool.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.realcool.base.min.SwipeTask;
import org.realcool.base.min.TapTask;

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
    public void onTap(TapTask tapTask){
        Log.e("Tag","模拟点击事件, x: " + tapTask.getX()+",y:" + tapTask.getY()+",duration:"+tapTask.getDuration());
        int x = tapTask.getX();
        int y = tapTask.getY();
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path p = new Path();
        p.moveTo(x , y);
        p.lineTo(x , y);
        builder.addStroke(new GestureDescription.StrokeDescription(p, 0, tapTask.getDuration()));
        GestureDescription gesture = builder.build();
        dispatchGesture(gesture, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Log.e("Tag", "onCompleted: 完成..........");
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Log.e("Tag", "onCompleted: 取消..........");
            }
        }, null);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onSwipe(SwipeTask swipeTask){
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path p = new Path();
        p.moveTo(swipeTask.getSx() , swipeTask.getSy());
        p.lineTo(swipeTask.getEx() , swipeTask.getEy());
        builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, swipeTask.getDuration()));
        GestureDescription gesture = builder.build();
        dispatchGesture(gesture, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Log.e("Tag", "onCompleted: 完成..........");
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Log.e("Tag", "onCompleted: 取消..........");
            }
        }, null);
    }
}
