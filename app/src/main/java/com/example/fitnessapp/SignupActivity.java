package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity  extends AppCompatActivity{

    private EditText input_email, input_name, input_password, input_age;
    private Button btn_register, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        input_email = (EditText) findViewById(R.id.input_email);
        input_name = (EditText) findViewById(R.id.input_name);
        input_password = (EditText) findViewById(R.id.input_password);
        input_age = (EditText) findViewById(R.id.input_age);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = input_email.getText().toString().trim();
                final String pwd = input_password.getText().toString().trim();
                final String name = input_name.getText().toString().trim();
                final String age = input_age.getText().toString().trim();
            }
        });


    }
}
