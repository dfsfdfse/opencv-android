package org.realcool.base;

public abstract class MinTask extends BaseTask{
    private boolean newThread;

    @Override
    public void exec() {
        if (newThread){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doTaskDelay();
                }
            }).start();
        } else {
            doTaskDelay();
        }
    }

    @Override
    public void stop() {

    }

    public boolean isNewThread() {
        return newThread;
    }

    public void setNewThread(boolean newThread) {
        this.newThread = newThread;
    }

    protected abstract void doTask();

    private void doTaskDelay(){
        Long d = getDelay();
        if (d != null && d != 0){
            try {
                Thread.sleep(d);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        doTask();
    }
}
