import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;

import org.realcool.utils.FileUtils;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static App instance;
    public static App getInstance(){
        if(instance == null){
            throw new NullPointerException("App instance hasn't registered");
        }
        return instance;
    }

    // 自定义数据
    private OcrEngine ocrEngine;
    private float ocrRatio = 0.9f;

    public OcrEngine getOcrEngine(){
        return ocrEngine;
    }

    /**
     * 文字检测
     * @param bitmap
     * @return
     */
    public OcrResult detect(Bitmap bitmap){
        Bitmap temp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int size = bitmap.getHeight()>bitmap.getWidth()?bitmap.getHeight():bitmap.getWidth();
        size = (int)(ocrRatio*size);
        OcrResult or = ocrEngine.detect(bitmap, temp, size);
        FileUtils.saveImage(temp, "OCR-temp");
        temp.recycle();
        return or;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // 初始化OCR服务
        ocrEngine = new OcrEngine(this.getApplicationContext());
        Log.i(TAG, "onCreate: OCR服务初始化完成");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
