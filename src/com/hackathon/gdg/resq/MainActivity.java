package com.hackathon.gdg.resq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	Cursor cursor;

	ArrayList<String> vCard ;
	String vfile;
	private ViewFlipper vf;
	private float lastX;
	@Override
	public void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		vf = (ViewFlipper)findViewById(R.id.view_flipper);
		vfile = "Contacts" + "_" + System.currentTimeMillis()+".vcf";
		getVcardString();
		

		//Email code here.........

        //id: resQ.app@gmail.com
        //pass: bitsgdgresq
		
		String owner="arpavan1990@gmail.com";	//Alok-Sharma: link this to the user's gmail ID
        
        //code below sends email to the email address mentioned in string "owner"
        try {   
        	GMailSender sender = new GMailSender("resq.app@gmail.com", "bitsgdgresq");
            sender.sendMail("This is Subject",   
                            "This is Body",   
                            "resq.app@gmail.com",   
                            owner);   
            Log.d("mailtest","success!");
            } catch (Exception e) {   
            	Log.e("SendMail", e.getMessage(), e);   
            } 

		//end of email code........

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
