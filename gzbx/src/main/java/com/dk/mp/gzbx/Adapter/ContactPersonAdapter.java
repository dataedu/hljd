package com.dk.mp.gzbx.Adapter;

import java.util.List;
import java.util.Map;

import com.dk.mp.core.util.Logger;
import com.dk.mp.gzbx.R;
import com.dk.mp.gzbx.entity.Person;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * 联系人适配器
 * @author dake
 *
 */
@SuppressLint("InflateParams")
public class ContactPersonAdapter extends BaseAdapter{
	private List<Person> list;
	private Context context;
	private LayoutInflater lif;
	private Map<String,Boolean> map;
	
	public List<Person> getList() {
		return list;
	}

	public void setList(List<Person> list) {
		this.list = list;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ContactPersonAdapter(Context context,List<Person> list,Map<String,Boolean> map){
		this.list = list;
		this.context = context;
		this.map = map;
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
		MyView mv;
		if(convertView == null){
			mv = new MyView();
			lif = LayoutInflater.from(context);
			convertView = lif.inflate(R.layout.layout_contactperson_item, null);
		}else{
			mv = (MyView) convertView.getTag();
		}
		mv.checkbox = (CheckBox) convertView.findViewById(R.id.tro_check);
		mv.person_name = (TextView) convertView.findViewById(R.id.person_name);
		Person p = list.get(position);
		mv.person_name.setText(p.getName());
		mv.checkbox.setChecked(map.get(p.getId()));
		convertView.setTag(mv);
		return convertView;
	}
	public class MyView{
		public CheckBox checkbox;
		public TextView person_name;
	}
}
