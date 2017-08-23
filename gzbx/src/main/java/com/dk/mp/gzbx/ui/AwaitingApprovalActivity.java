package com.dk.mp.gzbx.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.view.tab.MyTabActivity;
import com.dk.mp.gzbx.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AwaitingApprovalActivity extends MyTabActivity {
	public static final String ACTION_REFRESH = "com.test.action.refresh.tab";
	private Context context = AwaitingApprovalActivity.this;
	private int wCount = 0;
    public static AwaitingApprovalActivity instance = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("待我审批",true);
		instance = this;
		getData(1);
		BroadcastUtil.registerReceiver(context, receiver, ACTION_REFRESH);
	}
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (ACTION_REFRESH.equals(intent.getAction())) {
				if(DeviceUtil.checkNet()){
					getData(2);
				}
			}
		}
	};

	public void getData(final int par){
		if(DeviceUtil.checkNet()){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "wait");
			map.put("pageNo", "1");

			HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/wdsp", map, new HttpListener<JSONObject>() {
				@Override
				public void onSuccess(JSONObject result) {
					try {
						if (result.getInt("code") != 200){
							showMessage(getString(R.string.data_error));
						}else {
							wCount = (Integer) result.getJSONObject("data").get("totalCount");
							if(par == 2){
								mHandler.sendEmptyMessage(2);
							}else{
								mHandler.sendEmptyMessage(1);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
						showMessage(getString(R.string.data_error));
					}
				}

				@Override
				public void onError(VolleyError error) {
					showMessage(getString(R.string.data_error));
				}
			});
		}else{
			mHandler.sendEmptyMessage(1);
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				List<String> names = new ArrayList<String>();
				List<Intent> intents = new ArrayList<Intent>();
				
				Intent intent3 = new Intent(AwaitingApprovalActivity.this,MyTroublshootingListActivity.class);
				intent3.putExtra("type", "wait");
				intent3.putExtra("approvalorinitiate", 1);
				intents.add(intent3);
				names.add("待我审批（"+wCount+"）");
				Intent intent4 = new Intent(AwaitingApprovalActivity.this,MyTroublshootingListActivity.class);
				intent4.putExtra("type", "processed");
				intent4.putExtra("approvalorinitiate", 1);
				intents.add(intent4);
				names.add("我已审批");
				setTabs(names, intents);
				
				break;
			case 2:
				List<String> ns = new ArrayList<String>();
				ns.add("待我审批（"+wCount+"）");
				ns.add("我已审批");

				updateRadioText(ns);
				break;
			default:
				break;
			}
		};
	};

}
