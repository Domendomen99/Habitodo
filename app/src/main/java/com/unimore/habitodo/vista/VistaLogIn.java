package com.unimore.habitodo.vista;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

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


// la vista andrà poi visualizzata all'interno di un fragment che è contenuto nell'activity alla quale appartiene
public class VistaLogIn extends Fragment {

    private Button bottoneLogIn;
    private ProgressBar progressBar;


    // creazione dei componenti della vista e inizializzazioni
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_login, container, false);
        inizializzaVista(vista);
        inizializzaAzioni();
        Log.d("logMio","fine inizializzazioni vistaLogIn");
        return vista;
    }


    public ProgressBar getProgressBar() {
        return progressBar;
    }

    private void inizializzaVista(View vista) {
        this.bottoneLogIn = vista.findViewById(R.id.bottoneLogIn);
        this.progressBar = vista.findViewById(R.id.progressBar);
    }

    private void inizializzaAzioni() {
        this.bottoneLogIn.setOnClickListener(Applicazione.getInstance().getControlloLogIn().getAzionePremiBottoneLogIn());
    }

}
