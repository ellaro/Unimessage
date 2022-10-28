package com.ellaro.lasttimeella;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class FourthActivity extends AppCompatActivity {
    private Button back3;
    private DatabaseReference ref;
    private String Bodysms, translatedsms;
    private TextView transView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        back3 = (Button) findViewById(R.id.back3);

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back3();
            }

        });
        Intent intent = this.getIntent();

        if(intent != null) {
            int item_index = intent.getIntExtra("Index", -1);

            ContentResolver cResolver = getContentResolver();
            Cursor smsInboxCursor = cResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");
            int indexDate = smsInboxCursor.getColumnIndex("date");


            if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
                return;
            }

            int cur_item_index = 0;
            while(cur_item_index < item_index){
                smsInboxCursor.moveToNext();
                cur_item_index++;
            }

            Bodysms = smsInboxCursor.getString(indexBody);
            Log.d("index", "" + item_index);

            String Address = smsInboxCursor.getString(indexAddress);
            String Date = smsInboxCursor.getString(indexDate);

            Long timestamp = Long.parseLong(Date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            Date finaldate = calendar.getTime();
            String smsDate = finaldate.toString();

            TextView bodyView = (TextView) findViewById(R.id.Body);
            bodyView.setText("Original SMS:\n" + Bodysms);

            TextView addressView = (TextView) findViewById(R.id.Address);
            addressView.setText("Phone Number:\n" + Address);

            TextView dateView = (TextView) findViewById(R.id.Date);
            dateView.setText("Date:\n" + smsDate);

            transView = (TextView)findViewById(R.id.translated);


            translatedsms = "";
            ref = FirebaseDatabase.getInstance().getReference();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot tmp: snapshot.getChildren()){
                        if(tmp.getKey().equals(Bodysms)){
                            translatedsms = (String) tmp.getValue();
                            transView.setText("Translated to:\n" + translatedsms);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            transView.setText(translatedsms);

        }
    }
    private void back3() {
        Intent newer = new Intent(this, SecondActivity.class);
        startActivity(newer);
    }
}