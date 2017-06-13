package com.digw.it.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.digw.it.R;

//错误练习
public class ErrorPracticeActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout computer   //计算机
           ,mathsAndLogic ;//数学和逻辑
    private LinearLayout programme  ////编程
            ,probability;   //概率统计

    private View v1;
    private boolean isShowOrGone=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_error_practice);

        initview();
        initlistener();
        programme.setVisibility(View.GONE);
        probability.setVisibility(View.GONE);
        v1.setVisibility(View.VISIBLE);

    }

    private void initlistener() {
        computer.setOnClickListener(this);
        mathsAndLogic.setOnClickListener(this);
    }

    private void initview() {
        v1=findViewById(R.id.v1);
        computer= (LinearLayout) findViewById(R.id.line_computer);
        programme= (LinearLayout) findViewById(R.id.layout_programme);
        mathsAndLogic= (LinearLayout) findViewById(R.id.line_mathsAndLogic);
        probability= (LinearLayout) findViewById(R.id.layout_probability);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.line_computer:
                if (isShowOrGone){
                    programme.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.GONE);
                    isShowOrGone=false;
                }else {
                    programme.setVisibility(View.GONE);
                    v1.setVisibility(View.VISIBLE);
                    isShowOrGone=true;
                }
                break;
            case R.id.line_mathsAndLogic:
                if (isShowOrGone){
                    probability.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.GONE);
                    isShowOrGone=false;
                }else {
                    probability.setVisibility(View.GONE);
                    v1.setVisibility(View.VISIBLE);
                    isShowOrGone=true;
                }
                break;
            default:
                break;
        }
    }
}
