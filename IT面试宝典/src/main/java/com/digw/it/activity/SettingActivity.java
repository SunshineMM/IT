package com.digw.it.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.digw.it.R;

/**
 * digw创建于17-6-13.
 */

public class SettingActivity extends BaseActivity {
    @Override
    public void viewClick(View v) {

    }

    @Override
    public void initParams(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingFragment()).commit();
        SettingFragment frag = (SettingFragment) getFragmentManager().findFragmentById(R.id.content_frame);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Log.e("666",sp.getBoolean("wifi",false)+"");

    }


    public static class SettingFragment extends PreferenceFragment{
        private String mAteKey;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

    }
}
