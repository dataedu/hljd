package com.dk.mp.gzbx.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dk.mp.core.entity.GsonData;
import com.dk.mp.core.entity.JsonData;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.Logger;
import com.dk.mp.gzbx.Adapter.ContactPersonAdapter;
import com.dk.mp.gzbx.R;
import com.dk.mp.gzbx.entity.Person;
import com.dk.mp.gzbx.http.HttpListArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 联系人
 * @author dake
 *
 */
public class ContactPersonActivity extends MyActivity implements OnItemClickListener{
	private ListView listview;
	private List<Person> list;
	private ContactPersonAdapter mAdapter;
	private Map<String,Person> map = new HashMap<String,Person>();
	private Map<String, Boolean> isSelected = new HashMap<String, Boolean>();  
	private String id;
	private TextView right_txt;

	@Override
	protected int getLayoutID() {
		return R.layout.layout_contactperson;
	}

	@Override
	protected void initialize() {
		super.initialize();

		setTitle("处理人");

		findView();
		getData();
	}
	
	public void findView(){
		id = getIntent().getStringExtra("id");
		listview = (ListView) findViewById(R.id.listview);
		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listview.setOnItemClickListener(this);

		right_txt = (TextView) findViewById(R.id.right_txt);
		right_txt.setText("确定");
		right_txt.setVisibility(View.VISIBLE);

		right_txt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				right_txt.setEnabled(false);
				right_txt.setClickable(false);
				if(map.size()<=0){
					right_txt.setEnabled(true);
					right_txt.setClickable(true);
					showMessage("请选择处理人");
					return;
				}
//				showProgressDialog();
				Set<String> get = map.keySet();
				String mapP = "";
				for (String test:get) {
					mapP = test;
		        }
				final String mp = mapP;
//				showProgressDialog();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("idUser", mp);
				map.put("id", id);

				HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/qdlxr", map, new HttpListener<JSONObject>() {
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
						right_txt.setEnabled(true);
						right_txt.setClickable(true);
					}
				});
			}
		});
	}
	public void getData(){
		if(DeviceUtil.checkNet()){
//			showProgressDialog();

			HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/rylb", null, new HttpListener<JSONObject>() {
				@Override
				public void onSuccess(JSONObject result) {
					try {
						if (result.getInt("code") == 200){
//                            list = new Gson().fromJson(result.toString(), new TypeToken<GsonData<Person>>() {}.getType());
							list = HttpListArray.getPersons(result);
							for(Person p : list){
								isSelected.put(p.getId(), false);
							}
							if(list.size() == 0){
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Logger.info("position:"+position);
		Person p  = list.get(position);
//		MyView holder = (MyView)view.getTag();
		 // 当前点击的CB  
        boolean cu = !isSelected.get(p.getId()); 
     // 先将所有的置为FALSE  
        for(String m : isSelected.keySet()) {  
            isSelected.put(m, false);  
        } 
        // 再将当前选择CB的实际状态  
        isSelected.put(p.getId(), cu);  
		mAdapter.notifyDataSetChanged();  
		map.clear();
		if(cu)
			map.put(p.getId(), p);
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				mAdapter = new ContactPersonAdapter(mContext, list,isSelected);
				listview.setAdapter(mAdapter);
				break;
			case 2:
				showMessage("没有取到人员列表");
				break;
			case 3:
				right_txt.setEnabled(true);
				right_txt.setClickable(true);
				showMessage("指定处理人成功");
				finish();
				AwaitApprovalProcessActivity.instance.finish();
				BroadcastUtil.sendBroadcast(mContext, "com.test.action.refresh");
				BroadcastUtil.sendBroadcast(mContext, "com.test.action.refresh.tab");
//				AwaitingApprovalActivity.instance.finish();
				break;
			case 4:
				right_txt.setEnabled(true);
				right_txt.setClickable(true);
				showMessage("指定处理人失败");
				break;
			default:
				break;
			}
		};
	};
	
	 @Override
	    protected void onRestart() {
	    	super.onRestart();
	    	right_txt.setEnabled(true);//将触发控件设置为可用
	    	right_txt.setClickable(true);
	    }
	
}
