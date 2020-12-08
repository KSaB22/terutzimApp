package com.example.projectmeodchashuv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button btnadd;
    ListView lv;
    MyListAdapter adapter;
    //ArrayList<Teruzim> teruzims;
    public static ArrayList<String> maintitle = new ArrayList<String>();
    public static ArrayList<String> subtitle = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maintitle = new ArrayList<String>();
        subtitle = new ArrayList<String>();
        for (int i = 0; i < DataModel.teruzims.size(); i++){
            maintitle.add("לשימוש " + DataModel.teruzims.get(i).getReason());
            subtitle.add(DataModel.teruzims.get(i).getUpvotes() + " העלאות חיוביות");
        }
        adapter=new MyListAdapter(this, maintitle, subtitle);

        btnadd = findViewById(R.id.btnadd);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        btnadd.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        //bdika
        /*
        Intent intent = new Intent(this, NotificationsService.class);
        startService(intent);*/
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddingActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
        Intent intent = new Intent(this , ViewingActivity.class);
        intent.putExtra("PLACE" , i);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Teruzim teruz = new Teruzim(data.getStringExtra("REASON"),data.getStringExtra("TERUZ"),data.getStringExtra("CREATOR"), 0);
            DataModel.teruzims.add(teruz);
            DataModel.save();


            adapter.notifyDataSetChanged();
        }

    }
}
