
package com.hackathon.gdg.resq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;



public class SMSBroadcastReceiver extends BroadcastReceiver {
	ArrayList<String> vCard ;
	String vfile;
	Cursor cursor;



	String known_key;
	@Override
	public void onReceive(Context context, Intent intent) {
		//intent contains SMS data
		// Get the SMS map from Intent
		vfile = "Contacts.vcf";
		Bundle extras = intent.getExtras();
		boolean isLost = false;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String known_key = prefs.getString("sms", null);
		String known_email = prefs.getString("email",null);

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

			Log.d("alok",body_split[0]);
			Log.d("alok",body_split[1]);
			Log.d("alok",known_key);
			Log.d("alok",body_split[0].equals("resQ")+"");
			Log.d("alok",body_split[1].equals(known_key)+"");
			
			Toast.makeText(context,body_split[0]+body_split[1]+":"+known_key, Toast.LENGTH_LONG).show();
			
			if(body_split[0].equals("resQ") )
			{
				{
					isLost = true;
					Log.d("alok",isLost+"");
					SharedPreferences.Editor editor = prefs.edit();
					editor.putBoolean("isLost", isLost);
					editor.commit();
					Log.d("rishabh", "inside if getVcardgetting called");
					getVcardString(context);
					//Email code here.........

					//id: resQ.app@gmail.com
					//pass: bitsgdgresq

					String owner=known_email;	//Alok-Sharma: link this to the user's gmail ID

					//code below sends email to the email address mentioned in string "owner"
					try {   
						GMailSender sender = new GMailSender("resq.app@gmail.com", "bitsgdgresq");
						sender.sendMail("Contacts From your Phone",   
								"resQ forwards the contacts from your lost phone",   
								"resq.app@gmail.com",   
								owner);   
						Log.d("mailtest","success!");
					} catch (Exception e) {
						Toast.makeText(context, "Errormail "+e.toString(), Toast.LENGTH_LONG).show();
						//Log.e("SendMail", e.getMessage(), e);   
					} 

					//end of email code..................
					deleteAccount(context);
				}

			}
			//else	
			//{
			// 	this is not a resQ message. Don't do anything.

			//}


			// Here you can add any your code to work with incoming SMS
			// I added encrypting of all received SMS 

			//putSmsToDatabase( contentResolver, sms );


			// Display SMS message
			//Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
		}
	}
	private void getVcardString(Context context) {
		// TODO Auto-generated method stub


		vCard = new ArrayList<String>();
		cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		Log.d("Rishabh",Integer.toString(cursor.getCount()));
		if(cursor!=null&&cursor.getCount()>0)
		{
			cursor.moveToFirst();
			for(int i =0;i<cursor.getCount();i++)
			{

				get(cursor,context);
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

	public void get(Cursor cursor,Context context)
	{


		//cursor.moveToFirst();
		String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
		Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
		AssetFileDescriptor fd;
		try {
			fd = context.getContentResolver().openAssetFileDescriptor(uri, "r");





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


	}
	public void deleteAccount(Context context){
		try {


			AccountManager am = AccountManager.get(context);
			Account[] accounts = am.getAccountsByType(null);
			Log.d("accountinfo","reached here");

			String []names;//names="";
			names=new String[accounts.length];
			for(int i=0;i<accounts.length;i++)
			{
//TODO remove comments here before the final release
			//	Account accountToRemove = accounts[i];
				Log.d("accountinfo","reached here inside if");

				
			//	am.removeAccount(accountToRemove, null, null);
			//	am.clearPassword(accounts[i]);

			}

			

		} catch (Exception e) {
		} 


	}
}
