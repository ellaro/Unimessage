package com.ellaro.lasttimeella;


import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class SMSReceiver extends BroadcastReceiver {
//    public static final String SMS_BUNDLE = "pdus";//PDU - protocol data unit (SMS message)
    private DatabaseReference DataRef;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("mylog", "onReceive: ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp" + intent.getAction());

        DataRef = FirebaseDatabase.getInstance().getReference();
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            SmsMessage[] smsMessages = getMessagesFromIntent(intent);
            final String smsbody = smsMessages[0].getMessageBody();
            int mylog = Log.d("mylog", "sms body: " + smsbody);
            FirebaseTranslatorOptions opts = new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.HE)
                    .build();

            FirebaseTranslator translate = FirebaseNaturalLanguage.getInstance().getTranslator(opts);
            FirebaseModelDownloadConditions conds = new FirebaseModelDownloadConditions.Builder().build();

            translate.downloadModelIfNeeded(conds).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    translate.translate(smsbody).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String translated) {
                            new save_thread(smsbody, translated, DataRef).start();

                            Toast.makeText(context,
                                    "Translated to:\n" + translated,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
//            // translate

        }
    }

    private class save_thread extends Thread{
        String key, value;
        DatabaseReference ref;

        public save_thread(String given_key, String given_val, DatabaseReference reference){
            this.key = given_key;
            this.value = given_val;
            this.ref = reference;
        }
        @Override
        public void run(){
            this.ref.child(this.key).setValue(this.value);
        }
    }

}
