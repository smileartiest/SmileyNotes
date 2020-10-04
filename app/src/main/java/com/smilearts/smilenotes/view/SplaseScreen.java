package com.smilearts.smilenotes.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smilearts.smilenotes.BuildConfig;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.controller.FireBaseDB;
import com.smilearts.smilenotes.controller.TempData;
import com.smilearts.smilenotes.model.FCMMOdel;

public class SplaseScreen extends AppCompatActivity {

    TempData tempData;
    String TAG = "Splase Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splase_screen);
        tempData = new TempData(SplaseScreen.this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        tempData.AddToken(token);
                        String DEVICE_ID = Settings.Secure.getString(getContentResolver() , Settings.Secure.ANDROID_ID);
                        if(DEVICE_ID.length()!=0 && token.length()!=0) {
                            FCMMOdel model = new FCMMOdel(DEVICE_ID, token);
                            FireBaseDB.REGURL.child(DEVICE_ID).setValue(model);
                        }
                        Log.d(TAG, token+","+DEVICE_ID+","+Build.BRAND+"/"+Build.MODEL);
                    }
                });
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(tempData.getPinStatus()) {
                    startActivity(new Intent(getApplicationContext(), SecretPinNumber.class));finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), MainPage.class));finish();
                }
            }
        },3000);

    }

}