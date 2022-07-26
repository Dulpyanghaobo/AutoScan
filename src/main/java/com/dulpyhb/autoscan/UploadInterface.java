package com.dulpyhb.autoscan;

enum FileType {
    Img,
    Txt,
    Doc,
    Pdf
}

public interface UploadInterface {
    UploadInterface createUploadUtil(FileType type);
    void uploadFile(FileType fileType);
}
