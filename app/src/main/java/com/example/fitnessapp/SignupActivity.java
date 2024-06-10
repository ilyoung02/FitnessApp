package com.example.fitnessapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.config.ApiService;
import com.example.fitnessapp.config.RetrofitClient;
import com.example.fitnessapp.domain.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity  extends AppCompatActivity{


    private EditText input_id, input_password;
    private Button btn_register, btn_cancel;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        apiService= RetrofitClient.getApiService();

        input_id = (EditText) findViewById(R.id.input_id);
        input_password = (EditText) findViewById(R.id.input_password);
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
                join();
            }
        });

    }


    private void join(){
        String password = input_password.getText().toString().trim();
        String id = input_id.getText().toString().trim();

        User user=new User();
        user.setId(id);
        user.setPassword(password);

        Call<User> call=apiService.join(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignupActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "error message: " + t.getMessage());
            }
        });
    }

}
