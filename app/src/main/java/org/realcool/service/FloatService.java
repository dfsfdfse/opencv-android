package org.realcool.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.realcool.R;
import org.realcool.base.Task;
import org.realcool.bean.ScreenInfo;
import org.realcool.dialog.MenuDialog;

import org.realcool.service.event.TaskEvent;
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
        Log.e("floatService","创建了");
        fv = LayoutInflater.from(this).inflate(R.layout.float_service, null);
        int d = WinUtils.dip2px(this, 50);
        floatParams = WinUtils.newWmParams(d, d, this);
        wm = WinUtils.getWM(this);
        md = new MenuDialog(this);
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
                            TaskEvent.postTaskAction(TaskEvent.STOP);
                            return true;
                        }
                        break;
                }
                return false;
            }
        });
        registerConfigChangeReceiver();
    }

    private void registerConfigChangeReceiver(){
        IntentFilter configChangeFilter = new IntentFilter();
        configChangeFilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        configChangeFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mConfigChangeReceiver, configChangeFilter);
    }

    private BroadcastReceiver mConfigChangeReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("屏幕旋转","悬浮球，获取屏幕方向 getRotation = "
                    +wm.getDefaultDisplay().getRotation());
            int h,w;
            switch (wm.getDefaultDisplay().getRotation()){
                case Surface.ROTATION_0:
                    //竖屏
                    h = WinUtils.getSH(FloatService.this);
                    w = WinUtils.getSW(FloatService.this);
                    ScreenInfo.getInstance().setScreenHeight(h);
                    ScreenInfo.getInstance().setScreenWidth(w);
                    break;
                case Surface.ROTATION_90:
                case Surface.ROTATION_270:
                    //顺时针旋转270度
                    h = WinUtils.getSH(FloatService.this);
                    w = WinUtils.getSW(FloatService.this);
                    ScreenInfo.getInstance().setScreenHeight(w);
                    ScreenInfo.getInstance().setScreenWidth(h);
                    //顺时针旋转90度
                    break;
                //顺时针旋转180度
                default:
                    break;
            }
            TaskEvent.postAction(ScreenInfo.getInstance());
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(fv);
        md.hide();
    }
}