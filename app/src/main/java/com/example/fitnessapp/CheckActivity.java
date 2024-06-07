package com.example.fitnessapp;

import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import android.Manifest;
import android.content.Intent;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {
    private GeofencingClient geofencingClient;
    private GeoFenceHelper geofenceHelper;
    private TextView checkStatus;
    private Button startChecking;

    private static final String GEOFENCE_ID = "CHECK_GEOFENCE_ID";
    private static final float GEOFENCE_RADIUS = 200; // meters
    private static final int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private static final int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        checkStatus = findViewById(R.id.checkStatus);
        startChecking = findViewById(R.id.startChecking);

        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeoFenceHelper(this);

        if (Build.VERSION.SDK_INT >= 29) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                startGeofencing();
            }
        } else {
            startGeofencing();
        }

        startChecking.setOnClickListener(v -> {
            startGeofencing();
            startChecking.setVisibility(Button.GONE);
        });
    }

    private void startGeofencing() {
        try {
            // 특정 위치의 좌표 설정
            // 테스트 용 LatLng(35.8710526, 128.5593409) = 공차 위치
            LatLng checkLocation = new LatLng(35.8710526, 128.5593409);
            addGeofence(checkLocation, GEOFENCE_RADIUS);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while setting up geofence.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addGeofence(LatLng latLng, float radius) {
        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);

        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            return;
        }

        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(aVoid -> {
                    checkStatus.setText("Geofence added for attendance check.");
                    Toast.makeText(CheckActivity.this, "Geofence added successfully", Toast.LENGTH_SHORT).show();
                    // 출석이 성공하면 메인 화면으로 이동
                    Intent mainIntent = new Intent(CheckActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish(); // 현재 액티비티 종료
                })
                .addOnFailureListener(e -> {
                    String errorMessage = geofenceHelper.getErrorString(e);
                    checkStatus.setText("Failed to add geofence: " + errorMessage);
                    Toast.makeText(CheckActivity.this, "Failed to add geofence", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGeofencing();
            } else {
                checkStatus.setText("Fine location permission denied.");
            }
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGeofencing();
            } else {
                checkStatus.setText("Background location permission denied.");
            }
        }
    }
}
