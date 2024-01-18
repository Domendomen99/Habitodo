package com.unimore.habitodo.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unimore.habitodo.R;

public class ActivityLogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void mostraMessaggio(){
        Toast.makeText(this, "Ciao", Toast.LENGTH_SHORT).show();
    }

}
