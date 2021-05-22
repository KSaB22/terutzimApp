package com.example.projectmeodchashuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button submit,regibtn;
    EditText name, pass;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.LoadDarkModeState())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = findViewById(R.id.login);
        name = findViewById(R.id.un);
        pass = findViewById(R.id.pw);
        regibtn = findViewById(R.id.regis);
        regibtn.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    /**
     * בודקת אם הגל תקני ןאם כן יוצרת מתחברת למשתמש אם לא מודיע שיש טעות
     * או שולחת לעמוד הרשמות
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v == submit)
        {
            if (name.getText().toString().equals("") || pass.getText().toString().equals("")) {
                Toast.makeText(this, "fill in all boxes", Toast.LENGTH_SHORT).show();
            } else {
                boolean flag = false;
                int temp = 0;
                for (int i = 0; i < DataModel.users.size() && !flag; i++) {
                    if (DataModel.users.get(i).getUsername().equals(name.getText().toString())) {
                        flag = true;
                        temp = i;
                    }
                }
                if (!flag) {
                    Toast.makeText(this, "No account with that username", Toast.LENGTH_SHORT).show();
                } else {
                    if (DataModel.users.get(temp).getPassword().equals(pass.getText().toString()) && DataModel.users.get(temp).getUsername().equals(name.getText().toString())) {
                        sharedPref.SetUsername(name.getText().toString());
                        finish();
                    }
                }
            }
        }
        else if(v == regibtn){
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
            finish();
        }
    }
}