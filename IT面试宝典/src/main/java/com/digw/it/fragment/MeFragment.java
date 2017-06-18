package com.digw.it.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.activity.ClockActivity;
import com.digw.it.activity.CollectActivity;
import com.digw.it.activity.DownloadActivity;
import com.digw.it.activity.ErrorBrowseActivity;
import com.digw.it.activity.ErrorPracticeActivity;
import com.digw.it.activity.InvitationActivity;
import com.digw.it.activity.LoginActivity;
import com.digw.it.activity.SettingActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * digw创建于17-5-24.
 */

public class MeFragment extends BaseFragment {
    private LinearLayout registerOrLogin,
            error_practice,         //错题练习
            error_browse,         //错题浏览
            practice_test,       //练习试卷
            answer_question,         //回答问题
            comment,               //发表帖子
            collect_topic,    //收藏题目
            collect_comment,     //收藏帖子
            my_download,            //我的下载
            punch_card;         //我的打卡
    private TextView set_tv;//设置

    private TextView tvUserName;
    private CircleImageView civUserIcon;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView(View view) {
        registerOrLogin = $(R.id.registerOrLogin);
        error_practice = $(R.id.error_practice);      //错题练习
        error_browse = $(R.id.error_browse);            //错题浏览
        practice_test = $(R.id.practice_test);         //练习试卷
        answer_question = $(R.id.answer_question);    //回答问题
        comment = $(R.id.comment);                       //发表帖子
        collect_topic = $(R.id.collect_topic);        //收藏题目
        collect_comment = $(R.id.collect_comment);    //收藏帖子
        my_download = $(R.id.my_download);            //我的下载
        punch_card = $(R.id.punch_card);              //我的打卡
        set_tv = $(R.id.set_tv);                         //设置

        tvUserName=$(R.id.tv_username);
        civUserIcon=$(R.id.civ_user_icon);
    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.registerOrLogin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.error_practice:
                //错题练习
                startActivity(new Intent(getActivity(), ErrorPracticeActivity.class));
                break;
            case R.id.error_browse:
                //错题浏览
                startActivity(new Intent(getActivity(), ErrorBrowseActivity.class));
                break;
            case R.id.practice_test:
                //练习测试
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.answer_question:
                //回答问题
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.comment:
                //发表帖子
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.collect_topic:
                //收藏题目
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.collect_comment:
                //收藏帖子
                startActivity(new Intent(getActivity(), InvitationActivity.class));
                break;
            case R.id.my_download:
                //我的下载
                startActivity(new Intent(getActivity(), DownloadActivity.class));
                break;
            case R.id.punch_card:
                //我的打卡
                startActivity(new Intent(getActivity(), ClockActivity.class));
                break;
            case R.id.set_tv:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void setListener() {
        registerOrLogin.setOnClickListener(this);
        error_practice.setOnClickListener(this);        //错题练习
        error_browse.setOnClickListener(this);        //错题浏览
        practice_test.setOnClickListener(this);       //练习试卷
        answer_question.setOnClickListener(this);         //回答问题
        comment.setOnClickListener(this);               //发表帖子
        collect_topic.setOnClickListener(this);    //收藏题目
        collect_comment.setOnClickListener(this);    //收藏帖子
        my_download.setOnClickListener(this);            //我的下载
        punch_card.setOnClickListener(this);        //我的打卡
        set_tv.setOnClickListener(this);        //设置
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ITApplication.getInstance().LoginFlag){
            tvUserName.setText(ITApplication.getInstance().getCurrUser().getNickname());
            Picasso.with(getContext()).load(ITApplication.getInstance().getCurrUser().getHeadImg()).into(civUserIcon);
        }
    }
}

