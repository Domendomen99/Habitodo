package com.unimore.habitodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("logMio","esecuzione di splashScreen");
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        final Intent intent = new Intent(this, ActivityLogIn.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },1000);
    }
}