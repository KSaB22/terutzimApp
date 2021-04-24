package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RequestingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button addingbtn;
    ListView requestList;
    MyListAdapter adapter;
    SharedPref sharedPref;
    public static ArrayList<String> maintitle = new ArrayList<String>();
    public static ArrayList<String> subtitle = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requesting);
        maintitle.clear();
        subtitle.clear();
        for (int i = 0; i < DataModel.requests.toArray().length; i++){
            maintitle.add(DataModel.requests.get(i).getLog());
            subtitle.add("בקטגורית " + DataModel.requests.get(i).getCategory());
        }

        adapter = new MyListAdapter(this, maintitle, subtitle);

        addingbtn = findViewById(R.id.btnadd);
        addingbtn.setOnClickListener(this);

        requestList =  findViewById(R.id.requestlv);
        requestList.setAdapter(adapter);
        requestList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
        Intent intent = new Intent(this, ViewRequestActivity.class);
        intent.putExtra("REQUEST", i);
        startActivity(intent);
        finish();
    }
}