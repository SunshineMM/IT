package com.digw.it.util.net;

import okhttp3.Response;

/**
 * digw创建于17-4-13.
 */

public class NetListener {
    public interface HttpCallbackListener {
        void onFinish(Response response);

        void onError(Exception e);
    }

    public interface HttpCallbackProgressListener {
        void onProgress(long bytesRW, long contentLength, boolean done);

        void onFinish(Response response);

        void onError(Exception e);
    }
}
