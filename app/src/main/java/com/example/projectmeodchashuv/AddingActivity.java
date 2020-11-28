package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ETreason,ETcreator,ETteruz;
    Button btn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("teruzim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        ETreason = findViewById(R.id.reason);
        ETteruz = findViewById(R.id.theline);
        ETcreator = findViewById(R.id.creator);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(ETteruz.getText().toString().isEmpty())
            Toast.makeText(this, "לא רשמת תירוץ"  , Toast.LENGTH_SHORT).show();
        else if(ETreason.getText().toString().isEmpty())
            Toast.makeText(this, "לא רשמת שימוש"  , Toast.LENGTH_SHORT).show();
        else if(ETcreator.getText().toString().isEmpty())
            Toast.makeText(this, "לא רשמת את שם היוצר"  , Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent();
            intent.putExtra("REASON", ETreason.getText().toString());
            intent.putExtra("TERUZ", ETteruz.getText().toString());
            intent.putExtra("CREATOR", ETcreator.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
