package org.realcool.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ScreenRecordService extends Service {
    public ScreenRecordService() {
        Log.e("ScreenRecordService","服务初始化..");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}