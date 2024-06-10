package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChallengeViewEditActivity extends AppCompatActivity {

    private LinearLayout setContainer;
    private int setCount = 1;
    private ArrayList<String> data = new ArrayList<>();
    private String exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengeviewedit);

        exercise = getIntent().getStringExtra("exercise");
        data = getIntent().getStringArrayListExtra("data");
        setContainer = findViewById(R.id.set_container);

        for (String detail : data) {
            addSetDetail(detail);
        }

        Button addSetButton = findViewById(R.id.add_set_button);
        addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewSet();
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndReturn();
            }
        });
    }

    private void addNewSet() {
        setCount++;
        addSetDetail(setCount + "세트, 0 kg / 0 회");
    }

    private void addSetDetail(String detail) {
        LinearLayout newSetLayout = new LinearLayout(this);
        newSetLayout.setOrientation(LinearLayout.HORIZONTAL);
        newSetLayout.setPadding(4, 4, 4, 4);

        TextView setText = new TextView(this);
        setText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        setText.setText(detail);
        setText.setTextSize(16);

        newSetLayout.addView(setText);
        setContainer.addView(newSetLayout);
    }

    private void saveAndReturn() {
        data.clear();
        for (int i = 0; i < setContainer.getChildCount(); i++) {
            LinearLayout setLayout = (LinearLayout) setContainer.getChildAt(i);
            TextView setText = (TextView) setLayout.getChildAt(0);
            data.add(setText.getText().toString());
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("exercise", exercise);
        resultIntent.putStringArrayListExtra("data", data);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
