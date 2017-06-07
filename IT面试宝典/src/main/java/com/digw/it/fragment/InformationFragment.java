package com.digw.it.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.adapter.NewsRecyclerViewAdapter;
import com.digw.it.entity.NewsTitle;
import com.digw.it.util.net.HttpUtil;
import com.digw.it.util.net.NetListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

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
        HttpUtil.doGet("http://c.m.163.com/nc/article/headline/T1348649580692/0-40.html", new NetListener.HttpCallbackListener() {
            @Override
            public void onFinish(Response response) {
                try {
                    JSONObject jsonObj=new JSONObject(response.body().string());
                    JSONArray jsonArr=jsonObj.getJSONArray("T1348649580692");
                    for(int i=1;i<jsonArr.length();i++){
                        if (jsonArr.getJSONObject(i).has("url")){
                            NewsTitle n=new NewsTitle();
                            n.setTitle(jsonArr.getJSONObject(i).getString("title"));
                            n.setDigest(jsonArr.getJSONObject(i).getString("digest"));
                            n.setModify(jsonArr.getJSONObject(i).getString("lmodify"));
                            n.setSource(jsonArr.getJSONObject(i).getString("source"));
                            n.setUrl(jsonArr.getJSONObject(i).getString("url"));
                            n.setImgsrc(jsonArr.getJSONObject(i).getString("imgsrc"));
                            ITApplication.getInstance().newsTitles.add(n);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeChanged(0,ITApplication.getInstance().newsTitles.size());
                        refreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

}
