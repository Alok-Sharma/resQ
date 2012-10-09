package com.hackathon.gdg.resq;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.viewpager.extensions.FixedTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.hackathon.gdg.ui.ResQPagerAdapter;
import com.hackathon.gdg.ui.FixedTabsAdapter;
import com.hackathon.gdg.ui.model;

public class MainActivity extends Activity {

	Boolean isLost;
	EditText smskeytext;
	TextView smstextview;
	EditText emailtext;
	TextView emailtextview;
	EditText afterkeytext;
	TextView aftertext;
	Button submitbutton;
	View v = null;
	
	//Global Variables for the UI
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private FixedTabsView mFixedTabs;
	private TabsAdapter mFixedTabsAdapter;
	ArrayList<String> testlist = new ArrayList<String>();
	//
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fixed_tabs);
		
		
//		LayoutInflater inflater = this.getLayoutInflater();
//		v = inflater.inflate(R.layout.layout_0, null);
//		submitbutton = (Button)v.findViewById(R.id.submit);
//		smskeytext = (EditText)v.findViewById(R.id.smskey);
//		smstextview = (TextView)v.findViewById(R.id.smstext);
//		emailtext = (EditText)v.findViewById(R.id.emailcontacts);
//		emailtextview = (TextView)v.findViewById(R.id.emailtext);
//		afterkeytext = (EditText)v.findViewById(R.id.afterkey);
//		aftertext = (TextView)v.findViewById(R.id.aftertext);
//
//		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//		final String smsfrompref = prefs.getString("sms",null);
//		String emailfrompref = prefs.getString("email",null);
//		isLost = prefs.getBoolean("isLost",false);
//		Log.d("alok","in oncreate "+isLost);
//		
//		if(smsfrompref!=null && emailfrompref!=null){
//
//			smskeytext.setText(smsfrompref);
//			emailtext.setText(emailfrompref);
//		}
		
		testlist.add("Log Entries");testlist.add("Will fill up here");
		testlist.add("Similar to how it is now.");
		
		initViewPager(2, 0xFFFFFFFF, 0xFF000000,getModel());
		mFixedTabs = (FixedTabsView) findViewById(R.id.fixed_tabs);
		mFixedTabsAdapter = new FixedTabsAdapter(this);
		mFixedTabs.setAdapter(mFixedTabsAdapter);
		mFixedTabs.setViewPager(mPager);
		
//		submitbutton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				if(!isLost){
//					SharedPreferences.Editor editor = prefs.edit();
//					if(smskeytext.getText().toString().contains(" "))
//					{
//						Toast.makeText(getApplicationContext(),"No spaces allowed in key", Toast.LENGTH_SHORT).show();
//					}
//					else
//					{
//						editor.putString("sms", smskeytext.getText().toString().replace(" ", ""));
//						editor.putString("email", emailtext.getText().toString());
//						editor.commit();
//						Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_SHORT).show();
//						changeVis();
//					}
//				}else{
//					String checkstring = afterkeytext.getText().toString();
//					SharedPreferences.Editor editor = prefs.edit();
//					if(checkstring.equals(smsfrompref) || smsfrompref==null){
//						Log.d("alok","in if");
//						isLost = false;
//						editor.putBoolean("isLost",false);
//						changeVis();
//					}else{
//						Log.d("alok","in else");
//						isLost = true;
//						changeVis();
//					}
//					editor.commit();
//				}
//			}
//		}); 
	}
	

	@Override
	public void onResume(){
		super.onResume();
//		changeVis();
	}

	/*
	 * A convenenient method for changing the visibility
	 * of the appropriate button and textviews based upon
	 * locked status of phone.
	 */
//	public void changeVis(){
//		Log.d("alok","in changevis "+isLost);
//		if(isLost){
//			smskeytext.setText("hurr");
//			smskeytext.setVisibility(View.INVISIBLE);
//			smstextview.setVisibility(View.INVISIBLE);
//			emailtext.setVisibility(View.INVISIBLE);
//			emailtextview.setVisibility(View.INVISIBLE);
//			afterkeytext.setVisibility(View.VISIBLE);
//			aftertext.setVisibility(View.VISIBLE);
//		}else{
//			smskeytext.setText("hurr");
//			smskeytext.setVisibility(View.VISIBLE);
//			smstextview.setVisibility(View.VISIBLE);
//			emailtext.setVisibility(View.VISIBLE);
//			emailtextview.setVisibility(View.VISIBLE);
//			afterkeytext.setVisibility(View.INVISIBLE);
//			aftertext.setVisibility(View.INVISIBLE);
//		}
//	}
	
	/*
	 * Initializes the ViewPager (The content below the tabs).
	 */
	private void initViewPager(int pageCount, int backgroundColor, int textColor, List<model> list) {
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ResQPagerAdapter(this, pageCount, backgroundColor, textColor, list);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		mPager.setPageMargin(1);
	}
	
	/*
	 * 
	 */
	private List<model> getModel(){
    	List<model> list1 = new ArrayList<model>();
    	for(int i=0;i<testlist.size();i++){
    		list1.add(get(testlist.get(i)));
    	}
    	return list1;
    }
    
	/*
	 * 
	 */
    private model get(String s){
    	return new model(s);
    }
}
