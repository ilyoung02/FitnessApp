package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class ChallengeViewActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST_CODE = 1;
    private static final String EXERCISE_1 = "펙덱 플라이";
    private static final String EXERCISE_2 = "시티드 케이블 로우";

    private HashMap<String, ArrayList<String>> exerciseData = new HashMap<>();
    private LinearLayout exerciseListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengeview);

        exerciseListContainer = findViewById(R.id.exercise_list_container);

        // Initialize data
        exerciseData.put(EXERCISE_1, new ArrayList<>());
        exerciseData.put(EXERCISE_2, new ArrayList<>());

        TextView editButton1 = findViewById(R.id.edit_button_1);
        editButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editExercise(EXERCISE_1);
            }
        });

        TextView editButton2 = findViewById(R.id.edit_button_2);
        editButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editExercise(EXERCISE_2);
            }
        });

        Button completeButton = findViewById(R.id.complete_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the completion of the workout
                finish();
            }
        });

        updateExerciseList();
    }

    private void editExercise(String exercise) {
        Intent intent = new Intent(ChallengeViewActivity.this, ChallengeViewEditActivity.class);
        intent.putExtra("exercise", exercise);
        intent.putStringArrayListExtra("data", exerciseData.get(exercise));
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    private void updateExerciseList() {
        exerciseListContainer.removeAllViews();
        for (String exercise : exerciseData.keySet()) {
            LinearLayout exerciseLayout = new LinearLayout(this);
            exerciseLayout.setOrientation(LinearLayout.HORIZONTAL);
            exerciseLayout.setPadding(8, 8, 8, 8);
            exerciseLayout.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

            TextView exerciseView = new TextView(this);
            exerciseView.setText(exercise);
            exerciseView.setTextSize(18);
            exerciseView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            exerciseView.setPadding(8, 8, 8, 8);
            exerciseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showExerciseDetails(exercise);
                }
            });

            TextView editButton = new TextView(this);
            editButton.setText("...");
            editButton.setTextSize(18);
            editButton.setPadding(8, 8, 8, 8);
            editButton.setClickable(true);
            editButton.setFocusable(true);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editExercise(exercise);
                }
            });

            exerciseLayout.addView(exerciseView);
            exerciseLayout.addView(editButton);
            exerciseListContainer.addView(exerciseLayout);
        }
    }

    private void showExerciseDetails(String exercise) {
        // Show the sets and reps for the selected exercise
        ArrayList<String> details = exerciseData.get(exercise);
        LinearLayout detailsLayout = new LinearLayout(this);
        detailsLayout.setOrientation(LinearLayout.VERTICAL);
        detailsLayout.setPadding(8, 8, 8, 8);

        for (String detail : details) {
            TextView detailView = new TextView(this);
            detailView.setText(detail);
            detailsLayout.addView(detailView);
        }

        exerciseListContainer.addView(detailsLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            String exercise = data.getStringExtra("exercise");
            ArrayList<String> details = data.getStringArrayListExtra("data");
            exerciseData.put(exercise, details);
            updateExerciseList();
        }
    }
}
