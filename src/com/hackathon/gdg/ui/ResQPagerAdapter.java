package com.hackathon.gdg.ui;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.gdg.resq.R;
import com.hackathon.gdg.resq.MainActivity;

public class ResQPagerAdapter extends PagerAdapter {
	
	protected transient Activity mContext;
	private int mLength = 0;
	private int mBackgroundColor = 0xFFFFFFFF;
	private int mTextColor = 0xFF000000;
	List<model> testlist;
	int layoutId, textviewid;
	
	// Global Variables for Layout 0
	Boolean isLost;
	EditText smskeytext;
	TextView smstextview;
	EditText emailtext;
	TextView emailtextview;
	EditText afterkeytext;
	TextView aftertext;
	Button submitbutton;
	//
	
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
		
		if(position == 0){
			// All UI elements related code for layout_0 comes here.
			v = inflater.inflate(R.layout.layout_0, null);
			setUpLayout0(v);
			((ViewPager) container).addView(v, 0);
		}else if(position == 1){
			// All UI elements related code for layout_1 comes here.
			v = inflater.inflate(R.layout.layout_1, null);
			ListView mlist = (ListView)v.findViewById(R.id.list);
			layoutId = R.layout.rowitem;
			textviewid = R.id.tvDescription;
			ArrayAdapter<model> mAdapter = new ListAdapter(mContext, testlist, textviewid, layoutId);
			mlist.setAdapter(mAdapter);
			((ViewPager) container).addView(v,0);
		}
		return v;
	}
	
	/*
	 * The method that handles all view interaction of layout 0.
	 * If more views are added and whose views are required to be
	 * referenced from within the code, then make similar methods for
	 * those as well.
	 */
	public void setUpLayout0(View v){
		
		submitbutton = (Button)v.findViewById(R.id.submit);
		smskeytext = (EditText)v.findViewById(R.id.smskey);
		smstextview = (TextView)v.findViewById(R.id.smstext);
		emailtext = (EditText)v.findViewById(R.id.emailcontacts);
		emailtextview = (TextView)v.findViewById(R.id.emailtext);
		afterkeytext = (EditText)v.findViewById(R.id.afterkey);
		aftertext = (TextView)v.findViewById(R.id.aftertext);
		
		
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		final String smsfrompref = prefs.getString("sms",null);
		String emailfrompref = prefs.getString("email",null);
		isLost = prefs.getBoolean("isLost",false);
		
		
		if(smsfrompref!=null && emailfrompref!=null){
			smskeytext.setText(smsfrompref);
			emailtext.setText(emailfrompref);
		}
		Log.d("changevis", "Setup layout 0 called, isLost: "+isLost);
		changeVis();
		
		submitbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!isLost){
					SharedPreferences.Editor editor = prefs.edit();
					if(smskeytext.getText().toString().contains(" "))
					{
						Toast.makeText(mContext,"No spaces allowed in key", Toast.LENGTH_SHORT).show();
					}
					else
					{
						editor.putString("sms", smskeytext.getText().toString().replace(" ", ""));
						editor.putString("email", emailtext.getText().toString());
						editor.commit();
						Toast.makeText(mContext,"Saved", Toast.LENGTH_SHORT).show();
						Log.d("changevis", "1");
						changeVis();
					}
				}else{
					String checkstring = afterkeytext.getText().toString();
					SharedPreferences.Editor editor = prefs.edit();
					if(checkstring.equals(smsfrompref) || smsfrompref==null){
						isLost = false;
						editor.putBoolean("isLost",false);
						Log.d("changevis", "2");
						changeVis();
					}else{
						//Nothing changes.
						Toast.makeText(mContext,"Wrong key", Toast.LENGTH_SHORT).show();
					}
					editor.commit();
				}
			}
		}); 
	}
	
	/*
	 * A convenient method for changing the visibility
	 * of the appropriate button and textviews based upon
	 * locked status of phone.
	 */
	public void changeVis(){
		Log.d("visibiility","isLost: "+isLost);
		if(isLost){
			smskeytext.setVisibility(View.GONE);
			smstextview.setVisibility(View.GONE);
			emailtext.setVisibility(View.GONE);
			emailtextview.setVisibility(View.GONE);
			afterkeytext.setVisibility(View.VISIBLE);
			aftertext.setVisibility(View.VISIBLE);
		}else{
			smskeytext.setVisibility(View.VISIBLE);
			smstextview.setVisibility(View.VISIBLE);
			emailtext.setVisibility(View.VISIBLE);
			emailtextview.setVisibility(View.VISIBLE);
			afterkeytext.setVisibility(View.GONE);
			aftertext.setVisibility(View.GONE);
		}
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
