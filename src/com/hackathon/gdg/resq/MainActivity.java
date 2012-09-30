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
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	Cursor cursor;

	ArrayList<String> vCard ;
	String vfile;
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
		vfile = "Contacts" + "_" + System.currentTimeMillis()+".vcf";
		getVcardString();

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
					editor.putString("sms", smskeytext.getText().toString());
					editor.putString("email", emailtext.getText().toString());
					editor.commit();
					changeVis(isLost);
				}else{
					String checkstring = afterkeytext.getText().toString();
					if(checkstring.equals(smsfrompref)){
						isLost = false;
						changeVis(isLost);
					}else{
						isLost = true;
						changeVis(isLost);
					}
				}
			}
		}); 
		
		changeVis(isLost);
	
	}
	
	private void changeVis(Boolean isLost){
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
	
	private void getVcardString() {
		// TODO Auto-generated method stub


		vCard = new ArrayList<String>();
		cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		Log.d("Rishabh",Integer.toString(cursor.getCount()));
		if(cursor!=null&&cursor.getCount()>0)
		{
			cursor.moveToFirst();
			for(int i =0;i<cursor.getCount();i++)
			{

				get(cursor);
				Log.d("Iterator", Integer.toString(i+1));
				Log.d("TAG", "Contact "+(i+1)+"VcF String is"+vCard.get(i));
				cursor.moveToNext();
			}

		}
		else
		{
			Log.d("TAG", "No Contacts in Your Phone");
		}

	}

	public void get(Cursor cursor)
	{


		//cursor.moveToFirst();
		String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
		Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
		AssetFileDescriptor fd;
		try {
			fd = this.getContentResolver().openAssetFileDescriptor(uri, "r");

			// Your Complex Code and you used function without loop so how can you get all Contacts Vcard.??




			FileInputStream fis = fd.createInputStream();
			byte[] buf = new byte[(int) fd.getDeclaredLength()];
			fis.read(buf);
			String vcardstring= new String(buf);
			vCard.add(vcardstring);

			String storage_path = Environment.getExternalStorageDirectory().toString() + File.separator + vfile;
			FileOutputStream mFileOutputStream = new FileOutputStream(storage_path, false);
			mFileOutputStream.write(vcardstring.toString().getBytes());
			fd.close();

		} catch (Exception e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.getContentResolver().delete(uri, null, null);

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
