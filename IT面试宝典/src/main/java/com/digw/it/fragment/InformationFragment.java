package com.digw.it.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.digw.it.Constant;
import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.activity.NewsInfoActivity;
import com.digw.it.adapter.NewsRecyclerViewAdapter;
import com.digw.it.entity.news.NewsTitle;
import com.digw.it.util.JsonUtil;
import com.digw.it.util.net.HttpUtil;
import com.digw.it.util.net.NetListener;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * digw创建于17-5-24.
 */

public class InformationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView newsRecyclerView;
    private NewsRecyclerViewAdapter adapter;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_information;
    }

    @Override
    public void initView(View view) {
        refreshLayout=$(R.id.refresh_layout);
        newsRecyclerView=$(R.id.news_recycler_view);
    }

    @Override
    public void viewClick(View v) {

    }

    @Override
    public void setListener() {
        refreshLayout.setOnRefreshListener(this);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new NewsRecyclerViewAdapter(getContext());
        newsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(), NewsInfoActivity.class);
                intent.putExtra("news",ITApplication.getInstance().newsTitles.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        update();
    }

    @Override
    public void onRefresh() {
        update();
    }

    private void update(){
        adapter.notifyItemRangeRemoved(0,ITApplication.getInstance().newsTitles.size());
        ITApplication.getInstance().newsTitles.clear();
        //获取资讯列表
        HttpUtil.doGet(Constant.URL_GET_NEWS_LIST, new NetListener.HttpCallbackListener() {
            @Override
            public void onFinish(Response response) {
                String result=null;
                try {
                    ResponseBody body=response.body();
                    result= body != null ? body.string() : null;
                    JSONObject jsonObj=new JSONObject(result);
                    JSONArray jsonArr=jsonObj.getJSONArray(Constant.NEWS_GROUP);
                    for(int i=1;i<jsonArr.length();i++){
                        if (jsonArr.getJSONObject(i).has("url")){
                            NewsTitle n= JsonUtil.jsonToBean(jsonArr.getJSONObject(i).toString(),NewsTitle.class);
                            ITApplication.getInstance().newsTitles.add(n);
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemRangeChanged(0,ITApplication.getInstance().newsTitles.size());
                            refreshLayout.setRefreshing(false);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

}
