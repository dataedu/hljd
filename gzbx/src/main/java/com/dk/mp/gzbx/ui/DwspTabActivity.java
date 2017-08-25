package com.dk.mp.gzbx.ui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;

import com.android.volley.VolleyError;
import com.dk.mp.core.adapter.MyFragmentPagerAdapter;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.BaseFragment;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.widget.MyViewpager;
import com.dk.mp.gzbx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cobb on 2017/8/22.
 */

public class DwspTabActivity extends MyActivity{

    public static final String ACTION_REFRESH = "com.test.action.refresh";

    TabLayout mTabLayout;
    MyViewpager mViewpager;

    @Override
    protected int getLayoutID() {
        return R.layout.ac_tab_gzbx;
    }

    @Override
    protected void initialize() {
        super.initialize();

        setTitle("待我审批");
        findView();

        initViewPager();

        getData();
        BroadcastUtil.registerReceiver(mContext, receiver, ACTION_REFRESH);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_REFRESH.equals(intent.getAction())) {
                getData();
            }
        }
    };
    public void getData(){
        if(DeviceUtil.checkNet()){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("type", "wait");
            map.put("pageNo", "1");

            HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/wdsp", map, new HttpListener<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        if (result.getInt("code") == 200){
                            int wCount = (Integer) result.getJSONObject("data").get("totalCount");
                            mTabLayout.getTabAt(0).setText("待我审批（"+wCount+")");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        showMessage(getString(R.string.data_error));
                    }
                }

                @Override
                public void onError(VolleyError error) {
//                    showMessage(getString(R.string.data_error));
                }
            });
        }
    }

    private void findView(){
        mTabLayout = (TabLayout) findViewById(R.id.id_stickynavlayout_indicator);
        mViewpager = (MyViewpager) findViewById(R.id.id_stickynavlayout_viewpager);
    }

    private void initViewPager(){
        List<String>  titles = new ArrayList<>();
        titles.add("待我审批（0)");
        titles.add("我已审批");

        for(int i=0;i<titles.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<BaseFragment> fragments = new ArrayList<>();

        fragments.add(GzbxListActivity.newInstance("wait",1));
        fragments.add(GzbxListActivity.newInstance("processed",1));

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,titles);
        mViewpager.setOffscreenPageLimit ( fragments.size ( ) );
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);

    }

}
