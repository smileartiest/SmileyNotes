package com.smilearts.smilenotes.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDB {
    public static DatabaseReference REGURL = FirebaseDatabase.getInstance().getReference("RegProfile");
}
