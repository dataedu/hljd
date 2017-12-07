package com.dk.mp.gzbx.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.dk.mp.core.entity.JsonData;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.Logger;
import com.dk.mp.gzbx.Adapter.MyProcessAdapter;
import com.dk.mp.gzbx.R;
import com.dk.mp.gzbx.entity.Malfunction;
import com.dk.mp.gzbx.entity.ProcessInfo;
import com.dk.mp.gzbx.http.HttpListArray;
import com.dk.mp.gzbx.util.StringUtil;
import com.dk.mp.gzbx.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的已处理报修
 * @author janabo
 *
 */
public class MyTroublshootingProcessActivity extends MyActivity implements OnClickListener{
	private MyProcessAdapter mAdapter;
	private Malfunction m;
	private TextView textView1,textView2;
	private TextView status;
	private TextView address,device,des;
	private MyListView listview;
	private LinearLayout bottom_view;
	private LinearLayout bottom_left;
	private LinearLayout bottom_right;
	private LinearLayout bottom_view2;//指定维修人
	private Context context;
	private String type;
	private Map<String,Object> map;
	private TextView tro_end_repair;
	private String wyfk = "";
	private LinearLayout bximgs;
	private NestedScrollView myscrollview;
	private ArrayList<String> images = new ArrayList<>();


	@Override
	protected int getLayoutID() {
		return R.layout.layout_my_process_troublshooting;
	}

	@Override
	protected void initialize() {
		super.initialize();

		setTitle("跟踪报修");
		context = MyTroublshootingProcessActivity.this;
		findView();
		getData();
	}
	
	private void findView(){
		Intent intent = getIntent();
		m = (Malfunction) intent.getSerializableExtra("malfunction");
		type = intent.getStringExtra("type");
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		tro_end_repair = (TextView) findViewById(R.id.tro_end_repair);
		status = (TextView) findViewById(R.id.status);
		address = (TextView) findViewById(R.id.address);
		device = (TextView) findViewById(R.id.device);
		des = (TextView) findViewById(R.id.des);
		listview = (MyListView) findViewById(R.id.listview);
		bottom_view = (LinearLayout) findViewById(R.id.bottom_view);
		bottom_left = (LinearLayout) findViewById(R.id.bottom_left);
		bottom_right = (LinearLayout) findViewById(R.id.bottom_right);
		bottom_view2 = (LinearLayout) findViewById(R.id.bottom_view2);
		bximgs= (LinearLayout) findViewById(R.id.bximgs);
		myscrollview = (NestedScrollView)findViewById(R.id.mScrollview);
		listview.setDividerHeight(0);
		bottom_view2.setOnClickListener(this);
		bottom_left.setOnClickListener(this);
		bottom_right.setOnClickListener(this);
		if("processed".equals(type)){
			status.setTextColor(getResources().getColor(R.color.red));
		}
		String name = m.getName();
		textView1.setText(StringUtil.dealString(name));
		textView2.setText(m.getTitle());
		status.setText(m.getStatusname());
		address.setText(m.getAddress());
		device.setText(m.getDevice());
		des.setText(m.getDes());

		int screenWidth = DeviceUtil.getScreenWidth(mContext)-DeviceUtil.dip2px(context,20);
		int mHeight = screenWidth/16*9;
		if(m.getTps() != null) {
			for (int i = 0; i < m.getTps().size(); i++) {
				ImageView imageView = new ImageView(this);
				LinearLayout.LayoutParams viewPream = new LinearLayout.LayoutParams(
						screenWidth,
						mHeight
				);//设置布局控件的属性
				viewPream.topMargin = DeviceUtil.dip2px(this, 10);
				viewPream.rightMargin = DeviceUtil.dip2px(this, 10);
				viewPream.leftMargin = DeviceUtil.dip2px(this, 10);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				Glide.with(mContext).load(getUrl(m.getTps().get(i))).into(imageView);//填充图片
				bximgs.addView(imageView, viewPream);
				images.add(getUrl(m.getTps().get(i)));
				final int position = i;
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext, ImagePreviewActivity.class);
						intent.putExtra("index", position);
						intent.putStringArrayListExtra("list", images);
						startActivity(intent);
					}
				});
			}
		}
	}
	
	/**
	 * 获取维修详细信息
	 */
	public void getData(){
		if(DeviceUtil.checkNet()){
			Map<String,Object> _map = new HashMap<String, Object>();
			_map.put("id",  m.getId());
			HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/splc", _map, new HttpListener<JSONObject>() {
				@Override
				public void onSuccess(JSONObject result) {
					try {
						if (result.getInt("code") == 200){
							map = HttpListArray.getSplc(result);
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
		}else {
			showErrorMsg(getString(R.string.net_no2));
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(mAdapter == null){
					@SuppressWarnings("unchecked")
					List<ProcessInfo> list = (List<ProcessInfo>) map.get("pinfo");
					if("processed".equals(type)){
						mAdapter = new MyProcessAdapter(context, list);
						listview.setAdapter(mAdapter);
					}else{
						String operation = (String) map.get("operation");
						String operateState = (String)map.get("operateState");
						String lc = list.get(list.size()-1).getLc();
						if("3".equals(lc)){
							tro_end_repair.setText(R.string.tro_end_repair);
							ProcessInfo pinfo = new ProcessInfo();
							pinfo.setId("0");
							pinfo.setName("现教中心");
							pinfo.setStatus("-1");
							pinfo.setStatusname("已维修，待提报人员反馈");
							pinfo.setTime(list.get(list.size()-1).getTime());
							list.add(pinfo);
							mAdapter = new MyProcessAdapter(context, list);
							listview.setAdapter(mAdapter);
						}else if("1".equals(lc)){
							ProcessInfo pinfo = new ProcessInfo();
							pinfo.setId("0");
							pinfo.setName("现教中心");
							pinfo.setStatus("-1");
							pinfo.setStatusname("待审批");
							pinfo.setTime(list.get(list.size()-1).getTime());
							list.add(pinfo);
							mAdapter = new MyProcessAdapter(context, list);
							listview.setAdapter(mAdapter);
						}else if("2".equals(lc)){
							ProcessInfo pinfo = new ProcessInfo();
							pinfo.setId("0");
							pinfo.setName("现教中心");
							pinfo.setStatus("-1");
							pinfo.setStatusname("待维修人员维修");
							pinfo.setTime(list.get(list.size()-1).getTime());
							list.add(pinfo);
//							list.get(list.size()-1).setStatus("-1");
							mAdapter = new MyProcessAdapter(context, list);
							listview.setAdapter(mAdapter);
						}else if("4".equals(lc)){
							mAdapter = new MyProcessAdapter(context, list);
							listview.setAdapter(mAdapter);
						}
						if("0".equals(operation) && "1".equals(operateState)){
							bottom_view.setVisibility(View.VISIBLE);
						}else{
							bottom_view.setVisibility(View.GONE);
						}
					}
				}
				myscrollview.scrollTo(0,0);
				break;
			case 2:
				showMessage(getString(R.string.data_error));
				myscrollview.scrollTo(0,0);
				break;
			case 3:
				finish();
				BroadcastUtil.sendBroadcast(context, "com.test.action.refresh");
				BroadcastUtil.sendBroadcast(context, "com.test.action.refresh.tab");
				showMessage("提交成功");
				break;
			case 4:
				showMessage("提交失败，请重试");
				break;
			default:
				break;
			}
		};
	};
	
	public void alertDialog(String title){
		final Dialog dlg = new Dialog(context,R.style.repair_MyDialog);
		Window window = dlg.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.setContentView(R.layout.repair_dialog);
		dlg.show();
		TextView titleView = (TextView) window.findViewById(R.id.title);
		titleView.setText(title);
		final EditText contentView = (EditText) window.findViewById(R.id.content);
		contentView.setText(wyfk);
		contentView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(contentView.getText().toString().trim().length() >= 140){
					showMessage("请填写少于140个字");
				}
			}
		});
		final Button ok = (Button) window.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ok.setEnabled(false);
				final String contextText = contentView.getText().toString().trim();
				wyfk = contextText;
				if(contextText.length()<=0){
					showMessage("请您填写维修反馈情况");
					ok.setEnabled(true);
					return ;
				}else if(contextText.length()>140){
					showMessage("请填写少于140个字");
					ok.setEnabled(true);
					return;
				}else{
					Logger.info("长度"+contextText.length());
					if(DeviceUtil.checkNet()){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("type", "fkxx");
						String message = "维修反馈:"+contextText;
						map.put("id", m.getId());
						map.put("message", message);
						HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/tbryfkxx", map, new HttpListener<JSONObject>() {
							@Override
							public void onSuccess(JSONObject result) {
								JsonData jd = getGson().fromJson(result.toString(),JsonData.class);
								if (jd.getCode() == 200 && (Boolean) jd.getData()) {
                                    mHandler.sendEmptyMessage(3);
                                    ok.setEnabled(true);
                                }else{
                                    mHandler.sendEmptyMessage(4);
                                    ok.setEnabled(true);
                                }
							}

							@Override
							public void onError(VolleyError error) {
								showMessage(getString(R.string.data_error));
								ok.setEnabled(true);
							}
						});
					}else{
						ok.setEnabled(true);
						dlg.hide();
					}
				}
			}
		});
		Button cancel = (Button) window.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ok.setEnabled(true);
				dlg.cancel();
			}
		});
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.bottom_view2){
			Intent intent = new Intent(MyTroublshootingProcessActivity.this,ContactPersonActivity.class);
			startActivityForResult(intent, 1);
		}else if(v.getId() == R.id.bottom_right){
			alertDialog("请填写维修反馈情况");
		}else if(v.getId() == R.id.bottom_left){
			if(DeviceUtil.checkNet()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("type", "ywx");
				String message = "";
				map.put("id", m.getId());
				map.put("message", message);
				HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/tbryfkxx", map, new HttpListener<JSONObject>() {
					@Override
					public void onSuccess(JSONObject result) {
						JsonData jd = getGson().fromJson(result.toString(),JsonData.class);
						if (jd.getCode() == 200 && (Boolean) jd.getData()) {
							mHandler.sendEmptyMessage(3);
						}else{
							mHandler.sendEmptyMessage(4);
						}
					}

					@Override
					public void onError(VolleyError error) {
						showMessage(getString(R.string.data_error));
					}
				});
			}
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.info("RESULT_OK");
		Logger.info("requestCode：" + requestCode);
		if (resultCode == RESULT_OK) {
			String userid = data.getStringExtra("userid");
			Logger.info("userid:"+userid);
			bottom_view2.setVisibility(View.GONE);
		}
	}

	/**
	 * 处理url
	 * @param url
	 * @return
	 */
	private String getUrl(String url) {
		if (url.startsWith("http://") || url.startsWith("https://")) {
			return url;
		} else {
			return getReString(R.string.rootUrl) + url;
		}
	}
	
}
