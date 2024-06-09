package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {

    private Button closeButton;
    private Button completeButton;
    private ListView categoryListView;
    private ListView exerciseListView;
    private LinearLayout selectedItemsLayout;

    private ArrayList<String> selectedItems;
    private String selectedCategory;
    private String selectedExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        closeButton = findViewById(R.id.closeButton);
        completeButton = findViewById(R.id.completeButton);
        categoryListView = findViewById(R.id.categoryListView);
        exerciseListView = findViewById(R.id.exerciseListView);
        selectedItemsLayout = findViewById(R.id.selectedItemsLayout);

        selectedItems = new ArrayList<>();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCategory != null && selectedExercise != null) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("category", selectedCategory);
                    resultIntent.putExtra("exercise", selectedExercise);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        setupCategoryListView();
    }

    private void setupCategoryListView() {
        String[] categories = {"가슴", "등", "하체", "어깨", "삼두", "코어", "유산소"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoryListView.setAdapter(categoryAdapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories[position];
                setupExerciseListView(selectedCategory);
            }
        });
    }

    private void setupExerciseListView(String category) {
        String[] exercises;
        switch (category) {
            case "가슴":
                exercises = new String[]{"펙덱 플라이", "니 푸쉬업", "인클라인 벤치프레스"};
                break;
            case "등":
                exercises = new String[]{"랫풀다운", "바벨 로우", "풀업"};
                break;
            case "하체":
                exercises = new String[]{"스쿼트", "레그 프레스", "런지"};
                break;
            case "어깨":
                exercises = new String[]{"숄더 프레스", "사이드 레터럴 레이즈", "프론트 레이즈"};
                break;
            case "삼두":
                exercises = new String[]{"트라이셉스 익스텐션", "덤벨 킥백", "케이블 푸쉬다운"};
                break;
            case "코어":
                exercises = new String[]{"크런치", "플랭크", "레그 레이즈"};
                break;
            case "유산소":
                exercises = new String[]{"러닝", "싸이클", "로잉"};
                break;
            default:
                exercises = new String[]{};
                break;
        }

        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exercises);
        exerciseListView.setAdapter(exerciseAdapter);

        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedExercise = exercises[position];
                addSelectedItemView(selectedCategory + ": " + selectedExercise);
            }
        });
    }

    private void addSelectedItemView(String item) {
        final TextView textView = new TextView(this);
        textView.setText(item);
        textView.setTextSize(16);
        textView.setPadding(8, 8, 8, 8);
        selectedItemsLayout.addView(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemsLayout.removeView(textView);
            }
        });
    }
}