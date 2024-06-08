package com.example.fitnessapp.GeofenceApi;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class HealthLocation {
    // 여러 개 위치 좌표 예시
    public static final LatLng GYM_LOCATION_1 = new LatLng(35.869965, 128.559561); // 집
    public static final LatLng GYM_LOCATION_2 = new LatLng(35.8710526, 128.5593409); // 공차
    public static final LatLng GYM_LOCATION_3 = new LatLng(35.8322, 128.7539); // 영남대
    public static final LatLng GYM_LOCATION_4 = new LatLng(35.868, 128.602); // 동성로

    public static List<LatLng> getAllGymLocations() {
        List<LatLng> locations = new ArrayList<>();
        locations.add(GYM_LOCATION_1);
        locations.add(GYM_LOCATION_2);
        locations.add(GYM_LOCATION_3);
        locations.add(GYM_LOCATION_4);
        // 추가적인 위치를 리스트에 추가
        return locations;
    }
}
