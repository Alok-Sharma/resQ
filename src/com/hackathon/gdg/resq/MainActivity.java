package com.hackathon.gdg.resq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	
	private ViewFlipper vf;
	private float lastX;
	Boolean isLost;
	
	EditText smskeytext;
	TextView smstextview;
	EditText emailtext;
	TextView emailtextview;
	EditText afterkeytext;
	TextView aftertext;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		vf = (ViewFlipper)findViewById(R.id.view_flipper);
		
		Button submitbutton = (Button)findViewById(R.id.submit);
		smskeytext = (EditText)findViewById(R.id.smskey);
		smstextview = (TextView)findViewById(R.id.smstext);
		emailtext = (EditText)findViewById(R.id.emailcontacts);
		emailtextview = (TextView)findViewById(R.id.emailtext);
		afterkeytext = (EditText)findViewById(R.id.afterkey);
		aftertext = (TextView)findViewById(R.id.aftertext);

		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		final String smsfrompref = prefs.getString("sms",null);
		String emailfrompref = prefs.getString("email",null);
		isLost = prefs.getBoolean("isLost",false);

		if(smsfrompref!=null && emailfrompref!=null){
			
			smskeytext.setText(smsfrompref);
			emailtext.setText(emailfrompref);
		}

		submitbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(!isLost){
					SharedPreferences.Editor editor = prefs.edit();
					//String k = edittext.getText().toString().replace(' ', '');
					//Testing.
					
					if(smskeytext.getText().toString().contains(" "))
					{
						Toast.makeText(getApplicationContext(),"No spaces allowed in key", Toast.LENGTH_LONG).show();
					}
					else
					{
					editor.putString("sms", smskeytext.getText().toString().replace(" ", ""));
					editor.putString("email", emailtext.getText().toString());
					editor.commit();
					changeVis();
					}
				}else{
					String checkstring = afterkeytext.getText().toString();
					Log.d("alok",smsfrompref);
				
					SharedPreferences.Editor editor = prefs.edit();
					if(checkstring.equals(smsfrompref)){
						isLost = false;
						editor.putBoolean("isLost",false);
						changeVis();
						//Toast.makeText(getApplicationContext(),"changeViscalledset visible key:"+checkstring+"*"+smsfrompref+"*", Toast.LENGTH_LONG).show();
					}else{
						isLost = true;
						
						changeVis();
						//Toast.makeText(getApplicationContext(),"changeViscalledset invisible key:"+checkstring+"*"+smsfrompref+"*", Toast.LENGTH_LONG).show();
					}
				}
			}
		}); 
		
		
		
	
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//Toast.makeText(getApplicationContext(), "onResume",Toast.LENGTH_LONG).show();
		changeVis();
	}
	
	private void changeVis(){
		if(isLost){
			smskeytext.setVisibility(View.INVISIBLE);
			smstextview.setVisibility(View.INVISIBLE);
			emailtext.setVisibility(View.INVISIBLE);
			emailtextview.setVisibility(View.INVISIBLE);
			afterkeytext.setVisibility(View.VISIBLE);
			aftertext.setVisibility(View.VISIBLE);
		}else{
			smskeytext.setVisibility(View.VISIBLE);
			smstextview.setVisibility(View.VISIBLE);
			emailtext.setVisibility(View.VISIBLE);
			emailtextview.setVisibility(View.VISIBLE);
			afterkeytext.setVisibility(View.INVISIBLE);
			aftertext.setVisibility(View.INVISIBLE);
		}
	}
	


	
	
	



	@Override
	public boolean onTouchEvent(MotionEvent touchevent) {
		switch (touchevent.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			lastX = touchevent.getX();
			break;
		}
		case MotionEvent.ACTION_UP:
		{
			float currentX = touchevent.getX();
			if (lastX < currentX)
			{
				if (vf.getDisplayedChild()==0) break;
				vf.setInAnimation(this, R.anim.in_from_left);
				vf.setOutAnimation(this, R.anim.out_to_right);
				vf.showNext();
			}
			if (lastX > currentX)
			{
				if (vf.getDisplayedChild()==1) break;
				vf.setInAnimation(this, R.anim.in_from_right);
				vf.setOutAnimation(this, R.anim.out_to_left);
				vf.showPrevious();
			}
			break;
		}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
