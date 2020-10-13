package com.smilearts.smilenotes.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDB {
    public static DatabaseReference REGURL = FirebaseDatabase.getInstance().getReference("RegProfile");
    public static FirebaseAuth AUTHURl = FirebaseAuth.getInstance();
    public static FirebaseUser AUTHUSER = AUTHURl.getCurrentUser();
}
