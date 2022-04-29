package com.test.forgroundservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(forgroundServiceRunning() == false) { // Make sure that service is not already running
                Intent serviceIntent = new Intent(MainActivity.this, MyForgroundService.class);
                startForegroundService(serviceIntent);
            }
        }
    }

    private boolean forgroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(MyForgroundService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}