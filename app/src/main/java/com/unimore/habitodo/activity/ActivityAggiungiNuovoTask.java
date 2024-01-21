package com.unimore.habitodo.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unimore.habitodo.R;

public class ActivityAggiungiNuovoTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("logMio","ingresso di onCreate di ActivityAggiungiNuovoTask");
        setContentView(R.layout.activity_aggiungi_nuovo_task);
    }

    public void mostraMessaggio(){
        Toast.makeText(this, "Ciao", Toast.LENGTH_SHORT).show();
    }

}
