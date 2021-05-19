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
import android.widget.TextView;

import java.util.ArrayList;

public class ViewRequestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView log,asker,comms;
    ListView lv;
    SharedPref sharedPref;
    int whichrequest;
    public static ArrayList<String> maintitle = new ArrayList<String>();
    public static ArrayList<String> subtitle = new ArrayList<String>();
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        log = findViewById(R.id.therequest);
        asker = findViewById(R.id.asker);
        comms = findViewById(R.id.comms);
        lv = findViewById(R.id.anslv);

        whichrequest = getIntent().getIntExtra("REQUEST", 0);

        maintitle.clear();
        subtitle.clear();

        log.setText(DataModel.requests.get(whichrequest).getLog());
        asker.setText("נוצר על ידי  " + DataModel.requests.get(whichrequest).getUser());
        if(DataModel.requests.get(whichrequest).getIDofAnswers() == null||  DataModel.requests.get(whichrequest).getIDofAnswers().isEmpty()){
            comms.setText("עדיין לא נכתבו תירוצים");
            lv.setVisibility(View.GONE);
        }
        else{
            for (int i = 0; i < DataModel.requests.get(whichrequest).getIDofAnswers().size(); i++){
                maintitle.add(DataModel.teruzims.get(DataModel.requests.get(whichrequest).getIDofAnswers().get(i)).getTluna());
                subtitle.add(DataModel.teruzims.get(DataModel.requests.get(whichrequest).getIDofAnswers().get(i)).getUpvotes() + " העלאות חיוביות");
            }
            adapter = new MyListAdapter(this ,maintitle, subtitle);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this);
        }
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
            i.putExtra("CATEGORY", DataModel.requests.get(whichrequest).getCategory());
            startActivityForResult(i, 0);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            MainActivity.getBackTeruzim.clear();
            MainActivity.getBackTeruzim.addAll(DataModel.teruzims);
            Teruz teruz = new Teruz(data.getStringExtra("REASON"), data.getStringExtra("TERUZ"), data.getStringExtra("CREATOR"), 1);
            MainActivity.teruzimOnThisDevice.add(teruz);
            DataModel.teruzims.add(teruz);
            DataModel.saveTeruzim();
            if(DataModel.requests.get(whichrequest).getIDofAnswers() != null)
                DataModel.requests.get(whichrequest).getIDofAnswers().add(DataModel.teruzims.size()-1);
            else{
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(DataModel.teruzims.size()-1);
                DataModel.requests.get(whichrequest).setIDofAnswers(temp);
            }

            DataModel.saveRequests();
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int i, long l) {
        Intent intent = new Intent(this, ViewingActivity.class);
        intent.putExtra("PLACE", DataModel.requests.get(whichrequest).getIDofAnswers().get(i));
        startActivity(intent);
        finish();
    }
}