package org.realcool.service;

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
import org.realcool.MainActivity;
import org.realcool.base.min.GetAllTextTask;
import org.realcool.base.min.GetCurrentPageTask;
import org.realcool.base.min.SearchTextTask;
import org.realcool.base.msg.CurrentPageMsg;
import org.realcool.base.msg.PicTextMsg;
import org.realcool.base.msg.PointMsg;
import org.realcool.bean.Page;
import org.realcool.bean.ScreenInfo;
import org.realcool.utils.SearchImgUtils;
import org.realcool.utils.WinUtils;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class VisualService extends Service {
    private ImageReader imgReader;
    private int sw;
    private int sh;
    private int code;
    private Intent data;
    private ReentrantLock lock;
    private Condition wait;
    private boolean useScreen;
    private Image image;
    MediaProjection projection;
    VirtualDisplay display;

    @Override
    public IBinder onBind(Intent intent) {
        EventBus.getDefault().register(this);
        code = intent.getIntExtra("code", -1);
        data = intent.getParcelableExtra("data");
        sw = WinUtils.getSW(this);
        sh = WinUtils.getSH(this);
        lock = new ReentrantLock();
        wait = lock.newCondition();
        projection = ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE))
                .getMediaProjection(code, data);
        initReader();
        display = projection.createVirtualDisplay("VisualService", sw, sh, WinUtils.getDDpi(this),
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imgReader.getSurface(), null, null);
        return null;
    }
    private void initReader(){
        imgReader = ImageReader.newInstance(sw, sh, PixelFormat.RGBA_8888, 2);
        imgReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image img = reader.acquireLatestImage();
                if (img != null){
                    if (useScreen){
                        image = img;
                        lock.lock();
                        useScreen = false;
                        wait.signal();
                        lock.unlock();
                    } else {
                        img.close();
                    }
                }
            }
        }, null);
    }
    //等待录屏甩出最新bitmap
    private Bitmap getLatest() {
        useScreen = true;
        lock.lock();
        try {
            while (useScreen) wait.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        int width = image.getWidth();
        int height = image.getHeight();
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

    /*------------------------------------------------------------------*/

    /**
     * 检测当前页面是哪个页面
     * @param task
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getCurrentPage(GetCurrentPageTask task) {
        Bitmap latest = getLatest();
        List<Page> pageList = task.getPageList();
        for (int i = 0; i < pageList.size(); i++) {
            Page page = pageList.get(i);
            List<String> featureText = page.getFeatureText();
            List<String> featureImage = page.getFeatureImage();
            if (featureText != null && featureText.size() > 0) {
                if (SearchImgUtils.matchText(MainActivity.getInstance().getOcrEngine(), latest, page.getFeatureText(), page.getSuitFeatureTextNum())) {
                    task.result(new CurrentPageMsg(page));
                    return;
                }
            } else if (featureImage != null && featureImage.size() > 0){
                if (SearchImgUtils.matchImg(this, latest, featureImage, page.getSuitFeatureImageNum())){
                    task.result(new CurrentPageMsg(page));
                    return;
                }
            }
        }
        latest.recycle();
        task.result(null);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void searchText(SearchTextTask task) {
        Bitmap latest = getLatest();
        PointMsg msg = SearchImgUtils.searchText(MainActivity.getInstance().getOcrEngine(), task.getText(), latest);
        latest.recycle();
        task.result(msg);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getAllText(GetAllTextTask task) {
        Bitmap latest = getLatest();
        PicTextMsg msg = SearchImgUtils.searchAllText(MainActivity.getInstance().getOcrEngine(), latest);
        latest.recycle();
        task.result(msg);
    }

    /**
     * 横竖屏切换
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void screenChange(ScreenInfo info) {
        sw = WinUtils.getSW(this);
        sh = WinUtils.getSH(this);
        if (imgReader != null) {
            imgReader.close();
            imgReader = null;
            initReader();
        }
        /*if (projection != null) {
            projection.stop();
            projection = null;
            projection = ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE))
                    .getMediaProjection(code, data);
        }*/
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
