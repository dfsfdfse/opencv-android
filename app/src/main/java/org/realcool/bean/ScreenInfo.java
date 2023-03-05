package org.realcool.bean;

public class ScreenInfo {
    private static class ScreenInfoHolder{
        public static ScreenInfo instance = new ScreenInfo();
    }

    private ScreenInfo(){
    }

    public static ScreenInfo getInstance(){
        return ScreenInfoHolder.instance;
    }

    // 屏幕相关信息
    private int screenWidth;
    private int screenHeight;
    private int screenDensity;

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenDensity() {
        return screenDensity;
    }

    public void setScreenDensity(int screenDensity) {
        this.screenDensity = screenDensity;
    }

    public void init(int screenWidth, int screenHeight, int screenDensity){
        this.screenDensity = screenDensity;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

}
