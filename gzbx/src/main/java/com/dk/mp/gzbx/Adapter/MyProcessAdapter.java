package com.dk.mp.gzbx.Adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.mp.core.util.Logger;
import com.dk.mp.core.util.StringUtils;
import com.dk.mp.gzbx.R;
import com.dk.mp.gzbx.entity.ProcessInfo;
import com.dk.mp.gzbx.util.StringUtil;

public class MyProcessAdapter extends BaseAdapter{
	private Context context;
	private List<ProcessInfo> list;
	private LayoutInflater lif;
	
	public MyProcessAdapter(Context context,List<ProcessInfo> list){
		this.context = context;
		this.list = list;
		lif = LayoutInflater.from(context);
	}

	public List<ProcessInfo> getList() {
		return list;
	}

	public void setList(List<ProcessInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Myview mv;
		if(convertView == null){
			mv = new Myview();
			convertView = lif.inflate(R.layout.layout_my_process_item, null);
			mv.topview = convertView.findViewById(R.id.topview);
			mv.bottomview = convertView.findViewById(R.id.bottomview);
			mv.imageview1 = (ImageView) convertView.findViewById(R.id.tro_imageview1);
			mv.tro_name = (TextView) convertView.findViewById(R.id.tro_name);
			mv.tro_title = (TextView) convertView.findViewById(R.id.tro_title);
			mv.tro_status = (TextView) convertView.findViewById(R.id.tro_status);
			mv.tro_time = (TextView) convertView.findViewById(R.id.tro_time);
			mv.tro_repairstatus = (TextView) convertView.findViewById(R.id.tro_repairstatus);
			convertView.setTag(mv);
		}else{
			mv = (Myview) convertView.getTag();
		}
		
		final ProcessInfo pinfo = list.get(position);
		if(position == 0)
			mv.topview.setVisibility(View.INVISIBLE);
		else
			mv.topview.setVisibility(View.VISIBLE);
		if(position == list.size()-1)
			mv.bottomview.setVisibility(View.INVISIBLE);
		else 
			mv.bottomview.setVisibility(View.VISIBLE);
		if("1".equals(pinfo.getStatus())){
			mv.imageview1.setImageResource(R.drawable.processed);
			mv.tro_status.setTextColor(context.getResources().getColor(R.color.tro_suc_fontcolor));
		}else{
			mv.imageview1.setImageResource(R.drawable.untreated);
			mv.tro_status.setTextColor(context.getResources().getColor(R.color.tro_status));
		}
		mv.tro_name.setText(StringUtil.dealString(pinfo.getName()));
		mv.tro_title.setText(pinfo.getName());
		mv.tro_status.setText(pinfo.getStatusname());
		mv.tro_time.setText(pinfo.getTime());
		if(StringUtils.isNotEmpty(pinfo.getRepairstatus())){
			mv.tro_repairstatus.setVisibility(View.VISIBLE);
			mv.tro_repairstatus.setText(pinfo.getRepairstatus());
		}else{
			mv.tro_repairstatus.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private class Myview{
		private View topview,bottomview;
		private ImageView imageview1;
		private TextView tro_name,tro_title,tro_status,tro_time,tro_repairstatus;
	}
}
