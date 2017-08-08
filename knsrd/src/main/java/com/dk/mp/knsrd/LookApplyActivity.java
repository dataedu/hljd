package com.dk.mp.knsrd;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.view.RecycleViewDivider;
import com.dk.mp.core.widget.ErrorLayout;
import com.dk.mp.knsrd.adapter.LookApplyAdapter;
import com.dk.mp.knsrd.entity.LookApplyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cobb on 2017/8/3.
 */

public class LookApplyActivity extends MyActivity {

    private ErrorLayout mError;
    private RecyclerView myListView;
    private List<LookApplyEntity> mData = new ArrayList<>();
    private LookApplyAdapter ada;

    private String type = "";

    @Override
    protected int getLayoutID() {
        return R.layout.ac_look_apply;
    }

    @Override
    protected void initialize() {
        super.initialize();


        setTitle(getIntent().getStringExtra("type"));

        mError = (ErrorLayout) findViewById(R.id.error_layout);
        myListView = (RecyclerView) findViewById(R.id.mListview);

        initpage();
    }

    private void initpage() {

        mData.add(new LookApplyEntity("申请档次","3829-90","申请时间"));
        mData.add(new LookApplyEntity("申请档次","3829-90","申请时间"));
        mData.add(new LookApplyEntity("申请档次","3829-90","申请时间"));

        myListView.setHasFixedSize(true);
        myListView.setLayoutManager(new LinearLayoutManager(mContext));
        ada = new LookApplyAdapter(mContext,mData);
        myListView.setAdapter(ada);
        myListView.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.HORIZONTAL, 20, Color.rgb(244,244,244)));//添加分割线
    }
}
