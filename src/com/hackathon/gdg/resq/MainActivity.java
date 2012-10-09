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
	
		testlist.add("Log Entries");testlist.add("Will fill up here");
		testlist.add("Similar to how it is now.");
		
		initViewPager(2, 0xFFFFFFFF, 0xFF000000,getModel());
		mFixedTabs = (FixedTabsView) findViewById(R.id.fixed_tabs);
		mFixedTabsAdapter = new FixedTabsAdapter(this);
		mFixedTabs.setAdapter(mFixedTabsAdapter);
		mFixedTabs.setViewPager(mPager);
	}
	
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
