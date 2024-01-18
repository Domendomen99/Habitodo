package com.unimore.habitodo.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.unimore.habitodo.R;

public class ActivityProva2 extends AppCompatActivity {

    //public static final String TAG = ActivityDue.class.getSimpleName();
    //private String stringaRicevuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("logg","oncreate di activ2");
        setContentView(R.layout.activity_prova_2);
        Log.d("logg","fine onCreate activity2");
        //Intent intent = this.getIntent();
        //stringaRicevuta = intent.getExtras().getString("Stringa");
        //stringaRicevuta = stringaRicevuta.toUpperCase();
        //Log.d("logg","nuova stringa : " + stringaRicevuta);
    }

    /*
    @Override
    public void finish() {
        Intent i = new Intent();
        Log.d("logg","esecuzione finish");
        i.putExtra("Stringa",stringaRicevuta);
        setResult(RESULT_OK,i);
        super.finish();
    }
     */
}
