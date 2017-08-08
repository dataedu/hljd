package com.dk.mp.xsxx;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.view.RecycleViewDivider;
import com.dk.mp.core.widget.ErrorLayout;
import com.dk.mp.xsxx.adapter.KqInfomationAdapter;
import com.dk.mp.xsxx.entity.KqInfimationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cobb on 2017/8/8.
 */

public class KqInfomationActivity extends MyActivity{

    private ErrorLayout mError;
    private RecyclerView myListView;
    private List<KqInfimationEntity> mData = new ArrayList<>();
    private KqInfomationAdapter ada;

    private String type = "";

    @Override
    protected int getLayoutID() {
        return R.layout.ac_kq_infomation;
    }

    @Override
    protected void initialize() {
        super.initialize();

        type = getIntent().getStringExtra("type");
        Log.e("3333333333333333",type);
        if (type.equals("0")){
            setTitle("上课考勤信息查询");
        }else if (type.equals("1")){
            setTitle("课程");
        }else {
            setTitle("班级");
        }

        initpage();
    }

    @Override
    protected void initView() {
        super.initView();

        mError = (ErrorLayout) findViewById(R.id.error_layout);
        myListView = (RecyclerView) findViewById(R.id.mListview);

    }

    private void initpage() {

        if (type.equals("0")){
            mData.add(new KqInfimationEntity("安卓应用开发","","2017-09-0912:00","3","2","12","3"));
            mData.add(new KqInfimationEntity("安卓应用开发2","","2017-09-0912:00","3","2","12","3"));
            mData.add(new KqInfimationEntity("安卓应用开发3","","2017-09-0912:00","3","2","12","3"));
        }else if (type.equals("1")){
            mData.add(new KqInfimationEntity("白浅","201223843","安卓开发1班","3","2","12","3"));
            mData.add(new KqInfimationEntity("白浅","201223843","安卓开发1班","3","2","12","3"));
            mData.add(new KqInfimationEntity("白浅","201223843","安卓开发1班","3","2","12","3"));
        }else {
            mData.add(new KqInfimationEntity("夜华","201223843","","3","2","12","3"));
            mData.add(new KqInfimationEntity("夜华","201223843","","3","2","12","3"));
            mData.add(new KqInfimationEntity("夜华","201223843","","3","2","12","3"));
        }

        myListView.setHasFixedSize(true);
        myListView.setLayoutManager(new LinearLayoutManager(mContext));
        ada = new KqInfomationAdapter(mData,mContext,type);
        myListView.setAdapter(ada);
        myListView.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.HORIZONTAL, 20, Color.rgb(244,244,244)));//添加分割线

    }

}
