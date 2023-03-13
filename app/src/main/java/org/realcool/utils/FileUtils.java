package org.realcool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FileUtils {
    public static Bitmap getBitmapByFileName(Context context,String fileName){
        InputStream open = null;
        try {
            open = context.getResources().getAssets().open(fileName);
            return BitmapFactory.decodeStream(open);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert open != null;
                open.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T>Map<String, T> loadAssetsYaml(Context context,String fileName) {
        InputStream open = null;
        try {
            open = context.getResources().getAssets().open(fileName);
            Yaml yaml = new Yaml();
            return yaml.load(open);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert open != null;
                open.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void test(Context context){
        Bitmap bitmapByFileName = getBitmapByFileName(context, "images/s.png");
        String imgText = TessUtils.getImgText(bitmapByFileName);
        Log.e("识别文字", imgText);
    }
}
