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

	
	private ViewFlipper vf;
	private float lastX;
	@Override
	public void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		

		

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
