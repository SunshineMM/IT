package com.digw.it.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.digw.it.R;
import com.digw.it.adapter.ViewPagerAdapter;
import com.digw.it.fragment.AssignmentFragment;
import com.digw.it.fragment.InformationFragment;
import com.digw.it.fragment.MeFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private BottomNavigationView navigation;
    private ViewPager vpContent;
    private ViewPagerAdapter viewPagerAdapter;

    //底部导航栏选择监听
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_assignment:
                    vpContent.setCurrentItem(0);
                    return true;
                case R.id.nav_information:
                    vpContent.setCurrentItem(1);
                    return true;
                case R.id.nav_me:
                    vpContent.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    navigation.setSelectedItemId(R.id.nav_assignment);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.nav_information);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.nav_me);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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
        fragments.add(new AssignmentFragment());
        fragments.add(new InformationFragment());
        fragments.add(new MeFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        toolbar = $(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigation = $(R.id.navigation);
        vpContent = $(R.id.content);
        vpContent.setAdapter(viewPagerAdapter);
    }

    @Override
    public void setListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        vpContent.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public void doBusiness(Context mContext) {
        /*fragmentManager.beginTransaction().add(R.id.content,fragments.get(0)).add(R.id.content,fragments.get(1)).add(R.id.content,fragments.get(2)).commit();
        hideFragment();
        fragmentManager.beginTransaction().show(fragments.get(0)).commit();*/
    }

    /*private void hideFragment(){
        FragmentTransaction ft=fragmentManager.beginTransaction();
        for (int i=0;i<fragments.size();i++){
            ft.hide(fragments.get(i));
        }
        ft.commit();
    }*/

}
