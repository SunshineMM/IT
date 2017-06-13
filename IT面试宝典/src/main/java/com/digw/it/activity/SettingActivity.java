package com.digw.it.activity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;

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
    }


    public static class SettingFragment extends PreferenceFragment{

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
