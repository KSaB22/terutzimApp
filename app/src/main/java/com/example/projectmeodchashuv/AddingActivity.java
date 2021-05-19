package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    EditText ETcreator, ETteruz;
    Button btn, chooser;
    String reason = "";
    ListView lv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("teruzim");
    MyListAdapter adapter;
    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        ETteruz = findViewById(R.id.theline);
        ETcreator = findViewById(R.id.creator);
        btn = findViewById(R.id.btn);
        lv = findViewById(R.id.lv);
        chooser = findViewById(R.id.btnch);
        chooser.setOnClickListener(this);

        if(getIntent().getBooleanExtra("ISSET", false)){
            chooser.setVisibility(View.GONE);
            reason = getIntent().getStringExtra("CATEGORY");
        }

        btn.setOnClickListener(this);
        ArrayList<String> maintitle = new ArrayList<String>();
        ArrayList<String> subtitle = new ArrayList<String>();

        maintitle.add("תירוצים לכל סיבה");
        subtitle.add("");
        maintitle.add("תירוצים לבית ספר");
        subtitle.add("");
        maintitle.add("תירוצים להורים");
        subtitle.add("");
        maintitle.add("תירוצים לספורט");
        subtitle.add("");
        maintitle.add("תירוצים לצבא");
        subtitle.add("");
        maintitle.add("תירוצים להברזה ממפגשים");
        subtitle.add("");
        adapter = new MyListAdapter(this, maintitle, subtitle);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn) {
            if (ETteruz.getText().toString().isEmpty())
                Toast.makeText(this, "לא בחרת תירוץ", Toast.LENGTH_SHORT).show();
            else if (reason.equals(""))
                Toast.makeText(this, "לא בחרת שימוש", Toast.LENGTH_SHORT).show();
            else if (sharedPref.GetUsername().equals("guest69"))
                Toast.makeText(this, "כנס  למשתמש כדי לפרסם תירוץ", Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent();
                intent.putExtra("REASON", reason);
                intent.putExtra("TERUZ", ETteruz.getText().toString());
                intent.putExtra("CREATOR", ETcreator.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else if (v == chooser) {
            lv.setVisibility(View.VISIBLE);
            chooser.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int i, long id) {
        switch (i) {
            case 0:
                reason = "הכל";
                break;
            case 1:
                reason = "בית ספר";
                break;
            case 2:
                reason = "הורים";
                break;
            case 3:
                reason =  "ספורט";
                break;
            case 4:
                reason = "צבא";
                break;
            case 5:
                reason =  "הברזה";
                break;
        }
        lv.setVisibility(View.GONE);
        chooser.setVisibility(View.VISIBLE);
        Toast.makeText(this, "בחרת " + reason, Toast.LENGTH_SHORT).show();
    }
}
