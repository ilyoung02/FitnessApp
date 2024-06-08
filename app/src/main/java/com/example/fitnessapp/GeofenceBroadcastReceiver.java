package com.example.fitnessapp;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "GeofenceBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationHelper notificationHelper = new NotificationHelper(context);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // GeofencingEvent가 null인지 확인
        if (geofencingEvent == null || geofencingEvent.hasError()) {
            if (geofencingEvent != null) {
                Log.d(TAG, "onReceive: Error receiving geofence event: " + geofencingEvent.getErrorCode());
            } else {
                Log.d(TAG, "onReceive: Error receiving geofence event...");
            }
            return;
        }

        // 빼도 됨
        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence : geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }

        // 전환 타입 확인
        int transitionType = geofencingEvent.getGeofenceTransition();
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("헬스장 출석", "출석이 완료 되었습니다.", MapsActivity.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_DWELL", "You are dwelling within the geofence area.", MapsActivity.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("헬스장 퇴장", "헬스장에서 나오셨습니다.", MapsActivity.class);
                break;
            default:
                Log.d(TAG, "onReceive: Unknown geofence transition");
                break;
        }
    }
}