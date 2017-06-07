package com.digw.it.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digw.it.Constant;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    /*是否是Debug版本*/
    private boolean isDebug;
    /*APP的名字*/
    private String APP_NAME;
    /*设置TAG*/
    protected final String TAG = this.getClass().getSimpleName();
    /*当前Fragment渲染的视图View*/
    protected View mContextView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDebug = Constant.isDebug;
        APP_NAME = Constant.APP_NAME;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContextView = bindView();
        if (null == mContextView) {
            mContextView = inflater.inflate(bindLayout(), container, false);
        }
        initView(mContextView);
        setListener();
        doBusiness(getContext());
        return mContextView;
    }

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
    public abstract void initView(View view);

    /*
    * 绑定控件
    * */
    @SuppressWarnings({"unchecked", "unused"})
    public <T extends View> T $(int resId) {
        return (T) mContextView.findViewById(resId);
    }

    /*
    * 根据View绑定控件
    * */
    @SuppressWarnings({"unchecked", "unused"})
    public <T extends View> T $(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    /*
    * View点击
    * */
    public abstract void viewClick(View v);

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
    * 简化Toast
    * */
    @SuppressWarnings("unused")
    protected void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*
    * 日志输出
    * */
    @SuppressWarnings("unused")
    public void $Log(String msg) {
        if (isDebug) {
            Log.d(APP_NAME + "-" + TAG, msg);
        }
    }

}
