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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autenticazioneFirebase();
        setContentView(R.layout.activity_login);
    }

    public void mostraMessaggio(){
        Toast.makeText(this, "Ciao", Toast.LENGTH_SHORT).show();
    }

    private void autenticazioneFirebase() {
        Log.d("logMio","ingresso in metodo autenticazioneFirebase");
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("logMio","FirebaseAuth.getInstance() OK");
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("logMio","FirebaseDatabase.getInstance() OK");
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1084849677669-uv8o9d7okmnr4t1pn1vndl23204irn6n.apps.googleusercontent.com")
                .requestEmail()
                .build();
        Log.d("logMio","fine di op su googleSignInOptions");
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        Log.d("logMio","fine di op su googleSignInClient");
        Log.d("logMio","fine metodo autenticazioneFirebase");
        Applicazione.getInstance().getModello().putBean("firebaseAuth",firebaseAuth);
        Applicazione.getInstance().getModello().putBean("firebaseDatabase",firebaseDatabase);
        Applicazione.getInstance().getModello().putBean("googleSignInClient",googleSignInClient);
        Log.d("logMio","tutte le robe inserite nel modello");
    }

    public void lanciaIntentLogInGoogle(){
        Log.d("logMio","esecuzione del metodo lanciaIntentLogInGoogle");
        GoogleSignInClient googleSignInClient = (GoogleSignInClient) Applicazione.getInstance().getModello().getBean("googleSignInClient");
        Log.d("logMio","googleSignInClient : " + googleSignInClient.toString());
        Log.d("logMio","valore RC_SIGN_IN : " + Costanti.RC_SIGN_IN);
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
            Log.d("logMio","Contenuto di data ricevuto da fase di logIn : " + data.getExtras().toString());
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
