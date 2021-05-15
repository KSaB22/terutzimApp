package com.example.projectmeodchashuv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    TextView maintxt;
    MyListAdapter adapter;
    public static ArrayList<String> maintitle = new ArrayList<String>();
    public static ArrayList<String> subtitle = new ArrayList<String>();
    SharedPref sharedPref;
    AlertDialog.Builder builder;
    public static ArrayList<Teruz> beenOnThisDevice = new ArrayList<>();
    public static ArrayList<Request> getBackRequests = new ArrayList<>();
    public static ArrayList<Teruz> getBackTeruzim = new ArrayList<>();
    static String tempusername;
    int counter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        tempusername = sharedPref.GetUsername();
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        maintitle = new ArrayList<String>();
        subtitle = new ArrayList<String>();
        maintitle.add("תירוצים לכל סיבה");
        subtitle.add("");
        maintitle.add("תירוצים לבית ספר");
        subtitle.add("");

        maintxt = findViewById(R.id.maintxt);
        maintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if(counter == 13)
                    LoadingActivity.ee = true;
            }
        });

        adapter = new MyListAdapter(this, maintitle, subtitle);

        builder = new AlertDialog.Builder(this);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);


            Intent intent = new Intent(this, NotificationsService.class);
            startService(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.action_changeTheme == item.getItemId()) {
            builder.setMessage("אתה בטוח שאתה רוצה לשנות את ערכת הנושא?")
                    .setTitle("שינוי ערכות נושא")
                    .setCancelable(false)
                    .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            if (sharedPref.LoadDarkModeState()) {
                                sharedPref.setDarkModeState(false);
                                restartapp();
                            } else {
                                sharedPref.setDarkModeState(true);
                                restartapp();
                            }

                        }
                    })
                    .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "בחרת לא",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.show();

        }
        else if (R.id.suggest == item.getItemId()) {
            Intent i = new Intent(this, SuggestingActivity.class);
            startActivity(i);
        }
        /* else if (R.id.regi == item.getItemId()) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }*/
        else if (R.id.login == item.getItemId()) {
            if(sharedPref.GetUsername().equals("guest69")){
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
            else{
                sharedPref.SetUsername("guest69");
                Toast.makeText(this, "התנתקת מהמשתמש", Toast.LENGTH_SHORT).show();
            }
        }
        else if(R.id.request == item.getItemId()){
            Intent i = new Intent(this, RequestingActivity.class);
            startActivity(i);
        }
        else if(R.id.addterutz == item.getItemId()){
            Intent intent = new Intent(this, AddingActivity.class);
            startActivityForResult(intent, 0);
        }
        /* else if (R.id.signout == item.getItemId()) {
            sharedPref.SetUsername("guest69");
            Toast.makeText(this, "You signed out", Toast.LENGTH_SHORT).show();
        }*/
//        else if(R.id.smsSender == item.getItemId()){
//            Intent i = new Intent(this, MessagingActivity.class);
//            startActivity(i);
//        }
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
        Intent intent = new Intent(this, SortingActivity.class);
        if (i == 0)
            intent.putExtra("REASON", "הכל");
        else
            intent.putExtra("REASON", "בית ספר");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            getBackTeruzim.clear();
            getBackTeruzim.addAll(DataModel.teruzims);
            Teruz teruz = new Teruz(data.getStringExtra("REASON"), data.getStringExtra("TERUZ"), tempusername, 0);
            beenOnThisDevice.add(teruz);
            DataModel.teruzims.add(teruz);
            DataModel.saveTeruzim();


            adapter.notifyDataSetChanged();
        }

    }

    void restartapp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
