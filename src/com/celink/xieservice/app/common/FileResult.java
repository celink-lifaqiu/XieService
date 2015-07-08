package com.celink.xieservice.app.common;

import com.celink.xieservice.utils.FileUtils;



/**
 * Created with IntelliJ IDEA.
 * User: Yin Jian Feng
 * Date: 14-3-6
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 */
public class FileResult {
    private FileUtils.FILE_MESSAGE message;
    private String filePath;
    private String url;

    public FileUtils.FILE_MESSAGE getMessage() {
        return message;
    }

    public void setMessage(FileUtils.FILE_MESSAGE message) {
        this.message = message;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
