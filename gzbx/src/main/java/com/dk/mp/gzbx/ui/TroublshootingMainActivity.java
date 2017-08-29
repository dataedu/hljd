package com.dk.mp.gzbx.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
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
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.widget.ErrorLayout;
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
	private RelativeLayout addmalfunction;//添加故障

	private Map<String,String> map = new HashMap<>();

	private LinearLayout mytroublshooting2;
	private LinearLayout mytroublshooting3;
	private TextView mytroublshooting_text2;
	private TextView mytroublshooting_text3;

	private LinearLayout addmalfunction2;
	private LinearLayout addmalfunction3;
	private TextView addmalfunction_text2;
	private TextView addmalfunction_text3;

	private TextView awaiting_approval_text;

	private ErrorLayout errorLayout;

	public static final String ACTION_REFRESH = "com.test.action.refresh";

	@Override
	protected int getLayoutID() {
		return R.layout.layout_main_troublshooting;
	}

	@Override
	protected void initialize() {
		super.initialize();

		setTitle(R.string.troublshooting);
		initViews();

		getData();
		BroadcastUtil.registerReceiver(mContext, receiver, new String[]{"checknetwork_true",ACTION_REFRESH});
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (ACTION_REFRESH.equals(intent.getAction()) || "checknetwork_true".equals(intent.getAction())) {
				getData();
			}
		}
	};
	
	public void initViews(){
		errorLayout = (ErrorLayout) findViewById(R.id.error_layout);

		mytroublshooting = (RelativeLayout) findViewById(R.id.mytroublshooting);
		awaiting_approval = (RelativeLayout) findViewById(R.id.awaiting_approval);
		addmalfunction = (RelativeLayout) findViewById(R.id.addmalfunction);

		mytroublshooting2 = (LinearLayout) findViewById(R.id.mytroublshooting2);
		mytroublshooting3 = (LinearLayout) findViewById(R.id.mytroublshooting3);
		mytroublshooting_text2 = (TextView) findViewById(R.id.mytroublshooting_text2);
		mytroublshooting_text3 = (TextView) findViewById(R.id.mytroublshooting_text3);

		awaiting_approval_text = (TextView) findViewById(R.id.awaiting_approval_text);

		addmalfunction2 = (LinearLayout) findViewById(R.id.addmalfunction2);
		addmalfunction3 = (LinearLayout) findViewById(R.id.addmalfunction3);
		addmalfunction_text2 = (TextView) findViewById(R.id.addmalfunction_text2);
		addmalfunction_text3 = (TextView) findViewById(R.id.addmalfunction_text3);


		mytroublshooting.setOnClickListener(this);
		awaiting_approval.setOnClickListener(this);
		addmalfunction.setOnClickListener(this);
	}
	
	public void getData(){
		errorLayout.setErrorType(ErrorLayout.LOADDATA);
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
					errorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
				}

				@Override
				public void onError(VolleyError error) {
					showMessage(getString(R.string.data_error));
					errorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
				}
			});
		}else{
			errorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
			showMessage(getString(R.string.net_no2));

			awaiting_approval.setVisibility(View.GONE);

			mytroublshooting2.setVisibility(View.VISIBLE);
			mytroublshooting3.setVisibility(View.GONE);
			mytroublshooting_text2.setText("跟踪报修（0）");

			addmalfunction2.setVisibility(View.VISIBLE);
			addmalfunction3.setVisibility(View.GONE);
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

					mytroublshooting2.setVisibility(View.VISIBLE);
					mytroublshooting3.setVisibility(View.GONE);
					mytroublshooting_text2.setText("跟踪报修（"+gzbx+"）");

					addmalfunction2.setVisibility(View.VISIBLE);
					addmalfunction3.setVisibility(View.GONE);
				}else{
					awaiting_approval.setVisibility(View.VISIBLE);
					awaiting_approval_text.setText("（"+dwsp+"）");

					mytroublshooting2.setVisibility(View.GONE);
					mytroublshooting3.setVisibility(View.VISIBLE);
					mytroublshooting_text3.setText("跟踪报修（"+gzbx+"）");

					addmalfunction2.setVisibility(View.GONE);
					addmalfunction3.setVisibility(View.VISIBLE);
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

//		getData();

	}
}
