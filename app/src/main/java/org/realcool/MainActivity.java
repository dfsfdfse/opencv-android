package org.realcool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.realcool.permission.FloatPermission;
import org.realcool.service.FloatService;

public class MainActivity extends AppCompatActivity {
    private final String STRING_ALERT = "悬浮窗";
    private final String STRING_START = "开始";
    private TextView start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.rc_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (start.getText().toString()){
                    case STRING_START:
                        startService(new Intent(MainActivity.this, FloatService.class));
                        moveTaskToBack(true);
                        break;
                    case STRING_ALERT:
                        requestPermissionAndShow();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkState();
    }

    private void checkState() {
        boolean hasWinPermission = FloatPermission.getInstance().check(this);
        if (hasWinPermission) {
            start.setText(STRING_START);
        }
    }
    /**
     * 开启悬浮窗权限
     */
    private void requestPermissionAndShow() {
        new AlertDialog.Builder(this).setTitle("悬浮窗权限未开启")
                .setMessage(getString(R.string.app_name) + "获得悬浮窗权限，才能正常使用应用")
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 显示授权界面
                        try {
                            FloatPermission.getInstance().apply(MainActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
    }
}

