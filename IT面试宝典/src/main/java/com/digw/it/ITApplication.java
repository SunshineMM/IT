package com.digw.it;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.digw.it.entity.news.NewsTitle;
import com.digw.it.entity.question.GroupTitle;
import com.digw.it.entity.User;
import com.digw.it.util.JsonUtil;
import com.digw.it.util.net.HttpUtil;
import com.digw.it.util.net.NetListener;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * digw创建于17-5-24.
 */

public class ITApplication extends Application {
    private static ITApplication Instance = null;
    private List<Activity> activities = new ArrayList<>();
    private User currUser=null;
    public SharedPreferences userPrf=null;
    public ArrayList<GroupTitle> titles = new ArrayList<>();
    public ArrayList<NewsTitle> newsTitles= new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        Instance=this;
        userPrf=getSharedPreferences(Constant.SHARED_USER, Context.MODE_PRIVATE);
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
        SharedPreferences.Editor editor=userPrf.edit();
        editor.putInt("id",currUser.getId());
        editor.putString("name",currUser.getName());
        editor.putInt("type",currUser.getType());
        editor.putString("email",currUser.getEmail());
        editor.putString("sex",currUser.getSex());
        editor.putString("password",currUser.getPassword());
        editor.apply();
    }

    public void init(final Handler handler){

        //判断用户是否登录
        if (userPrf.getInt("id",-1)!=-1){
            currUser=new User();
            currUser.setId(userPrf.getInt("id",-1));
            currUser.setName(userPrf.getString("name",""));
            currUser.setType(userPrf.getInt("type",-1));
            currUser.setEmail(userPrf.getString("email",""));
            currUser.setSex(userPrf.getString("sex",""));
            currUser.setPassword(userPrf.getString("password",""));
        }

        //获取标题数据
        HttpUtil.doPost(Constant.URL_POST_TITLE, null, new NetListener.HttpCallbackListener() {
            @Override
            public void onFinish(Response response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("state")){
                        JSONArray jsonArray=jsonObject.getJSONArray("result");
                        for (int i=0;i<jsonArray.length();i++){
                            Type type = new TypeToken<GroupTitle>() {}.getType();
                            GroupTitle title= JsonUtil.getGson().fromJson(jsonArray.getJSONObject(i).toString(),type);
                            titles.add(title);
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(Constant.APP_INIT_DONE);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity:activities) {
            activity.finish();
        }
        System.exit(0);
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static ITApplication getInstance(){
        return Instance;
    }
}
