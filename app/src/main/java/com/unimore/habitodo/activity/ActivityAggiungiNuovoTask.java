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

public class ActivityAggiungiNuovoTask extends BottomSheetDialogFragment {

    EditText campoTestoNuovoTask;
    Button bottoneAggiungiTask;

    public static ActivityAggiungiNuovoTask nuovaInstanza(){
        return new ActivityAggiungiNuovoTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.StileAggiungiActivity);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nuovo_task,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inizializzaVista(view);
        inizializzaAzioni();
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
    }

    private void inizializzaVista(View view) {
        campoTestoNuovoTask = view.findViewById(R.id.campoTestoNuovoTask);
        bottoneAggiungiTask = view.findViewById(R.id.bottoneInserisciNuovoTask);
        bottoneAggiungiTask.setEnabled(false);
    }

    private void inizializzaAzioni() {
        campoTestoNuovoTask.addTextChangedListener(new ListnerTestoNuovoTask());
        bottoneAggiungiTask.setOnClickListener(Applicazione.getInstance().getControlloAggiungiNuovoTask().getAzioneAggiungiNuovoTask());
    }

    private class ListnerTestoNuovoTask implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.toString().equals("")){
                bottoneAggiungiTask.setEnabled(false);
                bottoneAggiungiTask.setTextColor(Color.GRAY);
            }else{
                bottoneAggiungiTask.setEnabled(true);
                bottoneAggiungiTask.setTextColor(ContextCompat.getColor(getContext(), com.google.android.material.R.color.design_default_color_primary_dark));
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
}
