package com.unimore.habitodo.vista;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;

public class VistaLogIn extends Fragment {

    private Button bottoneLogIn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient googleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_login, container, false);
        inizializzaVista(vista);
        inizializzaAzioni();
        Log.d("logMio","fine inizializzazioni vistaLogIn");
        autenticazioneFirebase();
        return vista;
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
        googleSignInClient = GoogleSignIn.getClient(this.getContext(),googleSignInOptions);
        Log.d("logMio","fine di op su googleSignInClient");
        Log.d("logMio","fine metodo autenticazioneFirebase");
        Applicazione.getInstance().getModello().putBean("firebaseAuth",firebaseAuth);
        Applicazione.getInstance().getModello().putBean("firebaseDatabase",firebaseDatabase);
        Applicazione.getInstance().getModello().putBean("googleSignInClient",googleSignInClient);
        Log.d("logMio","tutte le robe inserite nel modello");
    }

    private void inizializzaVista(View vista) {
        this.bottoneLogIn = vista.findViewById(R.id.bottoneLogIn);
    }

    private void inizializzaAzioni() {
        this.bottoneLogIn.setOnClickListener(Applicazione.getInstance().getControlloLogIn().getAzionePremiBottoneLogIn());
    }

}
