package com.example.fitnessapp.GeofenceApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fitnessapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        //LatLng(35.868, 128.602) = 동성로
        //LatLng(35.8322, 128.7539) = 영남대
        LatLng Yeongnam = new LatLng(35.8322, 128.7539);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Yeongnam);
        markerOptions.title("영남대학교");
        markerOptions.snippet("대학교");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Yeongnam, 10));
    }
}
