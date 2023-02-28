package org.realcool.dialog;

import android.content.Context;
import android.view.View;
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
