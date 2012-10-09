package com.hackathon.gdg.ui;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<model>{
	
	List<model> testlist;
	private final Activity context;
	int layoutId, textviewId;

	public ListAdapter(Activity context, List<model> testlist, int textViewResourceId, int layoutId) {
		super(context, textViewResourceId);
		this.testlist = testlist;	
		this.context = context;
		this.layoutId = layoutId;
		this.textviewId = textViewResourceId;
	}
	
	static class ViewHolder{
		protected TextView text;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		
		View view = null;
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(layoutId, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView)view.findViewById(textviewId);
			view.setTag(viewHolder);
		}else{
			view = convertView;
		}
		ViewHolder holder = (ViewHolder)view.getTag();
		holder.text.setText(testlist.get(position).getmText());
		return view;
	}
	
	@Override
	public int getCount(){
		return testlist.size();
	}

}
