package com.unimore.habitodo.vista;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimore.habitodo.R;

public class VistaProva1 extends Fragment {

    //private Bundle bundle;
    //private String nomeBundleContatore = "Contatore";
    //private Button bottoneMostraMsg;
    //private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_prova_1, container, false);
        inizializzaVista(vista);
        inizializzaAzioni();
        return vista;
    }

    private void inizializzaVista(View vista) {
        //bottoneMostraMsg = vista.findViewById(R.id.bottoneMostraMsg);
        //progressBar = vista.findViewById(R.id.progressBar);
    }

    private void inizializzaAzioni() {
        //bottoneMostraMsg.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneMostraMessaggio());
        //bottoneMostraMsg.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneMostraActivityDueSenzaReturn());
        //bottoneMostraMsg.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneMostraActivityDueConreturn());
        //bottoneMostraMsg.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneLanciaIntentImplicito());
        //bottoneMostraMsg.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneLanciaAsyncTask());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // primo avvio o quando viene chiusa app in background
        super.onCreate(savedInstanceState);
        /*if(savedInstanceState!=null){
            bundle = savedInstanceState;
        }else{
            bundle = new Bundle();
            bundle.putInt(nomeBundleContatore,1);
        }*/
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(nomeBundleContatore,bundle.getInt(nomeBundleContatore));
    }

    @Override
    public void onStart() {
        // tra create e resume -> eseguito solo dopo on create
        super.onStart();
        /*int contatore = bundle.getInt(nomeBundleContatore);
        contatore++;
        bundle.putInt(nomeBundleContatore,contatore);*/
    }

    @Override
    public void onResume() {
        // appena si entra in activity
        super.onResume();
        //Toast.makeText(this.getContext(), "Valore Contatore : " + bundle.getInt(nomeBundleContatore), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        // avviene quando si cambia activity
        super.onPause();
    }

    @Override
    public void onStop() {
        // dopo che activity è onPause per un pò
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // fine ciclo vita o chiusura automatica
        super.onDestroy();
    }
}
