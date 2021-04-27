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
import android.widget.ListView;

import java.util.ArrayList;

public class SortingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<Integer> rembIndex = new ArrayList<>();
    ListView lv;
    MyListAdapter adapter;
    public static ArrayList maintitle;
    public static ArrayList subtitle;
    SharedPref sharedPref;
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
        setContentView(R.layout.activity_sorting);
        maintitle = new ArrayList<String>();
        subtitle = new ArrayList<String>();
        rembIndex.clear();
        for (int i = 0; i < DataModel.teruzims.toArray().length; i++) {
            if (DataModel.teruzims.get(i).getReason().equals(getIntent().getStringExtra("REASON"))) {
                rembIndex.add(i);
                maintitle.add(DataModel.teruzims.get(i).getTluna());
                subtitle.add(DataModel.teruzims.get(i).getUpvotes() + " העלאות חיוביות");
            }
        }
        lv = findViewById(R.id.lv);
        adapter = new MyListAdapter(this, maintitle, subtitle);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(R.id.add == item.getItemId()){
            Intent i = new Intent(this, AddingActivity.class);
            i.putExtra("ISSET", true);
            i.putExtra("CATEGORY", getIntent().getStringExtra("REASON"));
            startActivityForResult(i, 0);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int i, long id) {
        Intent intent = new Intent(this, ViewingActivity.class);
        intent.putExtra("PLACE", rembIndex.get(i));
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            MainActivity.getBackTeruzim.clear();
            MainActivity.getBackTeruzim.addAll(DataModel.teruzims);
            Teruzim teruz = new Teruzim(data.getStringExtra("REASON"), data.getStringExtra("TERUZ"), tempusername, 0);
            MainActivity.beenOnThisDevice.add(teruz);
            DataModel.teruzims.add(teruz);
            DataModel.saveTeruzim();
            finish();
        }

    }
}