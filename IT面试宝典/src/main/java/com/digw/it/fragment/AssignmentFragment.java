package com.digw.it.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digw.it.R;
import com.digw.it.adapter.DataRecyclerViewAdapter;

public class AssignmentFragment extends BaseFragment {
    private RecyclerView recyclerView;

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
        recyclerView.setAdapter(new DataRecyclerViewAdapter(getContext()));
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
