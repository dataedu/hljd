package com.mp.dk.cwyd;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dk.mp.core.dialog.MsgDialog;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.widget.ErrorLayout;
import com.google.gson.Gson;
import com.mp.dk.cwyd.entity.RoomDetailEntity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cobb on 2017/8/3.
 */

public class RoomDetailActivity extends MyActivity {

    private TextView kssj,jssj,xm,xh,yx,bj,tsqk,sqly;
    private String mtype = "1";
    private RelativeLayout tsqkIv;
    private ErrorLayout errorLayout;

    private String mUrl = "";

    @Override
    protected int getLayoutID() {
        return R.layout.ac_room_detail;
    }

    @Override
    protected void initialize() {
        super.initialize();

        mtype = getIntent().getStringExtra("type");
        if (mtype.equals("1")){
            tsqkIv.setVisibility(View.GONE);
            setTitle("退宿申请");
        }else {
            tsqkIv.setVisibility(View.VISIBLE);
            setTitle("调宿申请");
        }

        if (DeviceUtil.checkNet()){
            initData();
        }else {
            errorLayout.setErrorType(ErrorLayout.NETWORK_ERROR);
        }

    }

    @Override
    protected void initView() {
        super.initView();

        kssj = (TextView) findViewById(R.id.kssj);
        jssj = (TextView) findViewById(R.id.jssj);
        xm = (TextView) findViewById(R.id.xm);
        xh = (TextView) findViewById(R.id.xh);
        yx = (TextView) findViewById(R.id.yx);
        bj = (TextView) findViewById(R.id.bj);
        tsqk = (TextView) findViewById(R.id.tsqk);
        sqly = (TextView) findViewById(R.id.sqly);

        tsqkIv = (RelativeLayout) findViewById(R.id.tsqkI);
        errorLayout = (ErrorLayout) findViewById(R.id.error_layout);
    }

    private void initData() {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",getIntent().getStringExtra("id"));

        if (mtype.equals(1)){
            mUrl = "apps/ssyd/tuisuxiangqing";
        }else {
            mUrl = "apps/ssyd/tiaosuxiangqing";
        }

        HttpUtil.getInstance().postJsonObjectRequest( mUrl, map, new HttpListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result)  {
                try {
                    if (result.getInt("code") != 200) {
                        errorLayout.setErrorType(ErrorLayout.DATAFAIL);
                    }else{
                        RoomDetailEntity room = new Gson().fromJson(result.getJSONObject("data").toString(),RoomDetailEntity.class);
                        if (room != null){

                            kssj.setText(room.getKssj());
                            jssj.setText(room.getJssj());
                            xm.setText(room.getXm());
                            xh.setText(room.getXh());
                            yx.setText(room.getYx());
                            bj.setText(room.getBj());
                            sqly.setText(room.getSqly());

                            if (mtype.equals("2")){
                                tsqk.setText(room.getTsqk());
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
//                    MsgDialog.show(mContext, getString(R.string.data_fail));
//                    setUserindo();
                    errorLayout.setErrorType(ErrorLayout.DATAFAIL);
                }
            }
            @Override
            public void onError(VolleyError error) {
                errorLayout.setErrorType(ErrorLayout.DATAFAIL);
            }
        });

    }
}
