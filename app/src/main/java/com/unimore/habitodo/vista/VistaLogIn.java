package com.unimore.habitodo.vista;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;

public class VistaLogIn extends Fragment {

    private Button bottoneLogIn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_login, container, false);
        inizializzaVista(vista);
        inizializzaAzioni();
        autenticazioneFirebase();
        return vista;
    }

    private void autenticazioneFirebase() {
    }

    private void inizializzaVista(View vista) {
        this.bottoneLogIn = vista.findViewById(R.id.bottoneLogIn);
    }

    private void inizializzaAzioni() {
        this.bottoneLogIn.setOnClickListener(Applicazione.getInstance().getControlloLogIn().getAzionePremiBottoneLogIn());
    }

}
