package com.libo.libokdemos.Basis.Implement;

/**
 * Created by libok on 2018-01-11.
 */

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFaild();

    void onPaused();

    void onCanceled();
}
