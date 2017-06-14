package com.digw.it.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.digw.it.R;
import com.digw.it.entity.question.QuestionInfo;
import com.zzhoujay.richtext.RichText;

/**
 * digw创建于17-6-13.
 */

public class SubjectFragment extends BaseFragment {
    private QuestionInfo questionInfo;
    private TextView questionType,questionContent;

    public static SubjectFragment newInstance(QuestionInfo questionInfo) {
        Bundle args = new Bundle();
        args.putSerializable("questionInfo",questionInfo);
        SubjectFragment fragment = new SubjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_subject;
    }

    @Override
    public void initView(View view) {
        questionType=$(R.id.question_type);
        questionContent=$(R.id.question_content);
        this.questionInfo= (QuestionInfo) getArguments().getSerializable("questionInfo");
        switch (questionInfo.getQuestion().getType()){
            case 1:
                questionType.setText("(单选题)");
                break;
            case 2:
                questionType.setText("(多选题)");
                break;
        }
        RichText.fromHtml(questionInfo.getQuestion().getContent()).into(questionContent);
    }

    @Override
    public void viewClick(View v) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
