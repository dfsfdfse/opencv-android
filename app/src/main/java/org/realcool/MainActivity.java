package org.realcool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benjaminwan.ocrlibrary.OcrEngine;

import org.opencv.android.OpenCVLoader;
import org.realcool.bean.PageLoader;
import org.realcool.permission.AccessibilityPermission;
import org.realcool.permission.FloatPermission;
import org.realcool.service.FloatService;
import org.realcool.service.TaskAccessibilityService;
import org.realcool.service.TasksService;
import org.realcool.service.VisualService;
import org.realcool.utils.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ServiceConnection{
    private final String STRING_ALERT = "悬浮窗";
    private final String STRING_START = "开始";
    private final String STRING_GO = "无障碍";
    private static final int REQUEST_CODE = 1000;
    private TextView start;
    private static MainActivity instance;
    public static MainActivity getInstance(){
        if(instance == null){
            throw new NullPointerException("MainActivity instance hasn't registered");
        }
        return instance;
    }
    private OcrEngine ocrEngine;

    public OcrEngine getOcrEngine(){
        return ocrEngine;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.rc_start);
        initOpenCV();
        checkPermission();
        startScreenRecording();
        PageLoader.loadPage(this, "source.yaml");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (start.getText().toString()){
                    case STRING_START:
                        startService(new Intent(MainActivity.this, TasksService.class));
                        startService(new Intent(MainActivity.this, FloatService.class));
                        moveTaskToBack(true);
                        break;
                    case STRING_ALERT:
                        requestPermissionAndShow();
                        break;
                    case STRING_GO:
                        requestAcccessibility();
                }
            }
        });
        ocrEngine = new OcrEngine(this.getApplicationContext());
        Log.i("TAG", "onCreate: OCR服务初始化完成");
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 200){
            checkPermission();
        }
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                // 获得权限，启动Service开始录制
                Intent service = new Intent(this, VisualService.class);
                service.putExtra("code", resultCode);
                service.putExtra("data", data);
                //startService(service);
                bindService(service, this, BIND_AUTO_CREATE);
            } else {
                Toast.makeText(this, "用户关闭", Toast.LENGTH_LONG).show();
                Log.i("", "User cancelled");
            }
        }
    }

    private void initOpenCV(){
        if(!OpenCVLoader.initDebug()){
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, null);
        }
    }

    /**
     * 权限申请
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA
            };
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 200);
                    return;
                }
            }
        }
    }

    private void checkState() {
        boolean hasWinPermission = FloatPermission.getInstance().check(this);
        boolean hasAccessibility = AccessibilityPermission.isSettingOpen(TaskAccessibilityService.class, MainActivity.this);
        Log.e("hasAccessibility",Boolean.toString(hasAccessibility));
        if (hasAccessibility){
            if (hasWinPermission) {
                start.setText(STRING_START);
            } else {
                start.setText(STRING_ALERT);
            }
        } else {
            start.setText(STRING_GO);
        }
    }

    private void startScreenRecording() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent permissionIntent = mediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(permissionIntent, REQUEST_CODE);
    }

    private void requestAcccessibility() {
        new AlertDialog.Builder(this).setTitle("无障碍服务未开启")
                .setMessage("你的手机没有开启无障碍服务，" + getString(R.string.app_name) + "将无法正常使用")
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 显示授权界面
                        try {
                            AccessibilityPermission.jumpToSetting(MainActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
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

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
}

