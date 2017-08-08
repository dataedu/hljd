package com.dk.mp.czdh;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.view.RecycleViewDivider;
import com.dk.mp.core.widget.ErrorLayout;
import com.dk.mp.czdh.adapter.CzdhMainAdapter;
import com.dk.mp.czdh.entity.CzdhEntity;
import com.dk.mp.xsxx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cobb on 2017/8/8.
 */

public class CzdhMainActivity extends MyActivity {

    private ErrorLayout mError;
    private RecyclerView mListview;
    private List<CzdhEntity> mData = new ArrayList<>();
    private CzdhMainAdapter ada;

    @Override
    protected int getLayoutID() {
        return R.layout.ac_czdh_mani;
    }

    @Override
    protected void initView() {
        super.initView();

        mError = (ErrorLayout) findViewById(R.id.error_layout);
        mListview = (RecyclerView) findViewById(R.id.mListview);
    }

    @Override
    protected void initialize() {
        super.initialize();

        setTitle("成长导航");

        initpage();
    }

    private void initpage() {

        mData.add(new CzdhEntity("阿离","(2910384932)","1","100"));
        mData.add(new CzdhEntity("阿离","(2910384932)","2","100"));
        mData.add(new CzdhEntity("阿离","(2910384932)","3","100"));
        mData.add(new CzdhEntity("阿离","(2910384932)","4","100"));
        mData.add(new CzdhEntity("阿离","(2910384932)","5","100"));

        mListview.setHasFixedSize(true);
        mListview.setLayoutManager(new LinearLayoutManager(mContext));
        ada = new CzdhMainAdapter(mContext,mData);
        mListview.setAdapter(ada);
        mListview.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.HORIZONTAL, 20, Color.rgb(244,244,244)));//添加分割线

    }
}
