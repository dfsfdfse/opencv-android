package org.realcool.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.realcool.R;
import org.realcool.dialog.MenuDialog;
import org.realcool.utils.WinUtils;

public class FloatService extends Service {
    private WindowManager wm;
    private View fv;
    private WindowManager.LayoutParams floatParams;
    private MenuDialog md;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fv = LayoutInflater.from(this).inflate(R.layout.float_service, null);
        int d = WinUtils.dip2px(this, 50);
        floatParams = WinUtils.newWmParams(d, d, this);
        wm = WinUtils.getWM(this);
        md = new MenuDialog(this);
        md.setListener(new MenuDialog.Listener() {
            @Override
            public void onFloatWindowAttachChange(boolean attach) {
                if (attach) {
                    wm.addView(fv, floatParams);
                } else {
                    wm.removeView(fv);
                }
            }
            @Override
            public void onExitService() {
                stopSelf();
            }
        });
        wm.addView(fv, floatParams);
        fv.setClickable(true);
        fv.setOnTouchListener(new View.OnTouchListener() {
            private int x;
            private int y;
            //是否在移动
            private boolean isMoving;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        isMoving = false;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int moveX = nowX - x;
                        int moveY = nowY - y;
                        if (Math.abs(moveX) > 0 || Math.abs(moveY) > 0) {
                            isMoving = true;
                            floatParams.x += moveX;
                            floatParams.y += moveY;
                            //更新View的位置
                            wm.updateViewLayout(fv, floatParams);
                            x = nowX;
                            y = nowY;
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isMoving) {
                            md.show();
                            return true;
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(fv);
        md.hide();
    }
}