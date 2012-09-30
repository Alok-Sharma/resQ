package com.hackathon.gdg.resq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(1000);
				} catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint = new Intent("com.thenewboston.Test.MENU");
					startActivity(openStartingPoint);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}