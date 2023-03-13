package org.realcool.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

public class TessUtils {
    private static final String LANGUAGE = "eng+chi_sim";

    private static final String PATH = "file:///android_asset/tess/";

    private static final TessBaseAPI API = new TessBaseAPI();

    public static TessBaseAPI getInstance() {
        if (!API.init(PATH, LANGUAGE)) {
            API.clear();
        }
        return API;
    }

    public static String getImgText(Bitmap bitmap) {
        getInstance().setImage(bitmap);
        return getInstance().getUTF8Text();
    }
}
