package com.unimore.habitodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.Costanti;
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

    public void lanciaIntentLogInGoogle(){
        Log.d("logMio","esecuzione del metodo lanciaIntentLogInGoogle");
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        GoogleSignInClient googleSignInClient = (GoogleSignInClient) Applicazione.getInstance().getModello().getBean("googleSignInClient");
        Log.d("logMio","tutti gli oggetti recuperati da bean");
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, Costanti.RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Costanti.RC_SIGN_IN && resultCode==RESULT_OK){
            Log.d("logMio","ricevo qualcosa da lanciaIntentLogInGoogle");
        } else if (requestCode==Costanti.RC_SIGN_IN && resultCode!=RESULT_OK) {
            Log.d("logMio","chiamata activity in lanciaIntentLogInGoogle non ha ritornato correttamente ");
        }
    }
}
