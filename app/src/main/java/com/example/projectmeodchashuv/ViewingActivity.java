package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tluna,up,cr;
    Button btn,share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        up.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getUpvotes() + "upvote(s)");
    }

    @Override
    public void onClick(View v) {
        if(v== btn) {
            int j = getIntent().getIntExtra("PLACE", 0);
            DataModel.teruzims.get(j).addUpvote(j);
            MainActivity.mine.add(DataModel.teruzims.get(j));
            DataModel.saveTeruzim();
            up.setText(DataModel.teruzims.get(j).getUpvotes() + "upvote(s)");
            finish();
        }
        else if(v == share){
            Intent i = new Intent(this, MessagingActivity.class);
            i.putExtra("TERUZ", DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getTluna() );
            startActivity(i);
            finish();
        }
    }
}