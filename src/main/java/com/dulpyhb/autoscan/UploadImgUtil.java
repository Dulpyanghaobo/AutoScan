package com.dulpyhb.autoscan;

public class UploadImgUtil implements UploadInterface{

    @Override
    public UploadInterface createUploadUtil(FileType type) {
        UploadInterface util = new UploadImgUtil();
        return util;
    }

    @Override
    public void uploadFile(FileType fileType) {

    }
}
