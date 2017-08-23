package com.dk.mp.gzbx.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.gzbx.R;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 故障报修
 * @author dake
 *
 */
public class TroublshootingMainActivity extends MyActivity implements OnClickListener{
	private RelativeLayout mytroublshooting;//我发起的
	private RelativeLayout awaiting_approval;//等待我处理的
	private LinearLayout addmalfunction;//添加故障
	private Map<String,String> map = new HashMap<>();
	private TextView mytroublshooting_text;
	private TextView awaiting_approval_text;
	private TextView addmalfunction_text;
	private TextView mytroublshooting_text2;
	private LinearLayout mytroublshooting2;

	@Override
	protected int getLayoutID() {
		return R.layout.layout_main_troublshooting;
	}

	@Override
	protected void initialize() {
		super.initialize();

		setTitle(R.string.troublshooting);
		initViews();
//		getData();
	}
	
	public void initViews(){
		mytroublshooting = (RelativeLayout) findViewById(R.id.mytroublshooting);
		awaiting_approval = (RelativeLayout) findViewById(R.id.awaiting_approval);
		addmalfunction = (LinearLayout) findViewById(R.id.addmalfunction);
		mytroublshooting2 = (LinearLayout) findViewById(R.id.mytroublshooting2);
		mytroublshooting_text = (TextView) findViewById(R.id.mytroublshooting_text);
		awaiting_approval_text = (TextView) findViewById(R.id.awaiting_approval_text);
		addmalfunction_text = (TextView) findViewById(R.id.addmalfunction_text);
		mytroublshooting_text2 = (TextView) findViewById(R.id.mytroublshooting_text2);
		mytroublshooting.setOnClickListener(this);
		awaiting_approval.setOnClickListener(this);
		addmalfunction.setOnClickListener(this);
	}
	
	public void getData(){
		if(DeviceUtil.checkNet()){

			HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/dclzs", null, new HttpListener<JSONObject>() {
				@Override
				public void onSuccess(JSONObject result) {
					try {
						if (result.getInt("code") == 200){
							map.put("gzbx", result.getJSONObject("data").getString("gzbx"));
							map.put("dwsp", result.getJSONObject("data").getString("dwsp"));
							map.put("type", result.getJSONObject("data").getString("type"));

							if(map.isEmpty()){
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
			showMessage(getString(R.string.net_no2));
			awaiting_approval.setVisibility(View.GONE);
			mytroublshooting_text.setVisibility(View.GONE);
			mytroublshooting2.setOrientation(LinearLayout.VERTICAL);
			addmalfunction.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 20, 0, 0);
			mytroublshooting_text2.setLayoutParams(lp);
			addmalfunction_text.setLayoutParams(lp);
			mytroublshooting_text2.setText("跟踪报修（0）");
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.mytroublshooting){//跟踪报修
			Intent intent = new Intent(TroublshootingMainActivity.this,GzbxTabActivity.class);
			startActivity(intent);
		}else if(v.getId() == R.id.awaiting_approval){//待我审批
			Intent intent = new Intent(TroublshootingMainActivity.this,DwspTabActivity.class);
			startActivity(intent);
		}else if(v.getId() == R.id.addmalfunction){//申请报修
			Intent intent = new Intent(TroublshootingMainActivity.this,AddMalfunctionActivity.class);
			startActivity(intent);
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String type = map.get("type");
				String gzbx = map.get("gzbx");
				String dwsp = map.get("dwsp");
				if("0".equals(type)){
					awaiting_approval.setVisibility(View.GONE);
					mytroublshooting_text.setVisibility(View.GONE);
					mytroublshooting2.setOrientation(LinearLayout.VERTICAL);
					addmalfunction.setOrientation(LinearLayout.VERTICAL);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					lp.setMargins(0, 20, 0, 0);
					mytroublshooting_text2.setLayoutParams(lp);
					addmalfunction_text.setLayoutParams(lp);
					mytroublshooting_text2.setText("跟踪报修（"+gzbx+"）");
				}else{
					awaiting_approval.setVisibility(View.VISIBLE);
					mytroublshooting_text.setVisibility(View.VISIBLE);
					mytroublshooting2.setOrientation(LinearLayout.HORIZONTAL);
					addmalfunction.setOrientation(LinearLayout.HORIZONTAL);
					awaiting_approval_text.setText("（"+dwsp+"）");
					mytroublshooting_text.setText("（"+gzbx+"）");
				}
				
				break;
			case 2:
				showMessage(getString(R.string.data_error));
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}
}
