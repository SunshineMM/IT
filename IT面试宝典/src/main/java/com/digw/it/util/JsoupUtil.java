package com.digw.it.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * digw创建于17-6-5.
 */

public class JsoupUtil {

    public static void getDocumentByUrl(final String url, final JsoupCallbackListener listener){

        new Thread(){
            @Override
            public void run() {
                try {
                    Document document=Jsoup.connect(url).get();
                    if (null==document){
                        listener.onError();
                    }else {
                        listener.onFinish(document);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public interface JsoupCallbackListener{
        void onFinish(Document document);
        void onError();
    }
}
