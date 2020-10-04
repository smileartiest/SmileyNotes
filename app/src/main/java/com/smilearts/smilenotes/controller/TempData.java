package com.smilearts.smilenotes.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class TempData {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Context mContext;

    public TempData(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences("TempData" , mContext.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void Login(String UID , boolean Log){
        editor.putString("UID" , UID);
        editor.putBoolean("Log" , Log);
        editor.commit();
    }

    public void LastBackup(String date){
        editor.putString("BCDATE" , date).commit();
    }

    public String getLastBackup(){
        return sharedPreferences.getString("BCDATE" , null);
    }

    public void EnablePin(boolean sts){
        editor.putBoolean("Pin" , sts).commit();
    }

    public void AddToken(String token){
        editor.putString("TOKEN" , token).commit();
    }

    public void SetPin(String pin){
        editor.putString("PIN" , pin).commit();
    }

    public String getPin(){
        return sharedPreferences.getString("PIN" , null);
    }

    public boolean getPinStatus(){
        return sharedPreferences.getBoolean("Pin" , false);
    }

    public void Logout(){
        editor.clear().commit();
    }

    public String getUID(){
        return sharedPreferences.getString("UID" , null);
    }

    public boolean getLog(){
        return sharedPreferences.getBoolean("Log" , false);
    }

}
