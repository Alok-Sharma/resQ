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

public class MainActivity extends Activity {
	Cursor cursor;
    ArrayList<String> vCard ;
    String vfile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* Pavan wrote this line! */
        
        Log.d("testing","testing");
        Log.d("rishabh","testing");
        /*All you have to do is call getVcardString() - Rishabh*/
        
    }
    private void getVcardString() {
        // TODO Auto-generated method stub
    	
    	vfile = "Contacts" + "_" + System.currentTimeMillis()+".vcf";
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
               // Log.d("TAG", "Contact "+(i+1)+"VcF String is"+vCard.get(i));
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
