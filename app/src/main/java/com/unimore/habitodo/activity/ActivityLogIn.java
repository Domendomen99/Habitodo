package com.unimore.habitodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.Costanti;
import com.unimore.habitodo.R;
import com.unimore.habitodo.vista.VistaLogIn;

import java.util.HashMap;

public class ActivityLogIn extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("logMio","-------------------------------------------");
        setContentView(R.layout.activity_login);
        settingPerAutenticazioneFirebase();
        FirebaseAuth auth = (FirebaseAuth)Applicazione.getInstance().getModello().getBean("firebaseAuth");
        if(auth.getCurrentUser()!=null){
            lanciaIntentActivityDopoLogIn();
            finish();
        }
    }

    private void lanciaIntentActivityDopoLogIn() {
        Intent intentLanciaActivityDopoLogIn = new Intent(this, ActivityDopoLogIn.class);
        startActivity(intentLanciaActivityDopoLogIn);
    }

    public void mostraMessaggio(){
        Toast.makeText(this, "Ciao", Toast.LENGTH_SHORT).show();
    }

    private void settingPerAutenticazioneFirebase() {
        Log.d("logMio","ingresso in metodo settingPerAutenticazioneFirebase");
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("logMio","FirebaseAuth.getInstance() OK");
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("logMio","FirebaseDatabase.getInstance() OK");
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1084849677669-uvk0caljl0lqvvlr6faqj30db0o60udk.apps.googleusercontent.com")
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
        Log.d("logMio","FINE : setting");
    }

    public void lanciaIntentLogInGoogle(){
        Log.d("logMio","esecuzione del metodo lanciaIntentLogInGoogle");
        GoogleSignInClient googleSignInClient = (GoogleSignInClient) Applicazione.getInstance().getModello().getBean("googleSignInClient");
        Log.d("logMio","googleSignInClient : " + googleSignInClient.toString());
        Log.d("logMio","valore RC_SIGN_IN : " + Costanti.RC_SIGN_IN);
        Log.d("logMio","tutti gli oggetti recuperati da bean");
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, Costanti.RC_SIGN_IN);
        Log.d("logMio","FINE : lanciaIntentLogInGoogle");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("logMio","risultato lancio activity da ActivityLogIn - codRichiesta :  " + requestCode + " - codRisultato : " + resultCode);
        if(requestCode==Costanti.RC_SIGN_IN){
            Log.d("logMio","ricevo qualcosa da lanciaIntentLogInGoogle");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("logMio","Contenuto di data ricevuto da fase di logIn : " + data.getExtras().toString());
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("logMio","ottenimento informazioni account : " + account.getEmail());
                inserimentoCredenzialiInFirebaseDB(account.getIdToken());

            }catch (Exception e){
                Log.d("logMio","eccezione in onActivityResult di ActivityLogIn");
                Log.d("logMio",e.getMessage() + e.getStackTrace() + e.getCause() + e.getClass() + e.getLocalizedMessage() + e.getSuppressed());
            }

        }
    }

    private void inserimentoCredenzialiInFirebaseDB(String account) {
        Log.d("logMio","esecuzione inserimentoCredenzialiInFirebaseDB");
        AuthCredential credenziali = GoogleAuthProvider.getCredential(account,null);
        Log.d("logMio","ottenimento credenziali");
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");

        visualizzaCaricamento();

        Log.d("logMio","oggetti firebaseAuth e firebaseDatabase recuperati da bean");
        firebaseAuth.signInWithCredential(credenziali)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // chiamata alla funzione che restituisce true se utente è da aggiungere a db
                            if(isUtenteNonRegistrato()){

                                // operazioni per aggiungere utente a db
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("id",user.getUid());
                                map.put("name",user.getDisplayName());
                                map.put("profile",user.getPhotoUrl().toString());
                                Log.d("logMio","fine inserimento dati in db");

                                firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(map);
                                Log.d("logMio","ottenuta reference da DB");

                                // codice inutile
                                /*Intent intentLanciaActivityDopoLogIn = new Intent(Applicazione.getInstance().getCurrentActivity(), ActivityDopoLogIn.class);
                                Log.d("logMio","creazione intent per lancio senconda activity");
                                startActivity(intentLanciaActivityDopoLogIn);*/

                            }

                            // dopo aver inserito utente nuovo se necessario si passa a activity riepilogo task
                            lanciaIntentActivityDopoLogIn();
                            Log.d("logMio","activityDopoLogInLanciata");
                        }else{
                            Log.d("logMio","qualcosa nell'inserimento dell'utente nel db è andato storto");
                        }

                    }
                });
    }

    private void visualizzaCaricamento() {
        // gestione animazione durante caricamento
        VistaLogIn vistaLogIn = this.getVistaLogIn();
        ProgressBar progressBar = vistaLogIn.getProgressBar();
        progressBar.setVisibility(View.VISIBLE);
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, 100);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }

    // funzione che restituisce true se utente è da inserire in db
    private boolean isUtenteNonRegistrato() {
        Log.d("logMio","isUtenteNonRegistrato : ingresso in utenteNonRegistrato");

        // ottengo tutti riferimenti necessari
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // costruisco la query che mi permette di capire se utente è già presente in db
        Query queryOttieniUtenteSePresente = firebaseDatabase.getReference().child("users");

        // variabile appoggio a cui vorrei assegnare il risultato della query
        boolean isNonRegistrato = true;

        // esecuzione della query in handler
        queryOttieniUtenteSePresente.addListenerForSingleValueEvent(ListnerSingleValueEventOttieniUtente(user));

        // controllo di isNonRegistrato
        if(isNonRegistrato){
            Log.d("logMio","isUtenteNonRegistrato : utente non registrato");
        }else{
            Log.d("logMio","isUtenteNonRegistrato : utente attualmente registrato");
        }
        return isNonRegistrato;
    }

    // operazioni della query
    private ValueEventListener ListnerSingleValueEventOttieniUtente(FirebaseUser user) {
        Log.d("logMio","ListnerSingleValueEventOttieniUtente : ");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String utenteAttuale = user.getUid();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Log.d("logMio","ListnerSingleValueEventOttieniUtente : dato ottenuto da db " + dataSnapshot.getKey());
                    Log.d("logMio","ListnerSingleValueEventOttieniUtente : dato dell'utente attuale " + utenteAttuale);
                    if(dataSnapshot.getKey().equals(utenteAttuale)){
                        Log.d("logMio","ListnerSingleValueEventOttieniUtente : utente già inserito in db");
                    }else{
                        Log.d("logMio","ListnerSingleValueEventOttieniUtente : utente da inserire in db");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        return valueEventListener;
    }

    public VistaLogIn getVistaLogIn(){
        return (VistaLogIn) getSupportFragmentManager().findFragmentById(R.id.vistaLogIn);
    }


    // codice copiato
    public class ProgressBarAnimation extends Animation {
        private ProgressBar progressBar;
        private float from;
        private float  to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }
}
