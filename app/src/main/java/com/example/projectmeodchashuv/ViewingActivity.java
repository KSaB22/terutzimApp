package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tluna, up, cr;
    Button upbtn, share, dnbtn;
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
        cr = findViewById(R.id.cr);
        upbtn = findViewById(R.id.upbtn);
        dnbtn= findViewById(R.id.rebtn);
        share = findViewById(R.id.share);
        share.setOnClickListener(this);
        upbtn.setOnClickListener(this);
        dnbtn.setOnClickListener(this);
        tluna.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE", 0)).getTluna());
        cr.setText("נוצר על ידי " + DataModel.teruzims.get(getIntent().getIntExtra("PLACE", 0)).getCreator());
        up.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE", 0)).getUpvotes() + "upvote(s)");
        ArrayList<String> temp = SharedPref.readListFromPref(this);
        if (temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                if (DataModel.teruzims.get(getIntent().getIntExtra("PLACE", 0)).getTluna().equals(temp.get(i))) {
                    upbtn.setVisibility(View.GONE);
                    dnbtn.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == upbtn) {
            int j = getIntent().getIntExtra("PLACE", 0);
            MainActivity.niggerBack.clear();
            MainActivity.niggerBack.addAll(DataModel.teruzims);
            DataModel.teruzims.get(j).addUpvote(j);
            MainActivity.mine.add(DataModel.teruzims.get(j));
            DataModel.saveTeruzim();
            up.setText(DataModel.teruzims.get(j).getUpvotes() + "upvote(s)");
            finish();
        } else if (v == share) {
            //Intent i = new Intent(this, MessagingActivity.class);
            //i.putExtra("TERUZ", DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getTluna() );
            //startActivity(i);
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, DataModel.teruzims.get(getIntent().getIntExtra("PLACE", 0)).getTluna());
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
            finish();
        } else if(v == dnbtn){
            MainActivity.niggerBack.clear();
            MainActivity.niggerBack.addAll(DataModel.teruzims);
            int j = getIntent().getIntExtra("PLACE", 0);
            ArrayList<String> temp = sharedPref.readListFromPref(getApplicationContext());
            for(int i=0; i < temp.size(); i++){
                if(temp.get(i))// todo: sayem bbait
            }
            temp.remove()
            sharedPref.writeListInPref(getApplicationContext(), temp);
            MainActivity.mine.remove();
            DataModel.teruzims.get(j).setUpvotes(DataModel.teruzims.get(j).getUpvotes()-1);
            DataModel.saveTeruzim();
            upbtn.setVisibility(View.VISIBLE);
            dnbtn.setVisibility(View.GONE);
        }

    }
}