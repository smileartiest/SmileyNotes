package com.smilearts.smilenotes.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.smilearts.smilenotes.BuildConfig;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.controller.FireBaseDB;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.controller.TempData;

import java.io.File;

import dmax.dialog.SpotsDialog;

public class SettingPage extends AppCompatActivity {

    Toolbar myToolbar;
    TempData tempData;
    Switch password_switch;
    TextView complete;
    Dialog d;
    RoomDB roomDB;
    SpotsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);
        initialise();
        CreateFolder();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (tempData.getPinStatus()) {
            password_switch.setChecked(true);
            password_switch.setText("4 digit Password is Enabled");
        } else {
            password_switch.setChecked(false);
            password_switch.setText("4 digit Password is disabled");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.settings_clear_all_notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(SettingPage.this);
                ad.setTitle("Delete ");
                ad.setMessage("Are you conform to delete all notes ?");
                ad.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        roomDB.notesDao().ClearNotes();
                        Snackbar.make(v, " Successful Cleared All Notes", Snackbar.LENGTH_SHORT).show();
                    }
                });
                ad.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        findViewById(R.id.settings_changepassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPasswordDialog();
            }
        });

        findViewById(R.id.settings_backup_notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view , "Wait for Next update " , Snackbar.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.settings_restore_notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view , "Wait for Next update " , Snackbar.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.settings_share_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Smiley Notes - Handle Day to Day Notes");
                String shareMessage = "\nLet me recommend you this application. Best Notes Application in Play Store\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }
        });

        findViewById(R.id.settings_update_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }
        });

        findViewById(R.id.settings_rate_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AppRating(view);
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }
        });

        findViewById(R.id.settings_about_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://samplephpdata.000webhostapp.com")));
            }
        });

        password_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    tempData.EnablePin(false);
                    password_switch.setText("4 digit Password is disabled");
                } else {
                    tempData.EnablePin(true);
                    password_switch.setText("4 digit Password is Enabled");
                    openPasswordDialog();
                }
            }
        });

    }

    private void CreateFolder(){
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmikyBackup/";    // it will return root directory of internal storage
            File root = new File(path);
            if (!root.exists()) {
                root.mkdirs();       // create folder if not exist
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AppRating(final View view){
        ReviewManager manager = ReviewManagerFactory.create(SettingPage.this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(Task<ReviewInfo> task) {
                if(task.isSuccessful()){
                    ReviewInfo info = task.getResult();
                    Snackbar.make(view , "Thank you for review "+info.toString() , Snackbar.LENGTH_SHORT).show();
                }else {
                    Log.d("Setting Page" , task.toString());}
            }
        });
    }

    private void openPasswordDialog() {
        d.setContentView(R.layout.dialog_changepassword);
        d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCanceledOnTouchOutside(false);
        final TextInputLayout new_Password = d.findViewById(R.id.dialog_password_new);
        final TextInputLayout conform_Password = d.findViewById(R.id.dialog_password_conform);
        complete = d.findViewById(R.id.dialog_password_change);
        TextView cancel = d.findViewById(R.id.dialog_password_cancel);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_Password.getEditText().getText().toString().equals(conform_Password.getEditText().getText().toString())) {
                    tempData.SetPin(new_Password.getEditText().getText().toString());
                    Snackbar.make(v, "Password change successful", Snackbar.LENGTH_SHORT).show();
                    d.dismiss();
                } else {
                    conform_Password.getEditText().setError("Check Password");
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cancel();
            }
        });

        d.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });
        d.show();
    }

    private void initialise() {
        myToolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        tempData = new TempData(SettingPage.this);
        password_switch = findViewById(R.id.settings_passwordswitch);
        roomDB = RoomDB.getInstance(SettingPage.this);
        d = new Dialog(SettingPage.this);
        dialog = new SpotsDialog(SettingPage.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}