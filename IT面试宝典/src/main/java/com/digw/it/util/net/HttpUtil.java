package com.digw.it.util.net;


import android.app.Activity;

import com.digw.it.util.net.progress.ProgressRequestBody;
import com.digw.it.util.net.progress.ProgressResponseBody;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * digw创建于17-3-26.
 */

public class HttpUtil {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();

    public static void doGet(String urlPath, final NetListener.HttpCallbackListener listener){
        doGet(null,urlPath,listener);
    }

    public static void doGet(final Activity activity, String urlPath, final NetListener.HttpCallbackListener listener) {
        Request request = new Request.Builder().url(urlPath).addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (null == activity) {
                    listener.onError(e);
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (null == activity) {
                        listener.onError(new IOException("Unexpected code " + response));
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(new IOException("Unexpected code " + response));
                            }
                        });
                    }
                } else {
                    if (null == activity) {
                        listener.onFinish(response);
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFinish(response);
                            }
                        });
                    }

                }
            }
        });
    }

    public static void doPost(String urlPath, Map<String, String> params, final NetListener.HttpCallbackListener listener){
        doPost(null,urlPath,params,listener);
    }

    public static void doPost(final Activity activity, String urlPath, Map<String, String> params, final NetListener.HttpCallbackListener listener) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(urlPath).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (null==activity){
                    listener.onError(e);
                }else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (null==activity){
                        listener.onError(new IOException("Unexpected code " + response));
                    }else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(new IOException("Unexpected code " + response));
                            }
                        });
                    }
                } else {
                    if (null==activity){
                        listener.onFinish(response);
                    }else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFinish(response);
                            }
                        });
                    }
                }
            }
        });
    }

    public static void doPostUploadFileOnProgress(String urlPath, MultipartBody requestBody, final NetListener.HttpCallbackProgressListener listener) {
        Request request = new Request.Builder().url(urlPath).post(new ProgressRequestBody(requestBody, listener)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onFinish(response);
            }
        });
    }

    public static void doGetDownloadFileOnProgress(String urlPath, final NetListener.HttpCallbackProgressListener progressListener) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        });
        Request request = new Request.Builder().url(urlPath).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                progressListener.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressListener.onFinish(response);
            }
        });
    }

}
