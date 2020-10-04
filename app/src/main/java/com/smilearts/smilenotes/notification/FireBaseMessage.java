package com.smilearts.smilenotes.notification;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smilearts.smilenotes.controller.FireBaseDB;
import com.smilearts.smilenotes.model.FCMMOdel;

public class FireBaseMessage extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("Push Message " , remoteMessage.getNotification().getBody());
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        String DEVICE_ID = Settings.Secure.getString(getContentResolver() , Settings.Secure.ANDROID_ID);
        if(DEVICE_ID.length()!=0 && s.length()!=0) {
            FCMMOdel fcmmOdel = new FCMMOdel(DEVICE_ID, s+","+ Build.BRAND+"/"+Build.MODEL);
            FireBaseDB.REGURL.child(DEVICE_ID).setValue(fcmmOdel);
            Log.d("NEW TOKEN ", s);
        }
        super.onNewToken(s);
    }
}
