package com.digw.it.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digw.it.Constant;
import com.digw.it.ITApplication;
import com.digw.it.R;

/**
 * digw创建于17-5-24.
 */

public class SplashActivity extends BaseActivity {
    private LinearLayout waitLayout;
    private TextView waitTv;
    private static final int SHOW_TIME_MIN = 3;// 最小显示时间单位秒
    private long mStartTime = 0;// 开始时间
    private boolean threadFlag = false;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.APP_INIT_DONE:
                    waitLayout.setVisibility(View.VISIBLE);
                    splashThread.start();
                    break;
            }
        }
    };

    private Thread splashThread = new Thread() {
        @Override
        public void run() {
            super.run();
            try {
                for (int i = 0; i < SHOW_TIME_MIN; i++) {
                    if (threadFlag) break;
                    sleep(1000);
                    mStartTime += 1;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            waitTv.setText(SHOW_TIME_MIN - mStartTime + "秒");
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    threadFlag = true;
                    mStartTime = SHOW_TIME_MIN;
                    startActivity(MainActivity.class);
                    SplashActivity.this.finish();
                }
            });
        }
    };

    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.wait_layout:
                threadFlag = true;
                break;
        }
    }

    @Override
    public void initParams(Bundle bundle) {
        setAllowFullScreen(true);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_spalsh;
    }

    @Override
    public void initView(View view) {
        mContextView.setBackgroundColor(Color.BLUE);
        waitLayout = $(R.id.wait_layout);
        waitTv = $(R.id.wait_tv);
        waitTv.setText(SHOW_TIME_MIN + "秒");
    }

    @Override
    public void setListener() {
        waitLayout.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        ITApplication.getInstance().init(mHandler);
    }
}
