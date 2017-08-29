package com.dk.mp.gzbx.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dk.mp.core.dialog.MsgDialog;
import com.dk.mp.core.entity.JsonData;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.Logger;
import com.dk.mp.core.view.DrawCheckMarkView;
import com.dk.mp.core.view.DrawCrossMarkView;
import com.dk.mp.core.view.DrawHookView;
import com.dk.mp.gzbx.R;

import org.json.JSONObject;


/**
 * 申请报修
 * @author janabo
 *
 */
public class AddMalfunctionActivity extends MyActivity{
	private EditText tro_address;//地点
	private EditText tro_dev;//设备
	private EditText tro_problem;//问题描述

	private LinearLayout ok;  //提交按钮
	private DrawHookView progress;
	private DrawCheckMarkView progress_check;
	private DrawCrossMarkView progress_cross;
	private TextView ok_text;

	@Override
	protected int getLayoutID() {
		return R.layout.layout_add_malfunction;
	}

	@Override
	protected void initialize() {
		super.initialize();

		setTitle("申请报修");
		findView();
	}
	
	private void findView(){
		ok = (LinearLayout) findViewById(R.id.ok);
		progress = (DrawHookView) findViewById(R.id.progress);
		progress_check = (DrawCheckMarkView) findViewById(R.id.progress_check);
		progress_cross = (DrawCrossMarkView) findViewById(R.id.progress_cross);
		ok_text = (TextView) findViewById(R.id.ok_text);

//		ok.setEnabled(false);

		tro_address = (EditText) findViewById(R.id.tro_address);
		tro_dev = (EditText) findViewById(R.id.tro_dev);
		tro_problem = (EditText) findViewById(R.id.tro_problem);
		SpannableString ss = new SpannableString(getResources().getString(R.string.tro_address_hint));
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12,true);
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tro_address.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
		SpannableString ssd = new SpannableString(getResources().getString(R.string.tro_dev_hint));
		AbsoluteSizeSpan assd = new AbsoluteSizeSpan(12,true);
		ssd.setSpan(assd, 0, ssd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tro_dev.setHint(new SpannedString(ssd)); // 一定要进行转换,否则属性会消失
		SpannableString ssp = new SpannableString(getResources().getString(R.string.tro_problemdescription_hint));
		AbsoluteSizeSpan assp = new AbsoluteSizeSpan(12,true);
		ssp.setSpan(assp, 0, ssp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tro_problem.setHint(new SpannedString(ssp)); // 一定要进行转换,否则属性会消失

		tro_problem.addTextChangedListener(mTextWatcher3);
		tro_dev.addTextChangedListener(mTextWatcher2);
		tro_address.addTextChangedListener(mTextWatcher1);
	}

	/**
	 * 设置ok按钮的样式
	 */
	public void dealOkButton(){
		if(tro_address.getText().toString().length()>0 && tro_dev.getText().toString().length()>0 && tro_problem.getText().toString().length()>0){
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

	public void submitLiusu(View v) {
		final String addressText = tro_address.getText().toString().trim();
		final String devText = tro_dev.getText().toString().trim();
		final String proText = tro_problem.getText().toString().trim();
		if(addressText.length()<=0){
			showMessage("请填写地点");
			return;
		}
		if(devText.length()<=0){
			showMessage("请填写设备");
			return;
		}
		if(proText.length()<=0){
			showMessage("请填写问题描述");
			return;
		}
		Logger.info("地址长度："+addressText.length()+":"+devText.length());
		if(addressText.length()>50 || devText.length()>50){
			showMessage("请填写少于50个字");
			return;
		}
		if(proText.length()>200){
			showMessage("请填写少于200个字");
			return;
		}

		ok_text.setVisibility(View.GONE);
		progress.setVisibility(View.VISIBLE);

		if(DeviceUtil.checkNet()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("dd", addressText);
			map.put("sb", devText);
			map.put("wtms", proText);

			HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/tjbx", map, new HttpListener<JSONObject>() {
				@Override
				public void onSuccess(JSONObject result) {
					JsonData jd = getGson().fromJson(result.toString(),JsonData.class);
					if (jd.getCode() == 200 && (Boolean) jd.getData()) {
						progress.setVisibility(View.GONE);
						progress_check.setVisibility(View.VISIBLE);
						new Handler().postDelayed(new Runnable() {//等待成功动画结束
							@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
							@Override
							public void run() {
								ok.setEnabled(true);
								showMessage("提交成功");
								BroadcastUtil.sendBroadcast(mContext, "com.test.action.refresh");
								onBackPressed();
							}
						},1500);
                    }else{
						showErrorMsg(jd.getMsg());
						errorInfo();
                    }
				}
				@Override
				public void onError(VolleyError error) {
					errorInfo();
				}
			});
		}else {
			showErrorMsg(getString(R.string.net_no2));
			errorInfo();
		}
		
	}

	TextWatcher mTextWatcher1 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
//			dealOkButton();
			if(tro_address.getText().toString().trim().length() >= 50){
				showMessage("地点不能大于50个字");
			}
		}
	};
	
	TextWatcher mTextWatcher2 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
//			dealOkButton();
			if(tro_dev.getText().toString().trim().length() >= 50){
				showMessage("设备不能大于50个字");
			}
		}
	};
	TextWatcher mTextWatcher3 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
//			dealOkButton();
			if(tro_problem.getText().toString().trim().length() >= 200){
				showMessage("问题描述不能大于200个字");
			}
		}
	};
}
