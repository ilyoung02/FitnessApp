package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RoutineActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_EXERCISE = 1;

    private Button backButton;
    private Button completeButton;
    private LinearLayout mondayExerciseLayout;
    private LinearLayout wednesdayExerciseLayout;
    private LinearLayout fridayExerciseLayout;

    private String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        backButton = findViewById(R.id.backButton);
        completeButton = findViewById(R.id.completeButton);
        mondayExerciseLayout = findViewById(R.id.mondayExerciseLayout);
        wednesdayExerciseLayout = findViewById(R.id.wednesdayExerciseLayout);
        fridayExerciseLayout = findViewById(R.id.fridayExerciseLayout);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 완료 버튼을 클릭하면 메인 화면으로 이동
                Intent intent = new Intent(RoutineActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.addMondayExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = "월";
                Intent intent = new Intent(RoutineActivity.this, AddExerciseActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EXERCISE);
            }
        });

        findViewById(R.id.addWednesdayExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = "수";
                Intent intent = new Intent(RoutineActivity.this, AddExerciseActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EXERCISE);
            }
        });

        findViewById(R.id.addFridayExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = "금";
                Intent intent = new Intent(RoutineActivity.this, AddExerciseActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EXERCISE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_EXERCISE && resultCode == RESULT_OK && data != null) {
            String category = data.getStringExtra("category");
            String exercise = data.getStringExtra("exercise");
            addExerciseToRoutine(selectedDay, category, exercise);
        }
    }

    private void addExerciseToRoutine(String day, String category, String exercise) {
        LinearLayout exerciseLayout;
        switch (day) {
            case "월":
                exerciseLayout = mondayExerciseLayout;
                break;
            case "수":
                exerciseLayout = wednesdayExerciseLayout;
                break;
            case "금":
                exerciseLayout = fridayExerciseLayout;
                break;
            default:
                return;
        }

        LinearLayout exerciseItemLayout = new LinearLayout(this);
        exerciseItemLayout.setOrientation(LinearLayout.HORIZONTAL);
        exerciseItemLayout.setBackgroundColor(getResources().getColor(R.color.gray));
        exerciseItemLayout.setPadding(8, 8, 8, 8);
        exerciseItemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView categoryTextView = new TextView(this);
        categoryTextView.setText(category);
        categoryTextView.setTextSize(14);
        categoryTextView.setTextColor(getResources().getColor(R.color.black));
        categoryTextView.setBackgroundColor(getResources().getColor(R.color.teal_200));
        categoryTextView.setPadding(4, 4, 4, 4);
        exerciseItemLayout.addView(categoryTextView);

        TextView exerciseTextView = new TextView(this);
        exerciseTextView.setText(exercise);
        exerciseTextView.setTextSize(14);
        exerciseTextView.setTextColor(getResources().getColor(R.color.black));
        exerciseTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        exerciseItemLayout.addView(exerciseTextView);

        Button removeButton = new Button(this);
        removeButton.setText("X");
        removeButton.setTextSize(14);
        removeButton.setTextColor(getResources().getColor(R.color.black));
        removeButton.setBackgroundResource(android.R.color.transparent);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseLayout.removeView(exerciseItemLayout);
            }
        });
        exerciseItemLayout.addView(removeButton);

        exerciseLayout.addView(exerciseItemLayout);
    }
}