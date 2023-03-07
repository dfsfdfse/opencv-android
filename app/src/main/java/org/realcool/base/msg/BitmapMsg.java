package org.realcool.base.msg;

import android.graphics.Bitmap;

public class BitmapMsg extends BaseMsg{
    private Bitmap bitmap;

    public BitmapMsg(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
