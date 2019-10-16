package com.yolo.downloadmanager;

public interface ProgressListener {
    void onProgress(String percent);
    void onFinished();
}
