package com.ellaro.lasttimeella;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button start;
    TextView unimsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("mylog","MainActivity");
        start = (Button)findViewById(R.id.start);
        unimsg = (TextView) findViewById(R.id.name);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_second();
            }
        });

    }

    private void start_second(){
        Intent newer = new Intent(this, SecondActivity.class);
        startActivity(newer);
    }
}