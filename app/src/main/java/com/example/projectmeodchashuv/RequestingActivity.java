package com.example.projectmeodchashuv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RequestingActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {

    ListView requestList;
    MyListAdapter adapter;
    SharedPref sharedPref;
    public static ArrayList<String> maintitle = new ArrayList<String>();
    public static ArrayList<String> subtitle = new ArrayList<String>();
    static String tempusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        tempusername = sharedPref.GetUsername();
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


        requestList =  findViewById(R.id.requestlv);
        requestList.setAdapter(adapter);
        requestList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(R.id.add == item.getItemId()){
            Intent i = new Intent(this, AddingRequestActivity.class);
            startActivityForResult(i, 0);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){

            DataModel.requests.add(new Request(tempusername,data.getStringExtra("LOG"),data.getStringExtra("CATEGORY")));
            DataModel.saveRequests();
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
        Intent intent = new Intent(this, ViewRequestActivity.class);
        intent.putExtra("REQUEST", i);
        startActivity(intent);
        finish();
    }
}