package com.mp.dk.cwyd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dk.mp.core.entity.GsonData;
import com.dk.mp.core.entity.JsonData;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.StringUtils;
import com.dk.mp.core.util.TimeUtils;
import com.dk.mp.core.view.DrawCheckMarkView;
import com.dk.mp.core.view.DrawCrossMarkView;
import com.dk.mp.core.view.DrawHookView;
import com.dk.mp.core.widget.ErrorLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mp.dk.cwyd.entity.TsqkEntity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cobb on 2017/8/2.
 */

public class RoomaApplicationActivity extends MyActivity{

    private LinearLayout ok;
    private DrawHookView progress;
    private DrawCheckMarkView progress_check;
    private DrawCrossMarkView progress_cross;
    private TextView ok_text;

    private TextView ksrq_pick, jsrq_pick, tsqk_pick; //开始时间，结束时间，调宿情况
    private LinearLayout tsqkLv; //退宿

    private EditText sqly; //申请理由
    private TextView tv_left_num;

    private String type = "1";//1:退宿申请，2：调宿申请

    //调宿情况
    List<TsqkEntity> tsqk = new ArrayList<>();
    private String tsqkid = "";
    private String tsqkmc = "";

    @Override
    protected int getLayoutID() {
        return R.layout.ac_room_application;
    }

    @Override
    protected void initView() {
        super.initView();

        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ok = (LinearLayout) findViewById(R.id.ok);
        progress = (DrawHookView) findViewById(R.id.progress);
        progress_check = (DrawCheckMarkView) findViewById(R.id.progress_check);
        progress_cross = (DrawCrossMarkView) findViewById(R.id.progress_cross);
        ok_text = (TextView) findViewById(R.id.ok_text);

        jsrq_pick = (TextView) findViewById(R.id.jsrq_pick);
        ksrq_pick = (TextView) findViewById(R.id.ksrq_pick);
        tsqk_pick = (TextView) findViewById(R.id.tsqk_pick);
        tsqkLv = (LinearLayout) findViewById(R.id.tsqkLv);

        sqly = (EditText) findViewById(R.id.sqly);
        tv_left_num = (TextView) findViewById(R.id.tv_left_num);

        jsrq_pick.addTextChangedListener(mTextWatcher);
        ksrq_pick.addTextChangedListener(mTextWatcher);
        sqly.addTextChangedListener(mTextWatcher);

        type = getIntent().getStringExtra("type");
        if("1".equals(type)) {
            setTitle("退宿申请");
            tsqkLv.setVisibility(View.GONE);
        }else{
            setTitle("调宿申请");
            tsqkLv.setVisibility(View.VISIBLE);
        }

        ok.setEnabled(false);

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
            dealOkButton();
        }
    };

    /**
     * 设置ok按钮的样式
     */
    public void dealOkButton(){
        if(jsrq_pick.getText().toString().length()>0 && ksrq_pick.getText().toString().length()>0 && sqly.getText().toString().length()>0){
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
     * 开始日期
     * @param v
     */
    public void toPickKsrq(View v){
        Intent intent = new Intent(mContext, TimePickActivity.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    /**
     * 结束日期
     * @param v
     */
    public void toPickJsrq(View v){
        Intent intent = new Intent(mContext, TimePickActivity.class);
        startActivityForResult(intent, 2);
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    /**
     * 调宿情况
     */
    public void toPickTsqk(View v){
        if (tsqk.size()>0){
            toPickActivity(tsqk);
        }else {
            if (DeviceUtil.checkNet()){
                getTsqk();
            }else {
                showErrorMsg(R.string.net_no2+"");
            }
        }

    }

    private void getTsqk() {

        tsqk.add(new TsqkEntity("1","不想住了"));
        tsqk.add(new TsqkEntity("1","不想住了"));
        tsqk.add(new TsqkEntity("1","不想住了"));
        tsqk.add(new TsqkEntity("1","不想住了"));
        tsqk.add(new TsqkEntity("1","不想住了"));
        toPickActivity(tsqk);


        /*tsqk.clear();
        Map<String,Object> map = new HashMap<>();
        HttpUtil.getInstance().postJsonObjectRequest("apps/ssyd/tiaosuqingkuang", map, new HttpListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if(result != null) {
                        GsonData<TsqkEntity> gsonData = new Gson().fromJson(result.toString(), new TypeToken<GsonData<TsqkEntity>>() {
                        }.getType());
                        if (gsonData.getCode() == 200) {
                            List<TsqkEntity> dfxxes = gsonData.getData();
                            if(dfxxes.size()>0){//获取数据不为空
                                tsqk.addAll(dfxxes);
                                toPickActivity(tsqk);
                            }else{
                                showErrorMsg("获取调宿情况为空");
                            }
                        } else {
                            showErrorMsg("获取调宿情况为空");
                        }
                    }else{
                        showErrorMsg("获取调宿情况为空");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorMsg("获取调宿情况为空");
                }
            }
            @Override
            public void onError(VolleyError error) {
                showErrorMsg("获取调宿情况为空");
            }
        });*/
    }

    public void toPickActivity(List<TsqkEntity> picks){
        Intent intent = new Intent(mContext, TsqkPickActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("kfs", (Serializable) picks);
        intent.putExtras(bundle);
        startActivityForResult(intent, 3);
//        overridePendingTransition(R.anim.push_up_in, 0);
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String mWjrq = data.getStringExtra("date");
                    ksrq_pick.setText(mWjrq);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    String mWjrq = data.getStringExtra("date");
                    jsrq_pick.setText(mWjrq);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK){
                    String mtsqkmc = data.getStringExtra("tsqkmc");
                    String mtsqkid = data.getStringExtra("tsqkid");

                    tsqkmc = mtsqkmc;
                    tsqk_pick.setText(mtsqkmc);
                    tsqkid = mtsqkid;
                }
                break;
        }
    }

    /**
     * 提交
     * @param v
     */
    public void submitLiusu(View v){
        if(!TimeUtils.comparedDate(ksrq_pick.getText().toString(),jsrq_pick.getText().toString())){
            showErrorMsg("开始时间不能大于结束时间");
            return ;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("kssj",ksrq_pick.getText().toString());
        map.put("jssj",jsrq_pick.getText().toString());
        map.put("sqly",type);

        String murl = "";
        if (type.equals("1")){
            murl = "apps/ssyd/tijiaotuisu";
        }else {
            map.put("tsqkid",tsqkid);
            murl = "apps/ssyd/tijiaotiaosu";
        }

        ok_text.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        HttpUtil.getInstance().postJsonObjectRequest(murl, map, new HttpListener<JSONObject>() {
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
