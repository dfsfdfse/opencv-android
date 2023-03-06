package org.realcool.dialog;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.realcool.R;
import org.realcool.service.event.CheckedEvent;
import org.realcool.service.event.TaskEvent;
import org.realcool.utils.WinUtils;

public class MenuDialog extends BaseServiceDialog implements View.OnClickListener {
    private Handler handler;

    public MenuDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void hide() {
        if (isShowing()) dismiss();
    }

    @Override
    protected void beforeAddContent() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_menu;
    }

    @Override
    protected int getWidth() {
        return WinUtils.dip2px(getContext(), 350);
    }

    @Override
    protected int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onInited() {
        setCanceledOnTouchOutside(true);
        findViewById(R.id.rc_exec).setOnClickListener(this);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch cj = findViewById(R.id.swt_cj);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch dy = findViewById(R.id.swt_dy);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        dy.setChecked(true);
                        break;
                    case 1:
                        dy.setChecked(false);
                        break;
                    case 2:
                        cj.setChecked(true);
                        break;
                    case 3:
                        cj.setChecked(false);
                }
            }
        };
        EventBus.getDefault().register(this);
        cj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TaskEvent.postTaskAction(TaskEvent.ADD_CAIJI);
                } else {
                    TaskEvent.postTaskAction(TaskEvent.STOP_CAIJI);
                }
            }
        });
        dy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TaskEvent.postTaskAction(TaskEvent.ADD_DAYE);
                } else {
                    TaskEvent.postTaskAction(TaskEvent.STOP_DAYE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rc_exec:
                TaskEvent.postStartAction();
                hide();
                break;
        }
    }

    @Subscribe
    public void setCheckState(CheckedEvent event) {
        Message msg = new Message();
        msg.what = event.isChecked() ? 0 : 1;
        handler.sendMessage(msg);
    }
}
