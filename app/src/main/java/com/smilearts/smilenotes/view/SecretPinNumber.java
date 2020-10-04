package com.smilearts.smilenotes.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.controller.FireBaseDB;
import com.smilearts.smilenotes.controller.TempData;

import dmax.dialog.SpotsDialog;

public class SecretPinNumber extends AppCompatActivity {

    EditText pin_number ;
    TextView text1 , text2 , text3 , text4 , text5 , text6 , text7 , text8 , text9 , text0 ;
    TempData tempData;
    int count = 0;
    SpotsDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_pin_number);
        initialise();
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.pin_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin1 = pin_number.getText().toString();
                if(pin1.length()!=0 && pin1.length()==4){
                    loading.show();
                    CheckPin(pin1);
                }else if(pin1.length()!=0 && pin1.length()==6){
                    loading.show();
                    CheckPin(pin1);
                }else {
                    Snackbar.make(v , "Please enter valid pin number" , Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        text0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"0");
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"1");
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"2");
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"3");
            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"4");
            }
        });
        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"5");
            }
        });
        text6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"6");
            }
        });
        text7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"7");
            }
        });
        text8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"8");
            }
        });
        text9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = pin_number.getText().toString();
                pin_number.setText(temp+"9");
            }
        });

        pin_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==4){
                    loading.show();
                    CheckPin(s.toString());
                }else if(s.length()>4){
                    pin_number.setError("Not valid");
                }
            }
        });

    }

    private void CheckPin(final String pin ){
        if(pin.equals(tempData.getPin())){
            loading.dismiss();
            startActivity(new Intent(getApplicationContext() , MainPage.class));finish();
        }else {
            loading.dismiss();
            Toast.makeText(SecretPinNumber.this, "Incorrect Pin Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialise(){
        pin_number = findViewById(R.id.pin_pinnumber);
        text0 = findViewById(R.id.pin_pin0);
        text1 = findViewById(R.id.pin_pin1);
        text2 = findViewById(R.id.pin_pin2);
        text3 = findViewById(R.id.pin_pin3);
        text4 = findViewById(R.id.pin_pin4);
        text5 = findViewById(R.id.pin_pin5);
        text6 = findViewById(R.id.pin_pin6);
        text7 = findViewById(R.id.pin_pin7);
        text8 = findViewById(R.id.pin_pin8);
        text9 = findViewById(R.id.pin_pin9);
        tempData = new TempData(SecretPinNumber.this);
        loading = new SpotsDialog(SecretPinNumber.this);
    }

    @Override
    public void onBackPressed() {
        count++;
        if(count >1){
            finishAffinity();
        }else {
            Toast.makeText(this, "Press again to close", Toast.LENGTH_SHORT).show();
        }
    }
}