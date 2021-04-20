package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

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
    public static ArrayList<String> maintitle = new ArrayList<String>();
    public static ArrayList<String> subtitle = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requesting);

        for (int i = 0; i < DataModel.requests.toArray().length; i++){
            maintitle.add(DataModel.requests.get(i).getLog());
            subtitle.add("בקטגורית" + DataModel.requests.get(i).getCategory());
        }

        adapter = new MyListAdapter(this, maintitle, subtitle);

        addingbtn = findViewById(R.id.btnadd);
        addingbtn.setOnClickListener(this);

        requestList.findViewById(R.id.lv);
        requestList.setAdapter(adapter);
        requestList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}