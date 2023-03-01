package org.realcool.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import org.realcool.R;
import org.realcool.utils.WinUtils;

public class MenuDialog extends BaseServiceDialog implements View.OnClickListener {
    private View overlay;           // 叠加层
    private Listener listener;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rc_start:
                Log.e("start","开始了");
                hide();
                break;
            case R.id.swt_cj:
                Log.e("cj","采集开关");
                break;
            case R.id.swt_dy:
                Log.e("dy","打野开关");
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        /**
         * 悬浮窗显示状态变化
         * @param attach
         */
        void onFloatWindowAttachChange(boolean attach);

        /**
         * 关闭辅助
         */
        void onExitService();
    }
}
