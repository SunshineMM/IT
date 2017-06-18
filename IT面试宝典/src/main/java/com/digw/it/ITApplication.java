package com.digw.it;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.digw.it.entity.User;
import com.digw.it.entity.news.NewsTitle;
import com.digw.it.entity.question.GroupTitle;
import com.digw.it.util.JsonUtil;
import com.digw.it.util.net.HttpUtil;
import com.digw.it.util.net.NetListener;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public boolean LoginFlag=false;
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
        LoginFlag=true;
        File fileDir=getFilesDir();
        File userObj=new File(fileDir,"userObj");
        if (userObj.exists()){
            userObj.delete();
        }
        try {
            FileOutputStream fos=new FileOutputStream(userObj);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(currUser);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(final Handler handler){

        //判断用户是否登录
        File userObj=new File(getFilesDir(),"userObj");
        if (userObj.exists()){
            LoginFlag=true;
            try {
                FileInputStream fis=new FileInputStream(userObj);
                ObjectInputStream ois=new ObjectInputStream(fis);
                currUser= (User) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Log.e("TAG", "init: 用户"+currUser.getNickname()+"已登录");
        }

        //获取标题数据
        HttpUtil.UserAgent=Constant.URL_NOWCODER_USER_AGENT;
        HttpUtil.doPostEnqueue(Constant.URL_POST_TITLE, null, new NetListener.HttpCallbackListener() {
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
