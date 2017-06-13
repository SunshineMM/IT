package com.digw.it.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.digw.it.Constant;
import com.digw.it.ITApplication;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    /*设置TAG*/
    protected final String TAG = this.getClass().getSimpleName();
    /*是否是Debug版本*/
    private boolean isDebug;
    /*APP的名字*/
    private String APP_NAME;
    /*是否允许全屏*/
    private boolean isAllowFullScreen = false;
    /*是否允许旋转屏幕*/
    private boolean isAllowScreenRotate = false;
    /*当前Activity渲染的视图View*/
    @SuppressWarnings("FieldCanBeLocal")
    protected View mContextView = null;

    /*
    * View点击
    * */
    public abstract void viewClick(View v);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITApplication.getInstance().addActivity(this);
        isDebug= Constant.isDebug;
        APP_NAME=Constant.APP_NAME;
        initParams(getIntent().getExtras());
        View mView = bindView();
        if (null==mView){
            mContextView = LayoutInflater.from(this).inflate(bindLayout(),null);
        } else {
            mContextView = mView;
        }
        if (isAllowFullScreen){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(mContextView);
        if (!isAllowScreenRotate){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initView(mContextView);
        setListener();
        doBusiness(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ITApplication.getInstance().removeActivity(this);
    }

    /*
            * 初始化参数
            * */
    public abstract void initParams(Bundle bundle);

    /*
    * 绑定视图
    * */
    public abstract View bindView();

    /*
    * 绑定布局
    * */
    public abstract int bindLayout();

    /*
    * 初始化控件
    * */
    public abstract void initView(final View view);

    /*
    * 绑定控件
    * */
    @SuppressWarnings({"unchecked", "unused"})
    public <T extends View>T $(int resId){
        return (T)findViewById(resId);
    }

    /*
    * 根据View绑定控件
    * */
    @SuppressWarnings({"unchecked", "unused"})
    public <T extends View>T $(View view,int resId){
        return (T)view.findViewById(resId);
    }

    /*
    * 设置监听
    * */
    public abstract void setListener();

    @Override
    public void onClick(View v) {
        viewClick(v);
    }

    /*
    * 业务操作
    * */
    public abstract void doBusiness(Context mContext);

    /*
    * 页面跳转
    * */
    @SuppressWarnings("unused")
    public void startActivity(Class<?> clz){
        startActivity(clz,null);
    }

    /*
    * 携带数据的页面跳转
    * */
    public void startActivity(Class<?> clz,Bundle bundle){
        Intent intent=new Intent(this,clz);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /*
    * 带参回调式页面跳转
    * */
    public void startActivityForResult(Class<?> clz,Bundle bundle,int requestCode){
        Intent intent = new Intent(this,clz);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /*
    * 回调式页面跳转
    * */
    @SuppressWarnings("unused")
    public void startActivityForResult(Class<?> clz, int request){
        startActivityForResult(clz,null,request);
    }

    /*
     * 简化Toast
     * */
    @SuppressWarnings("unused")
    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /*
    * 日志输出
    * */
    @SuppressWarnings("unused")
    public void $Log(String msg){
        if (isDebug){
            Log.d(APP_NAME+"-"+TAG,msg);
        }
    }

    /*
    * 是否允许全屏
    * */
    @SuppressWarnings("unused")
    public void setAllowFullScreen(boolean allowFullScreen){
        this.isAllowFullScreen = allowFullScreen;
    }

    /*
    * 是否允许屏幕旋转
    * */
    @SuppressWarnings("unused")
    public void setScreenRotate(boolean isAllowScreenRotate){
        this.isAllowScreenRotate = isAllowScreenRotate;
    }
}
