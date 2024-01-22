package com.unimore.habitodo.vista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.unimore.habitodo.R;

public class VistaAggiungiNuovoTask extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_aggiungi_nuovo_task, container, false);
        Log.d("logMio","ingresso in onCreateView di VistaAggiungiNuovoTask");
        inizializzaVista(vista);
        inizializzaAzioni();
        Log.d("logMio","fine inizializzazioni VistaAggiungiNuovoTask");
        return vista;
    }

    private void inizializzaAzioni() {
    }

    private void inizializzaVista(View vista) {
        
    }


}
