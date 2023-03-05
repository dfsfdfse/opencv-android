package org.realcool.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.opencv.core.Mat;
import org.realcool.MainActivity;
import org.realcool.base.BaseTask;
import org.realcool.base.min.ComparePicPointTask;
import org.realcool.base.min.GetAllTextTask;
import org.realcool.base.min.SearchTextTask;
import org.realcool.base.msg.BaseMsg;
import org.realcool.base.msg.PicTextMsg;
import org.realcool.base.msg.PointMsg;
import org.realcool.bean.MatchPoint;
import org.realcool.bean.ScreenInfo;
import org.realcool.bean.TempMat;
import org.realcool.utils.SearchImgUtils;
import org.realcool.utils.WinUtils;

import java.nio.ByteBuffer;

public class VisualService extends Service {
    private ImageReader imgReader;
    private int sw;
    private int sh;
    private int code;
    private Intent data;
    MediaProjection projection;
    VirtualDisplay display;

    @Override
    public IBinder onBind(Intent intent) {
        EventBus.getDefault().register(this);
        code = intent.getIntExtra("code", -1);
        data = intent.getParcelableExtra("data");
        sw = WinUtils.getSW(this);
        sh = WinUtils.getSH(this);
        projection = ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE))
                .getMediaProjection(code, data);
        imgReader = ImageReader.newInstance(sw, sh, PixelFormat.RGBA_8888, 2);
        display = projection.createVirtualDisplay("VisualService", sw, sh, WinUtils.getDDpi(this),
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imgReader.getSurface(), null, null);
        return null;
    }

    private Bitmap getLatest() {
        Image image = imgReader.acquireLatestImage();
        if (image == null) {
            Log.e(getClass().getName(), "image为空重新获取图片");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getLatest();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        Log.e("img size", "width:" + width + "height:" + height);
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width +
                rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        image.close();
        return Bitmap.createBitmap(bitmap, 0, 0, width, height);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void comparePicPoint(ComparePicPointTask task) {
        Bitmap target = SearchImgUtils.getBitmapById(this, task.getImg());
        Mat temp = SearchImgUtils.getMatByBitmap(target);
        Mat origin = SearchImgUtils.getMatByBitmap(getLatest());
        TempMat tempMat = new TempMat(origin, temp);
        MatchPoint matchPoint = SearchImgUtils.boolMatch(tempMat);
        BaseTask.Listener listener = task.getListener();
        if (listener != null) {
            if (matchPoint.isMatch()) {
                PointMsg points = SearchImgUtils.getPoints(matchPoint, tempMat);
                listener.onFinished(points);
            } else {
                listener.onFinished(new BaseMsg(BaseMsg.FAIL));
            }
        }
        task.notifyResult();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void searchText(SearchTextTask task) {
        BaseTask.Listener listener = task.getListener();
        PointMsg point = SearchImgUtils.searchText(MainActivity.getInstance().getOcrEngine(), task.getText(), getLatest());
        if (point != null) {
            point.setMsg("找到文字" + task.getText());
            if (listener != null) listener.onFinished(point);
        } else {
            if (listener != null)
                listener.onFinished(new BaseMsg(BaseMsg.FAIL, "未找到文字" + task.getText()));
        }
        task.notifyResult();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getAllText(GetAllTextTask task){
        BaseTask.Listener listener = task.getListener();
        PicTextMsg picTextMsg = SearchImgUtils.searchAllText(MainActivity.getInstance().getOcrEngine(), getLatest());
        if (listener != null) listener.onFinished(picTextMsg);
        task.notifyResult();
    }


    /**
     * 横竖屏切换
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void screenChange(ScreenInfo info) {
        sw = WinUtils.getSW(this);
        sh = WinUtils.getSH(this);
        if (imgReader!=null){
            imgReader.close();
            imgReader = null;
            imgReader = ImageReader.newInstance(sw, sh,PixelFormat.RGBA_8888, 2);
        }
        if (projection != null) {
            projection.stop();
            projection = null;
            projection = ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE))
                    .getMediaProjection(code, data);
        }
        if (display != null) {
            display.release();
            display = null;
            display = projection.createVirtualDisplay("VisualService", sw, sh, WinUtils.getDDpi(this),
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    imgReader.getSurface(), null, null);
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("TAG", "Service onDestroy");
        imgReader.close();
        if (display != null) {
            display.release();
            display = null;
        }
        if (projection != null) {
            projection.stop();
            projection = null;
        }
    }
}
