package com.hackathon.gdg.resq;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;



public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //intent contains SMS data
	// Get the SMS map from Intent
        Bundle extras = intent.getExtras();
         
        String messages = "";
         
        if ( extras != null )
        {
            // Get received SMS array
            Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );
             
            // Get ContentResolver object for pushing encrypted SMS to the incoming folder
            //ContentResolver contentResolver = context.getContentResolver();
             
            for ( int i = 0; i < smsExtra.length; ++i )
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                 
                String body = sms.getMessageBody().toString();
                String address = sms.getOriginatingAddress();
                 
                messages += "SMS from " + address + " :\n";                    
                messages += body + "\n";
                
		String body_split[];
		body_split = body.split();
			
		if(body_split[0] == "resQ")
		{
			if(body_			
		}
		else
		{
			// this is not a resQ message. Don't do anything.
			
		}
		
 
                // Here you can add any your code to work with incoming SMS
                // I added encrypting of all received SMS 
                 
                //putSmsToDatabase( contentResolver, sms );
            }
             
            // Display SMS message
            Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
        }
    }
}

