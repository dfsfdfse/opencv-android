package org.realcool.base.min;

import android.graphics.Bitmap;

import org.realcool.base.CommandTask;

public class GetCurrentTask extends CommandTask {
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
