package org.realcool.dialog;

import android.content.Context;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;

import org.realcool.R;
import org.realcool.service.event.TaskEvent;
import org.realcool.utils.WinUtils;

public class MenuDialog extends BaseServiceDialog implements View.OnClickListener {
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

    @Override
    protected void onInited() {
        setCanceledOnTouchOutside(true);
        findViewById(R.id.rc_exec).setOnClickListener(this);
        Switch cj = findViewById(R.id.swt_cj);
        Switch dy = findViewById(R.id.swt_dy);
        cj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    TaskEvent.postTaskAction(TaskEvent.ADD_CAIJI);
                }else {
                    TaskEvent.postTaskAction(TaskEvent.STOP_CAIJI);
                }
            }
        });
        dy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    TaskEvent.postTaskAction(TaskEvent.ADD_DAYE);
                }else {
                    TaskEvent.postTaskAction(TaskEvent.STOP_DAYE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rc_exec:
                TaskEvent.postStartAction();
                hide();
                break;
        }
    }
}
