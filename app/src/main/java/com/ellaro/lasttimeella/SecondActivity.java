package com.ellaro.lasttimeella;

import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.IntentFilter;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    Button back, refresh, next;
    private ListView lvSMS;
    private final static int REQUEST_CODE_PERMISSION_READ_SMS = 456;
    ArrayList<String> smsMsgList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    public static SecondActivity instance;
    private SMSReceiver smsReceiver;
    private IntentFilter IntentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("mylog","SecondActivity");

        //setting list view for messages
        lvSMS = (ListView) findViewById(R.id.lv_sms);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, smsMsgList);
        lvSMS.setAdapter(arrayAdapter);
        lvSMS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("clicked", "i:" + i);


                Intent cell = new Intent (SecondActivity.this,FourthActivity.class);
                cell.putExtra("Index",i);
                startActivity(cell);
            }
        });

        if (checkPermission("Manifest.permission.READ_SMS")) {
            refreshInbox();
        } else {
            ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_PERMISSION_READ_SMS);
        }
//Buttons:
        refresh = (Button) findViewById(R.id.RefreshInbox);
        back = (Button) findViewById(R.id.back3);
        next = (Button)findViewById(R.id.next);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshInbox();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back();
            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_next();
            }
        });

        if (checkIfMyFGServiceRunning() == false){
            startForegroundService(new Intent(this, MyService.class));
        }
    }


    public boolean checkPermission(String readSms) {
        int check = ContextCompat.checkSelfPermission(this, readSms);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private void go_back() {
        Intent newer = new Intent(this, MainActivity.class);
        startActivity(newer);
    }
    private void go_next() {
        Intent newer = new Intent(this, ThirdActivity.class);
        startActivity(newer);
    }

    public void refreshInbox() {
        //Content Resolver resolves a URI to a specific Content Provider
        ContentResolver cResolver = getContentResolver();
        Cursor smsInboxCursor = cResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
            return;
        }

        arrayAdapter.clear();

        do {
            String str = "SMS from:" + smsInboxCursor.getString(indexAddress) + '\n';
            str += smsInboxCursor.getString(indexBody);
            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

    //FROM THE LECTURER

    public void updateList(final String smsMsg) {
        //new sms must be on top of the list
        arrayAdapter.insert(smsMsg, 0);
        arrayAdapter.notifyDataSetChanged();
    }


    private boolean checkIfMyFGServiceRunning()
    {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (MyService.class.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;

    }
}