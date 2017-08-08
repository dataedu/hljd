package com.dk.mp.gwsq;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dk.mp.core.entity.JsonData;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.TimeUtils;
import com.dk.mp.core.view.DrawCheckMarkView;
import com.dk.mp.core.view.DrawCrossMarkView;
import com.dk.mp.core.view.DrawHookView;
import com.dk.mp.core.view.RecycleViewDivider;
import com.dk.mp.gwsq.adapter.JobChoseAdapter;
import com.dk.mp.gwsq.adapter.WeekChoseAdapter;
import com.dk.mp.gwsq.entity.JobChooseEntity;
import com.dk.mp.gwsq.entity.WeekEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cobb on 2017/8/4.
 */

public class JobApplyActivity extends MyActivity implements WeekChoseAdapter.OnItemClickListener{

    private EditText sqly; //申请理由
    private TextView tv_left_num;

    private LinearLayout ok;  //提交按钮
    private DrawHookView progress;
    private DrawCheckMarkView progress_check;
    private DrawCrossMarkView progress_cross;
    private TextView ok_text;

    private RecyclerView weeksView; //星期
    private WeekChoseAdapter weekChoseAdapter;
    private List<WeekEntity> weekEntityList = new ArrayList<>();
    private int select = 0;

    private RecyclerView jobView;  //节次名称
    private JobChoseAdapter jobAdapter;
    private List<JobChooseEntity> jobList = new ArrayList<>();

    @Override
    protected int getLayoutID() {
        return R.layout.ac_job_apply;
    }

    @Override
    protected void initialize() {
        super.initialize();

        setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    protected void initView() {
        super.initView();

        sqly = (EditText) findViewById(R.id.sqly);
        tv_left_num = (TextView) findViewById(R.id.tv_left_num);
        sqly.addTextChangedListener(mTextWatcher);

        ok = (LinearLayout) findViewById(R.id.ok);
        progress = (DrawHookView) findViewById(R.id.progress);
        progress_check = (DrawCheckMarkView) findViewById(R.id.progress_check);
        progress_cross = (DrawCrossMarkView) findViewById(R.id.progress_cross);
        ok_text = (TextView) findViewById(R.id.ok_text);
        ok.setEnabled(false);

        getWeek();
        weeksView = (RecyclerView) findViewById(R.id.weeksView);
        weeksView.setHasFixedSize ( true );
        weeksView.setLayoutManager(new GridLayoutManager(mContext,weekEntityList.size()));
        weekChoseAdapter = new WeekChoseAdapter(mContext,weekEntityList,select);
        weeksView.setAdapter(weekChoseAdapter);

        getJob();
        jobView = (RecyclerView) findViewById(R.id.jobViews);
        jobView.setHasFixedSize ( true );
        jobView.setLayoutManager(new GridLayoutManager(mContext,3));
//        jobView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.bg)));
        jobAdapter = new JobChoseAdapter(mContext,jobList);
        jobView.setAdapter(jobAdapter);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int len = charSequence.length();
            tv_left_num.setText(len + "/200");

        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void getWeek() {

        weekEntityList.add(new WeekEntity("周一"));
        weekEntityList.add(new WeekEntity("周二"));
        weekEntityList.add(new WeekEntity("周三"));
        weekEntityList.add(new WeekEntity("周四"));
        weekEntityList.add(new WeekEntity("周五"));
    }

    @Override
    public void onItemClick(View view, int position) {
        select = position;
        weekChoseAdapter.setSelected(select);
        weekChoseAdapter.notifyDataSetChanged();
    }

    private void getJob() {
        jobList.add(new JobChooseEntity("1","早自习"));
        jobList.add(new JobChooseEntity("2","早自习"));
        jobList.add(new JobChooseEntity("3","早自习"));
        jobList.add(new JobChooseEntity("4","早自习"));
        jobList.add(new JobChooseEntity("5","早自习"));
        jobList.add(new JobChooseEntity("6","早自习"));
        jobList.add(new JobChooseEntity("7","早自习"));
        jobList.add(new JobChooseEntity("8","早自习"));
    }

    /**
     * 设置ok按钮的样式
     */
    public void dealOkButton(){
        if(sqly.getText().toString().length()>0){
            ok.setBackground(getResources().getDrawable(R.drawable.ripple_bg));
            ok.setEnabled(true);
        }else{
            ok.setBackgroundColor(getResources().getColor(R.color.rcap_gray));
            ok.setEnabled(false);
        }
    }

    private void errorInfo(){
        progress.setVisibility(View.GONE);
        progress_cross.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {//等待成功动画结束
            @Override
            public void run() {
                progress_cross.setVisibility(View.GONE);
                ok_text.setVisibility(View.VISIBLE);
                ok.setEnabled(true);
            }
        },1000);
    }

    /**
     * 提交
     * @param v
     */
    public void submitLiusu(View v){

        Map<String,Object> map = new HashMap<>();
        map.put("index",1);
        map.put("jcid",1);
        map.put("sqly",sqly.getText().toString());

        ok_text.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        HttpUtil.getInstance().postJsonObjectRequest("apps/tjgwkxsj/tj", map, new HttpListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result)  {
                try {
                    JsonData jd = getGson().fromJson(result.toString(),JsonData.class);
                    if (jd.getCode() == 200 && (Boolean) jd.getData()) {
                        progress.setVisibility(View.GONE);
                        progress_check.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {//等待成功动画结束
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                ok.setEnabled(true);
                                BroadcastUtil.sendBroadcast(mContext, "zssdjgl_refresh");
                                onBackPressed();
                            }
                        },1500);
                    }else{
                        showErrorMsg(jd.getMsg());
                        errorInfo();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    errorInfo();
                }
            }
            @Override
            public void onError(VolleyError error) {
                errorInfo();
            }
        });
    }

}
