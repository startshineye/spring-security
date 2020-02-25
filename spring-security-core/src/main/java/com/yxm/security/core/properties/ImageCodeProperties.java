package com.yxm.security.core.properties;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
public class ImageCodeProperties extends SmsCodeProperties{
    private int width = 67;
    private int height = 23;
    public ImageCodeProperties(){
        setLength(4);
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
