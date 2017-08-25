package com.dk.mp.gzbx.Adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dk.mp.gzbx.R;
import com.dk.mp.gzbx.entity.Malfunction;
import com.dk.mp.gzbx.util.StringUtil;

@SuppressLint("InflateParams")
public class MyTroublshootingAdapter extends BaseAdapter{
	private Context context;
	private List<Malfunction> list;
	private LayoutInflater lif;
	
	public List<Malfunction> getList() {
		return list;
	}
	public void setList(List<Malfunction> list) {
		this.list = list;
	}
	public MyTroublshootingAdapter(Context context,List<Malfunction> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MyView mv;
		if(convertView == null){
			mv = new MyView();
			lif = LayoutInflater.from(context);
			convertView = lif.inflate(R.layout.layout_my_troublshooting_item, null);
			mv.tro_name = (TextView) convertView.findViewById(R.id.tro_name);
			mv.tro_title = (TextView) convertView.findViewById(R.id.tro_title);
			mv.tro_status = (TextView) convertView.findViewById(R.id.tro_status);
			mv.tro_time = (TextView) convertView.findViewById(R.id.tro_time);
		}else{
			mv = (MyView) convertView.getTag();
		}
		Malfunction m = list.get(position);
		String name = m.getName();
		mv.tro_name.setText(StringUtil.dealString(name));
		mv.tro_title.setText(m.getTitle()+"的报修");
		mv.tro_status.setText(m.getStatusname());
		mv.tro_time.setText(m.getTime());
		convertView.setTag(mv);
		return convertView;
	}
	
	
	private static class MyView{
		TextView tro_name,tro_title,tro_status,tro_time;
	}

}
