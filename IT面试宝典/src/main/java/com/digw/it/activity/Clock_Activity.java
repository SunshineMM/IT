package com.digw.it.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digw.it.R;

public class Clock_Activity extends BaseActivity  {
    private Button button1,button2;
    private TextView textView;
    private ImageView imageView;



    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.vip:
                Toast.makeText(Clock_Activity.this,"请充值",Toast.LENGTH_SHORT).show();
                break;
            case R.id.pu:
                Toast.makeText(Clock_Activity.this,"你已签到成功",Toast.LENGTH_SHORT).show();
                textView.setText("签到成功  +1");
                textView.setTextColor(Color.GREEN);
                imageView.setImageResource(R.drawable.k);
                break;
        }
    }


    @Override
    public void initParams(Bundle bundle) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_clock_;
    }

    @Override
    public void initView(View view) {
           button1=$(R.id.vip);
           button2=$(R.id.pu);
           textView=$(R.id.wen);
           imageView=$(R.id.tu);
    }

    @Override
    public void setListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void doBusiness(Context mContext) {

    }



}
