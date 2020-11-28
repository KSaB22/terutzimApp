package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tluna,up,cr;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);
        tluna = findViewById(R.id.Teruz);
        up = findViewById(R.id.upvotes);
        cr =findViewById(R.id.cr);
        btn = findViewById(R.id.upbtn);
        btn.setOnClickListener(this);
        tluna.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getTluna());
        cr.setText(  "נוצר על ידי " + DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getCreator());
        up.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getUpvote() + "upvote(s)");
    }

    @Override
    public void onClick(View v) {
        DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).addupvote();
        DataModel.save();
        up.setText(DataModel.teruzims.get(getIntent().getIntExtra("PLACE",0)).getUpvote() + "upvote(s)");
        finish();
    }
}