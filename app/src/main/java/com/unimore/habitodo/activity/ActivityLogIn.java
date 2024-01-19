package com.unimore.habitodo.activity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Objects;

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
        GoogleSignInClient googleSignInClient = (GoogleSignInClient) Applicazione.getInstance().getModello().getBean("googleSignInClient");
        Log.d("logMio","tutti gli oggetti recuperati da bean");
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, Costanti.RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("logMio","risultato lancio activity da ActivityLogIn - codRichiesta :  " + requestCode + " - codRisultato : " + resultCode);
        if(requestCode==Costanti.RC_SIGN_IN /*&& resultCode==RESULT_OK*/){
            Log.d("logMio","ricevo qualcosa da lanciaIntentLogInGoogle");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                autenticazioneFirebase(account.getIdToken());

            }catch (Exception e){
                Log.d("logMio","eccezione in onActivityResult di ActivityLogIn");
                Log.d("logMio",e.getMessage() + e.getStackTrace(), e.getCause());
            }

        } /*else if (requestCode==Costanti.RC_SIGN_IN && resultCode!=RESULT_OK) {
            Log.d("logMio","chiamata activity in lanciaIntentLogInGoogle non ha ritornato correttamente ");
        }*/
    }

    private void autenticazioneFirebase(String account) {
        AuthCredential credenziali = GoogleAuthProvider.getCredential(account,null);
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        firebaseAuth.signInWithCredential(credenziali)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id",user.getUid());
                            map.put("name",user.getDisplayName());
                            map.put("profile",user.getPhotoUrl().toString());

                            firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(map);

                            Intent intentLanciaActivityDopoLogIn = new Intent(Applicazione.getInstance().getCurrentActivity(), ActivityLogIn.class);
                            startActivity(intentLanciaActivityDopoLogIn);

                        }else{
                            Log.d("logMio","qualcosa nell'inserimento dell'utente nel db è andato storto");
                        }

                    }
                });
    }
}
