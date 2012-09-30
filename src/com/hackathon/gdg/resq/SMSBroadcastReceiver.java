
package com.hackathon.gdg.resq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;



public class SMSBroadcastReceiver extends BroadcastReceiver {
	
	
	String known_key;
    @Override
    public void onReceive(Context context, Intent intent) {
        //intent contains SMS data
	// Get the SMS map from Intent
        Bundle extras = intent.getExtras();
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String known_key = prefs.getString("sms", null);
        
        boolean isLost=false;
        
        
        
        String messages = "";
        
        
        if ( extras != null )
        {
            // Get received SMS array
            Object[] smsExtra = (Object[]) extras.get( "pdus" );
            SmsMessage[] sms= new SmsMessage[smsExtra.length];
            //Get ContentResolver object for pushing encrypted SMS to the incoming folder
            //ContentResolver contentResolver = context.getContentResolver();
             
            for ( int i = 0; i < smsExtra.length; ++i )
            {
                sms[i] = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                 
                String body = sms[i].getMessageBody().toString();
                String address = sms[i].getOriginatingAddress();
                 
                messages += address + ":";                    
                messages += body + "\n";
            }
            //Log.d("testing:", messages);
            String msg_split[] = messages.split(":");
            Log.d("meta", msg_split[0]);
            Log.d("body", msg_split[1]);
            
            String body_split[] = msg_split[1].split(" ");
            Log.d("command", body_split[0]);
            Log.d("key", body_split[1]);
            
            
			
            if(body_split[0] == "resQ" && body_split[1].equals(known_key))
            {
            	isLost = true;
            	SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLost", isLost);
            		//MainActivity.getVCardString();
            		
            		// rishabh's method
            		// alok's method
            }
            //else	
            //{
                	// 	this is not a resQ message. Don't do anything.
			
            //}
		
 
                // Here you can add any your code to work with incoming SMS
                // I added encrypting of all received SMS 
                 
                //putSmsToDatabase( contentResolver, sms );
            
             
            // Display SMS message
            Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
        }
    }
}
