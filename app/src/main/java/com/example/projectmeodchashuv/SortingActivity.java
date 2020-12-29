package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SortingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
        ArrayList<Integer> rembIndex= new ArrayList<>();
        ListView lv;
        MyListAdapter adapter;
        public static ArrayList maintitle;
        public static ArrayList subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);
        maintitle = new ArrayList<String>();
        subtitle = new ArrayList<String>();
        rembIndex.clear();
        for (int i = 0; i< DataModel.teruzims.toArray().length; i++){
             if(DataModel.teruzims.get(i).getReason().equals(getIntent().getStringExtra("REASON"))){
                 rembIndex.add(i);
                 maintitle.add(DataModel.teruzims.get(i).getTluna());
                 subtitle.add(DataModel.teruzims.get(i).getUpvotes() + " העלאות חיוביות");
             }
        }
        lv = findViewById(R.id.lv);
        adapter=new MyListAdapter(this, maintitle, subtitle);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int i, long id) {
        Intent intent = new Intent(this , ViewingActivity.class);
        intent.putExtra("PLACE" , rembIndex.get(i));
        startActivity(intent);
        finish();
    }
}