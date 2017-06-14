package com.digw.it.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.digw.it.R;
import com.digw.it.adapter.QuestionViewPagerAdapter;
import com.digw.it.entity.question.QuestionInfo;
import com.digw.it.entity.question.QuestionRequestResult;
import com.digw.it.fragment.SubjectFragment;

import java.util.ArrayList;

public class SubjectStartActivity extends BaseActivity {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private QuestionViewPagerAdapter adapter;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private QuestionRequestResult qrr;

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
        qrr= (QuestionRequestResult) bundle.getSerializable("question");
        for (QuestionInfo qi:qrr.getAllQuestion()) {
            fragments.add(SubjectFragment.newInstance(qi));
        }
        adapter=new QuestionViewPagerAdapter(getSupportFragmentManager(),fragments);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_subject_start;
    }

    @Override
    public void initView(View view) {
        toolbar=$(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager=$(R.id.subject_vp);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
