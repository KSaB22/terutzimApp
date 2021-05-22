package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SuggestingActivity extends AppCompatActivity implements View.OnClickListener {
    EditText cat;
    Button send, sms;
    SharedPref sharedPref;
    private static final int SMS_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggesting);
        cat = findViewById(R.id.newcat);
        sms = findViewById(R.id.storage);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
        sms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == send) {
            String message = cat.getText().toString();
            if (message == null || message.length() < 1) {
                return;
            }
            if (CheckPermission1(Manifest.permission.SEND_SMS)) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+972586021103", null, message, null, null);
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Permission to send sms denied", Toast.LENGTH_SHORT).show();
            }
        } else if (v == sms) {
            checkPermission(Manifest.permission.SEND_SMS, SMS_PERMISSION_CODE);
        }

    }

    /**
     *see MessagingActivity.checkPermission()
     */
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *see MessagingActivity.CheckPermission1()
     */
    public boolean CheckPermission1(String perm) {
        int check = ContextCompat.checkSelfPermission(this, perm);
        return check == PackageManager.PERMISSION_GRANTED;
    }
}