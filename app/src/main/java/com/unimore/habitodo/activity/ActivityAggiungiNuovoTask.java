package com.unimore.habitodo.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;
import com.unimore.habitodo.vista.VistaLogIn;

public class ActivityAggiungiNuovoTask extends BottomSheetDialogFragment {

    EditText campoTestoNuovoTask;
    Button bottoneAggiungiTask;

    // crea instanza dell'activity
    public static ActivityAggiungiNuovoTask nuovaInstanza(){
        return new ActivityAggiungiNuovoTask();
    }

    // setting dell'aspetto dell'activity
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.StileAggiungiActivity);
    }

    // dichiarazione layout
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nuovo_task,container,false);

        // setting del fatto che la activity segue la tastiera nel momento in cui viene mostrata
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inizializzaVista(view);
        inizializzaAzioni();

        // inutili
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
    }

    private void inizializzaVista(View view) {
        campoTestoNuovoTask = view.findViewById(R.id.campoTestoNuovoTask);
        bottoneAggiungiTask = view.findViewById(R.id.bottoneInserisciNuovoTask);
        bottoneAggiungiTask.setEnabled(false);

        // ottenere il testo contenuto nel campo
        Applicazione.getInstance().getModello().putBean("campoTestoNuovoTask",campoTestoNuovoTask);
    }

    private void inizializzaAzioni() {
        campoTestoNuovoTask.addTextChangedListener(new ListnerTestoNuovoTask());
        bottoneAggiungiTask.setOnClickListener(Applicazione.getInstance().getControlloAggiungiNuovoTask().getAzioneAggiungiNuovoTask());
    }

    // vileva modifiche all'interno del campo di testo
    private class ListnerTestoNuovoTask implements TextWatcher {

        // op da fare prima che il testo venga cambiato
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        // op quando il testo viene cambiato
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // se il contenuto è vuoto
            if(charSequence.toString().equals("")){
                bottoneAggiungiTask.setEnabled(false);
                bottoneAggiungiTask.setTextColor(Color.GRAY);
            }else{
                // se viene scritto qualcosa nel campo
                bottoneAggiungiTask.setEnabled(true);
                bottoneAggiungiTask.setTextColor(ContextCompat.getColor(getContext(), com.google.android.material.R.color.design_default_color_primary_dark));
                // inseirmento testo del campo nel modello
                Applicazione.getInstance().getModello().putBean("testoNuovoTask",campoTestoNuovoTask.getText().toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}

    }

    public String getTestoCampoTestoNuovoTask(){
        return campoTestoNuovoTask.getText().toString();
    }

    /*@Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){

        }
    }*/

    public EditText getCampoTestoNuovoTask() {
        return campoTestoNuovoTask;
    }
}
