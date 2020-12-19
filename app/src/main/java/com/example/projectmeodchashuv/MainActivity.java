package com.example.projectmeodchashuv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button btnadd;
    ListView lv;
    MyListAdapter adapter;
    //ArrayList<Teruzim> teruzims;
    public static ArrayList<String> maintitle = new ArrayList<String>();
    public static ArrayList<String> subtitle = new ArrayList<String>();
    SharedPref sharedPref;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maintitle = new ArrayList<String>();
        subtitle = new ArrayList<String>();
        for (int i = 0; i < DataModel.teruzims.size(); i++){
            maintitle.add("לשימוש " + DataModel.teruzims.get(i).getReason());
            subtitle.add(DataModel.teruzims.get(i).getUpvotes() + " העלאות חיוביות");
        }
        adapter=new MyListAdapter(this, maintitle, subtitle);

        builder = new AlertDialog.Builder(this);

        btnadd = findViewById(R.id.btnadd);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        btnadd.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        //bdika

        Intent intent = new Intent(this, NotificationsService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(R.id.action_changeTheme == item.getItemId()){
            //Uncomment the below code to Set the message and title from the strings.xml file
            builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage("Do you want to change the theme ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            if (sharedPref.LoadDarkModeState()){
                                sharedPref.setDarkModeState(true);
                                restartapp();
                            }
                            else{
                                sharedPref.setDarkModeState(false);
                                restartapp();
                            }

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("AlertDialogExample");
            alert.show();

        }
        else if(R.id.smsSender == item.getItemId()){
            Intent i = new Intent(this, MessagingActivity.class);
            startActivity(i);
        }
        return true;
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
            DataModel.saveTeruzim();


            adapter.notifyDataSetChanged();
        }

    }
    void restartapp(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
