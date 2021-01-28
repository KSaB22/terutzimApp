package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;
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
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggesting);
        cat = findViewById(R.id.newcat);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String message = cat.getText().toString();
        if ( message == null || message.length() < 1) {
            return;
        }
        if (CheckPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+972586021103" , null, message, null, null);
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean CheckPermission(String perm) {
        int check = ContextCompat.checkSelfPermission(this, perm);
        return check == PackageManager.PERMISSION_GRANTED;
    }
}