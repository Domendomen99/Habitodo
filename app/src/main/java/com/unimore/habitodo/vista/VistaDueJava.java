package com.unimore.habitodo.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.unimore.habitodo.R;

public class VistaDueJava extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_due, container, false);
        inizializzaVista(vista);
        inizializzaAzioni();
        return vista;
    }

    private void inizializzaVista(View vista) {
    }

    private void inizializzaAzioni() {
    }




}
