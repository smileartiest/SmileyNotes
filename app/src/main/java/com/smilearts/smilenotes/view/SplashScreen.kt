package com.smilearts.smilenotes.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.smilearts.smilenotes.R
import com.smilearts.smilenotes.controller.FireBaseDB
import com.smilearts.smilenotes.controller.TempData
import com.smilearts.smilenotes.model.FCMMOdel

class SplashScreen : AppCompatActivity() {

    var tempData: TempData? = null
    var TAG = "Splash Screen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        tempData = TempData(this@SplashScreen)

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    val token = task.result!!.token
                    tempData!!.AddToken(token)
                    val DEVICE_ID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                    if (DEVICE_ID.length != 0 && token.length != 0) {
                        val model = FCMMOdel(DEVICE_ID, token)
                        FireBaseDB.REGURL.child(DEVICE_ID).setValue(model)
                    }
                    Log.d(TAG, token + "," + DEVICE_ID + "," + Build.BRAND + "/" + Build.MODEL)
                })
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        Handler().postDelayed({
            if (tempData!!.pinStatus) {
                startActivity(Intent(applicationContext, SecretPinNumber::class.java))
                finish()
            } else {
                startActivity(Intent(applicationContext, MainPage::class.java))
                finish()
            }
        }, 3000)

    }
}