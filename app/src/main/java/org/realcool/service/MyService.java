package org.realcool.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyService extends AccessibilityService {
    public MyService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e("无障碍","服务连接了...");
    }

    @Override
    public void onInterrupt() {
    }

    /**
     * 模拟滑动事件
     *
     * @param fx1
     * @param fy1
     * @param fx2
     * @param fy2
     * @param startTime 0即可执行
     * @param duration  滑动时长
     * @return 执行是否成功
     */
    private void swipe(float fx1, float fy1,float fx2 , float fy2 ,final int startTime , final int duration) {
        Log.e("Tag","模拟滑动事件");
        int x1 = (int)fx1;
        int y1 = (int)fy1;
        int x2 = (int)fx2;
        int y2 = (int)fy2;
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path p = new Path();
        p.moveTo(x1 , y1);
        p.lineTo(x2 , y2);
        builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, duration));
        GestureDescription gesture = builder.build();
        dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {
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