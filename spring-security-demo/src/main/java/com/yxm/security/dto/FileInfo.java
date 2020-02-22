package com.yxm.security.dto;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
public class FileInfo {

    public FileInfo(String path){
        this.path = path;
    }

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
