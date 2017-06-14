package com.digw.it.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.digw.it.R;
import com.digw.it.entity.question.SectionTitle;

import java.util.List;

/**
 * digw创建于17-6-5.
 */

public class DataRecyclerViewAdapter extends BaseSectionQuickAdapter<SectionTitle,BaseViewHolder> {
    private Context mContext;
    private List<SectionTitle> sectionTitles;

    public DataRecyclerViewAdapter(Context mContext,List<SectionTitle> sectionTitles) {
        super(R.layout.item_subtitle,R.layout.item_head_title, sectionTitles);
        this.mContext = mContext;
        this.sectionTitles=sectionTitles;
        openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                };
            }
        });
        isFirstOnly(false);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionTitle item) {
        helper.setText(R.id.title,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionTitle item) {
        helper.setText(R.id.subtitle,item.t.getName());
        helper.setText(R.id.subject_num,"共"+item.t.getQuestionNum()+"题");
    }

}
