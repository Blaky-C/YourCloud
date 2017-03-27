package com.yourcloud.yourcloud.Model.Utils;


/**
 * Created by huangrui on 2017/3/22.
 */

public interface OnDownloadItemListener {
    <T> void onGetProcess(T item, long completeBytes, long totalBytes);
}
