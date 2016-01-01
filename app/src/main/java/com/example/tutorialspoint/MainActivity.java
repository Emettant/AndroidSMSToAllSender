package com.example.tutorialspoint;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    ArrayList<String> alContacts = new ArrayList<String>();
    Boolean llocker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.editText);
        txtMessage = (EditText) findViewById(R.id.editText2);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                afterClick();
            }
        });
    }

    protected void sendSMS(String phoneNo) {
        try {

            String message5 ="3  НовNм Р0ком t@ Р!здв0м Христ0вNм /\\ Пивовар,Vancouver,BC,Ca";
            SmsManager smsManager = SmsManager.getDefault();
           //if (phoneNo.contains("6048689469") || phoneNo.contains("7783893561") || phoneNo.contains("7788743397"))  {
           //     phoneNo = "+17789294096";
                //smsManager.sendTextMessage(phoneNo, null, message5, null, null);
                Log.i("MainActivity", "----------------waiting 5 sec--------------------------------");
                Thread.sleep(5000);

            //}
            Log.i("MainActivity", "------------------------------------------------");
            Log.i("MainActivity", phoneNo + " : SMS sent.");
            Log.i("MainActivity", "------------------------------------------------");


        }
        catch (Exception e) {
            Log.e("MainActivity", "SMS faild, please try again.");

            e.printStackTrace();
        }
    }
    protected void afterClick() {
        /*Log.i("Send SMS", "");
        String phoneNo = txtphoneNo.getText().toString();
        String message = txtMessage.getText().toString();
*/
        if (llocker == true || !alContacts.isEmpty())
        {
            Log.i("MainActivity","contacts is full and sent. Stop.");
            return;
        }
        llocker = true;
        Log.i("MainActivity","Gathering has been strarted...");

        try {
  /*          SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
    */
            ContentResolver cr = getContentResolver(); //Activity/Application android.content.Context
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if(cursor.moveToFirst()) {

                do
                {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                        while (pCur.moveToNext())
                        {
                            String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                            if (contactNumber.length() >= 10)
                            {

                                if (contactNumber.startsWith("+380") || contactNumber.startsWith("80") || contactNumber.startsWith("0"))
                                {
                                    String uaNum = new String();
                                    if (contactNumber.startsWith("80")) uaNum = new String("+3" + contactNumber);
                                    else if (contactNumber.startsWith("0")) uaNum = new String("+38" + contactNumber);
                                    else if (contactNumber.startsWith("+380")) uaNum = new String(contactNumber);

                                    alContacts.add(uaNum);

                                }
                                else
                                {
                                    if (contactNumber.contains("6048689469"))
                                        alContacts.add(contactNumber);//Denys
                                    else if (contactNumber.contains("7783893561"))
                                        alContacts.add(contactNumber);//Diana
                                    else if (contactNumber.contains("7788743397"))
                                        alContacts.add(contactNumber);//Alina
                                    else if (contactNumber.contains("7782374836"))//Olga
                                        alContacts.add(contactNumber);
                                    else if (contactNumber.contains("7786861483"))//Olga
                                        alContacts.add(contactNumber);

                                }
                            }
                            break;
                        }
                        pCur.close();
                    }

                } while (cursor.moveToNext()) ;
            }
        }


        catch (Exception e) {
            Log.i("MainActivity", "SMS faild, please try again.");
            e.printStackTrace();
        }
        Log.i("MainActivity","Gathering end: " + alContacts.size());
        for(String num : alContacts)
        {
            sendSMS(num);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}