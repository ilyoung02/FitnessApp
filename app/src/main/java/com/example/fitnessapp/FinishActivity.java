package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FinishActivity extends AppCompatActivity {
    private TextView weightLiftedValueTextView;
    private TextView caloriesBurnedValueTextView;
    private TextView exerciseTimeValueTextView;
    private Button completeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        weightLiftedValueTextView = findViewById(R.id.weightLiftedValueTextView);
        caloriesBurnedValueTextView = findViewById(R.id.caloriesBurnedValueTextView);
        exerciseTimeValueTextView = findViewById(R.id.exerciseTimeValueTextView);
        completeButton = findViewById(R.id.completeButton);

        loadDataFromDatabase();

        completeButton.setOnClickListener(v -> {
            Intent intent = new Intent(FinishActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadDataFromDatabase() {
        // 데이터베이스에서 데이터 가져오기
        // 스프링 서버 통신 코드 작성
    }
}
