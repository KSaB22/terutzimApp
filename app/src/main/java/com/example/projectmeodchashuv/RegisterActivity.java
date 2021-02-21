package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button submit;
    EditText name, pass;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submit = findViewById(R.id.regi);
        name = findViewById(R.id.un);
        pass = findViewById(R.id.pw);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(name.getText().toString().equals("")|| pass.getText().toString().equals("")){
            Toast.makeText(this, "fill in all boxes",Toast.LENGTH_SHORT).show();
        }
        else{
            boolean flag= true;
            for (int i = 0; i < DataModel.users.size() && flag; i++){
                if(DataModel.users.get(i).getUsername().equals(name.getText().toString())){
                    flag = false;
                }
            }
            if(flag){
                User temp = new User(name.getText().toString(),pass.getText().toString());
                DataModel.users.add(temp);
                DataModel.saveUsers();
                sharedPref.SetUsername(name.getText().toString());
                finish();
            }
            else
            {
                Toast.makeText(this,"Username already taken", Toast.LENGTH_SHORT).show();
            }
        }
    }
}