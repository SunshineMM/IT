package com.digw.it.util;

import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * digw创建于17-4-8.
 */

public class JsonUtil {
    private static Gson gson = new Gson();

    public static <T>T jsonToBean(String json,Class<T> beanClass){
        T bean=null;
        try {
            bean = gson.fromJson(json,beanClass);
        } catch (Exception e){
            Log.e("JsonUtil", "解析Json数据时出现异常\nJson="+json,e);
        }
        return bean;
    }

    public static JSONObject beanToJSONObject(Object obj){
        JSONObject jsonObj=null;
        try {
            jsonObj=new JSONObject(gson.toJson(obj));
        } catch (JSONException e) {
            Log.e("JsonUtil", "转换为JsonObject时出现异常");
            e.printStackTrace();
        }
        return jsonObj;
    }

    public static JSONArray beanToJSONArray(Class<? extends List> list){
        JSONArray jsonArr=null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jsonArr=new JSONArray(list);
            }else {
                throw new JSONException("beanToJSONArray function target sdk >= 19");
            }
        } catch (JSONException e) {
            Log.e("JsonUtil", "转换为JSONArray时出现异常");
            e.printStackTrace();
        }
        return jsonArr;
    }
}
