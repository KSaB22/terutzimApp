package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tluna,up,cr;
    Button btn,share;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);
        tluna = findViewById(R.id.Teruz);
        up = findViewById(R.id.upvotes);
        cr =findViewById(R.id.cr);
        btn = findViewById(R.id.upbtn);
        share = findViewById(R.id.share);
        share.setOnClickListener(this);
        btn.setOnClickListener(this);
        tluna.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getTluna());
        cr.setText(  "נוצר על ידי " + DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getCreator());
        up.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getUpvotes() + " העלאות חיוביות");
        ArrayList<String> temp = SharedPref.readListFromPref(this);
        if(temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                if (DataModel.teruzims.get(getIntent().getIntExtra("PLACE", 0)).getTluna().equals(temp.get(i))) {
                    btn.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v== btn) {
            int j = getIntent().getIntExtra("PLACE", 0);
            MainActivity.getBackTeruzim.clear();
            MainActivity.getBackTeruzim.addAll(DataModel.teruzims);
            DataModel.teruzims.get(j).addUpvote(j);
            MainActivity.teruzimOnThisDevice.add(DataModel.teruzims.get(j));
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i<MainActivity.teruzimOnThisDevice.size(); i++){
                temp.add(MainActivity.teruzimOnThisDevice.get(i).getTluna());
            }
            SharedPref.writeListInPref(this,temp);
            DataModel.saveTeruzim();
            up.setText(DataModel.teruzims.get(j).getUpvotes() +  " העלאות חיוביות");
            finish();
        }
        else if(v == share){
            /**
             * שולח למישהו בווצאפ אלא אם אין ווצאפ ואז שולח את המשתמש לMessagingActivity
             */
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getTluna());
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Intent i = new Intent(this, MessagingActivity.class);
                startActivity(i);
                i.putExtra("TERUZ", DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getTluna());
                finish();
            }
            finish();
        }
    }
}