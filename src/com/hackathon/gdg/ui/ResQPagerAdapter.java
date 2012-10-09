package com.hackathon.gdg.ui;

import java.util.List;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hackathon.gdg.resq.R;
import com.hackathon.gdg.resq.MainActivity;

public class ResQPagerAdapter extends PagerAdapter {
	
	protected transient Activity mContext;
	
	private int mLength = 0;
	private int mBackgroundColor = 0xFFFFFFFF;
	private int mTextColor = 0xFF000000;
	List<model> testlist;
	int layoutId, textviewid;
	
	public ResQPagerAdapter(Activity context, int length, int backgroundColor, int textColor, List<model> testlist) {
		mContext = context;
		mLength = length;
		mBackgroundColor = backgroundColor;
		mTextColor = textColor;
		this.testlist = testlist;
	}
	
	@Override
	public int getCount() {
		return mLength;
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		View v = null;
		LayoutInflater inflater = mContext.getLayoutInflater();
		
		if(position == 1){
			v = inflater.inflate(R.layout.layout_1, null);
			ListView mlist = (ListView)v.findViewById(R.id.list);
			layoutId = R.layout.rowitem;
			textviewid = R.id.tvDescription;
			ArrayAdapter<model> mAdapter = new ListAdapter(mContext, testlist, textviewid, layoutId);
			mlist.setAdapter(mAdapter);
			((ViewPager) container).addView(v,0);
		}else if(position == 0){
			v = inflater.inflate(R.layout.layout_0, null);
			((ViewPager) container).addView(v, 0);
			
		}
		return v;
	}
	
	
	@Override
	public void destroyItem(View container, int position, Object view) {
		((ViewPager) container).removeView((View) view);
	}
	
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}
	
	
	@Override
	public void finishUpdate(View container) {}
	
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {}
	
	@Override
	public Parcelable saveState() {
		return null;
	}
	
	@Override
	public void startUpdate(View container) {}
	
}
