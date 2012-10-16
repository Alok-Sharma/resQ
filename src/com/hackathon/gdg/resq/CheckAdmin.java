package com.hackathon.gdg.resq;

import android.app.*;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class CheckAdmin extends DeviceAdminReceiver{
	
	static boolean enabled=false;
	
	
	static SharedPreferences getSamplePreferences(Context context) {
        return context.getSharedPreferences(DeviceAdminReceiver.class.getName(), 0);
    }

    static String PREF_PASSWORD_QUALITY = "password_quality";
    static String PREF_PASSWORD_LENGTH = "password_length";
    static String PREF_MAX_FAILED_PW = "max_failed_pw";

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "Device Admin: enabled");
        enabled=true;
        
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "This is an optional message to warn the user about disabling.";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: disabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw changed");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw failed");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw succeeded");
    }
	
public static class Faceless extends Activity {
	
	DevicePolicyManager mDPM;
	ComponentName mDeviceAdminSample;
	static final int RESULT_ENABLE = 1;
	String password;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Intent fromWhere=getIntent();
		if(fromWhere.getExtras()!=null)
		{
		boolean what= fromWhere.getExtras().getBoolean("tobeChecked");
		password=(String)fromWhere.getExtras().getCharSequence("password");
		mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeviceAdminSample = new ComponentName(Faceless.this, CheckAdmin.class);
	    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
        /*intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            "Additional text explaining why this needs to be added.");
       */
      // Toast.makeText(getApplicationContext(), "activity is to be started now",Toast.LENGTH_LONG).show();
        startActivityForResult(intent, RESULT_ENABLE);
        //enabled=true;
        
        if(what==true)
        {	boolean active = mDPM.isAdminActive(mDeviceAdminSample);
        	if (active) {
        					mDPM.resetPassword(password,DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        				}
        		
        	
        	
            if (active) {
                mDPM.lockNow();
            }
        }
        
        finish();
        
        
		}
		
		else
		{
			mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
			mDeviceAdminSample = new ComponentName(Faceless.this, CheckAdmin.class);
		    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
	        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
	        /*intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
	            "Additional text explaining why this needs to be added.");
	       */
	      // Toast.makeText(getApplicationContext(), "activity is to be started now",Toast.LENGTH_LONG).show();
	        startActivityForResult(intent, RESULT_ENABLE);
		}
        
       
	}

	public void onResume()
	{   
		super.onResume();
		if(mDPM.isAdminActive(mDeviceAdminSample))
		{
		
		Intent proceed=new Intent(getApplicationContext(),MainActivity.class);
		startActivity(proceed);
		}
		else
		{
			//Toast.makeText(getApplicationContext(), "permission not given",Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	}
}
