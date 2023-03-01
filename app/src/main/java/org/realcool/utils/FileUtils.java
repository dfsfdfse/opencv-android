package org.realcool.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
    /**
     * 获取一个保存的路径
     * @return path
     */
    public static String getSavePath() {
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            String rootDir = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/" +
                    "ScreenRecord" + "/";

            File file = new File(rootDir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }
            return rootDir;
        } else {
            return null;
        }
    }

    /**
     * 保存图片到调试目录，注意文件名都是覆盖方式
     * @param bitmap
     * @param name
     */
    public static boolean saveImage(Bitmap bitmap, String name){
        String savePath = getSavePath() + name + ".png";
        try {
            FileOutputStream fos = new FileOutputStream(savePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
