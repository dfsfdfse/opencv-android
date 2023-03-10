package org.realcool.base.min;

import android.graphics.Bitmap;

import org.realcool.base.CollectTask;
import org.realcool.base.CommandTask;

public class TestTask extends CollectTask {

    private Bitmap test1;
    private Bitmap test2;

    public TestTask() {
        add(new SwipeTask(200, 500, 4200, 500, 2000, 500));
        addOnFinished(res->{
            setOpen(false);
        });
    }

    public Bitmap getTest1() {
        return test1;
    }

    public Bitmap getTest2() {
        return test2;
    }
}
