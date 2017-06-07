package com.digw.it.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.activity.LoginActivity;

/**
 * digw创建于17-5-24.
 */

public class MeFragment extends BaseFragment {
    private CardView userCardView;
    private TextView tvUserName;

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
        userCardView=$(R.id.user_card_view);
        tvUserName=$(R.id.tv_username);
        if (null!=ITApplication.getInstance().getCurrUser()){
            tvUserName.setText(ITApplication.getInstance().getCurrUser().getName());
        }
    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.user_card_view:
                if (null==ITApplication.getInstance().getCurrUser()){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    Toast.makeText(getActivity(), ""+ITApplication.getInstance().getCurrUser().getName(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void setListener() {
        userCardView.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null!=ITApplication.getInstance().getCurrUser()){
            tvUserName.setText(ITApplication.getInstance().getCurrUser().getName());
        }
    }
}
