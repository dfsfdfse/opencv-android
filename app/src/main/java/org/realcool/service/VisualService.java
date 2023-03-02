package org.realcool.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class VisualService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
