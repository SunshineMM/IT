package com.digw.it.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.digw.it.Constant;
import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.activity.SubjectStartActivity;
import com.digw.it.adapter.DataRecyclerViewAdapter;
import com.digw.it.entity.question.GroupTitle;
import com.digw.it.entity.question.QuestionRequestResult;
import com.digw.it.entity.question.SectionTitle;
import com.digw.it.util.JsonUtil;
import com.digw.it.util.net.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AssignmentFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private DataRecyclerViewAdapter adapter;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_assignment;
    }

    @Override
    public void initView(View view) {
        recyclerView=$(R.id.recycler_view);
    }

    @Override
    public void viewClick(View v) {

    }

    @Override
    public void setListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new DataRecyclerViewAdapter(getContext(),changeData());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    private ArrayList<SectionTitle> changeData(){
        ArrayList<SectionTitle> sectionTitles=new ArrayList<>();
        for (GroupTitle t: ITApplication.getInstance().titles) {
            sectionTitles.add(new SectionTitle(true,t.getName()));
            for (GroupTitle.SubTitle sub:t.getChildData()) {
                sectionTitles.add(new SectionTitle(sub));
            }
        }
        return sectionTitles;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (((SectionTitle)adapter.getData().get(position)).isHeader)return;
        GetQuestionDataAsyncTask task=new GetQuestionDataAsyncTask();
        task.execute(((SectionTitle)adapter.getData().get(position)).t.getId());

    }

    private class GetQuestionDataAsyncTask extends AsyncTask<Integer,Void,String>{



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String result = "";
            JSONObject resultJson;
            FormBody.Builder builder = new FormBody.Builder();
            builder.add(Constant.QUESTION_REQUEST_KEY_COUNT, Constant.QUESTION_REQUEST_COUNT+"");
            builder.add(Constant.QUESTION_REQUEST_KEY_TAGID, integers[0]+"");
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder().url(Constant.URL_POST_REQUEST_QUESTION).post(requestBody).build();
            try {
                result= HttpUtil.okHttpClient.newCall(request).execute().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if ("".equals(s)){
                return;
            }else if ("操作太快，请稍后再试".equals(s)){
                return;
            }
            try {
                JSONObject resultJson=new JSONObject(s);
                if (resultJson.getInt("code")==0&&resultJson.getString("msg").equals("OK")){
                    QuestionRequestResult q= JsonUtil.jsonToBean(resultJson.getJSONObject("data").toString(),QuestionRequestResult.class);
                    Intent intent=new Intent(getActivity(), SubjectStartActivity.class);
                    intent.putExtra("question",q);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
