package com.digw.it.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.entity.news.NewsTitle;
import com.squareup.picasso.Picasso;

/**
 * digw创建于17-6-5.
 */

public class NewsRecyclerViewAdapter extends BaseQuickAdapter<NewsTitle, BaseViewHolder> {
    private Context mContext;

    public NewsRecyclerViewAdapter(Context context) {
        super(R.layout.item_news_list, ITApplication.getInstance().newsTitles);
        this.mContext=context;
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
    protected void convert(BaseViewHolder helper, NewsTitle item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_date,item.getModify());
        helper.setText(R.id.tv_source,item.getSource());
        Picasso.with(mContext).load(item.getImgsrc()).placeholder(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.iv_img));
    }

    /*public void setItems(ArrayList<NewsTitle> t){

    }*/

}
