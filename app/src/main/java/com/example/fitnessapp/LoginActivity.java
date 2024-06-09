package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.GeofenceApi.MapsActivity;

public class LoginActivity extends AppCompatActivity{
    private Button LoginBtn, RegisterBtn;
    private EditText loginid, loginpasswd;
    private CheckBox setid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        RegisterBtn = (Button) findViewById(R.id.RegisterBtn);
        loginid = (EditText) findViewById(R.id.loginid);
        loginpasswd = (EditText) findViewById(R.id.loginpasswd);
        setid = (CheckBox) findViewById(R.id.setid);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                String email = loginid.getText().toString().trim();
                String pwd = loginpasswd.getText().toString().trim();
                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(){
        String id = loginid.getText().toString().trim();
        String password = loginpasswd.getText().toString().trim();
    }
}
