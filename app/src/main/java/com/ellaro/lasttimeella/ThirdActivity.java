package com.ellaro.lasttimeella;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private ListView list;
    private Button back2;
    private ArrayList<String> smsMsgList = new ArrayList<String>();
    private ArrayAdapter arrayadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        back2 = (Button) findViewById(R.id.back3);

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }

        });
        list = (ListView)findViewById(R.id.list);
        arrayadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, smsMsgList);
        list.setAdapter(arrayadapter);


        ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap: snapshot.getChildren()){
                    arrayadapter.add("Original SMS:  " + snap.getKey()+ "\nTranslated:  " + snap.getValue());

                }
                arrayadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void back() {
        Intent newer = new Intent(this, SecondActivity.class);
        startActivity(newer);
    }



}