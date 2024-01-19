package com.unimore.habitodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.unimore.habitodo.R;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("logMio","esecuzione di splashScreen");
        setContentView(R.layout.activity_splash);
    }
}