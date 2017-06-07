package com.digw.it.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.entity.Title;

/**
 * digw创建于17-6-5.
 */

public class DataRecyclerViewAdapter extends BaseQuickAdapter<Title,BaseViewHolder> {
    private Context  mContext;

    public DataRecyclerViewAdapter(Context mContext) {
        super(R.layout.item_title_menu, ITApplication.getInstance().titles);
        this.mContext = mContext;
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
    protected void convert(BaseViewHolder helper, Title item) {
        helper.setText(R.id.title,item.getName());
    }

    /*@Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if (null==ITApplication.getInstance().titles){
            return 0;
        }
        return ITApplication.getInstance().titles.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_title_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(ITApplication.getInstance().titles.get(position).getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
        }
    }*/
}
