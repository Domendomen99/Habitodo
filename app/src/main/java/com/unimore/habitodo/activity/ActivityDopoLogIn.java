package com.unimore.habitodo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.Costanti;
import com.unimore.habitodo.R;
import com.unimore.habitodo.vista.VistaDopoLogIn;
import com.unimore.habitodo.vista.VistaLogIn;

import java.util.HashMap;

public class ActivityDopoLogIn extends AppCompatActivity {

    // permesso di invio notifiche
    public static final String permesso = Manifest.permission.POST_NOTIFICATIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("logMio","ingresso di onCreate di ActivityDopoLogIn");
        setContentView(R.layout.activity_dopo_login);

        // obbligatorio
        controlloPermessi();
    }

    // richiede di accettare il fatto che si vogliano ricevere notifiche da parte dell'applicazione
    private void controlloPermessi() {
        if(this.getApplicationContext().checkCallingOrSelfPermission(permesso)!= PackageManager.PERMISSION_GRANTED){
            Log.d("logg","permessi non concessi");
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                int codiceRichiesta=99;
                this.requestPermissions(new String[]{permesso},codiceRichiesta);
            }
        }else{
            Log.d("logg","ho i permessi per continuare");
        }
    }

    public void mostraMessaggio(){
        Toast.makeText(this, "Ciao", Toast.LENGTH_SHORT).show();
    }

    public VistaDopoLogIn getVistaDopoLogIn(){
        return (VistaDopoLogIn) getSupportFragmentManager().findFragmentById(R.id.vistaDopoLogIn);
    }



}
