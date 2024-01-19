package com.unimore.habitodo.vista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;

public class VistaDopoLogIn extends Fragment {

    private TextView labelNomeUtente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_dopo_login, container, false);
        Log.d("logMio","ingresso in onCreateView di VistaDopoLogIn");
        inizializzaVista(vista);
        inizializzaAzioni();
        Log.d("logMio","fine inizializzazioni vistaDopoLogIn");
        return vista;
    }

    private void inizializzaVista(View vista) {
        this.labelNomeUtente = vista.findViewById(R.id.labelNomeUtente);
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        this.labelNomeUtente.setText(user.getEmail());
    }

    private void inizializzaAzioni() {

    }

}
