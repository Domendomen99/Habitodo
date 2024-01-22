package com.unimore.habitodo.controllo;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;
import com.unimore.habitodo.activity.ActivityAggiungiNuovoTask;
import com.unimore.habitodo.activity.ActivityDopoLogIn;
import com.unimore.habitodo.modello.ModelloToDo;
import com.unimore.habitodo.vista.VistaDopoLogIn;

public class ControlloAggiungiNuovoTask {

    private View.OnClickListener azioneAggiungiNuovoTask = new AzioneAggiungiNuovoTask();

    public View.OnClickListener getAzioneAggiungiNuovoTask() {
        return azioneAggiungiNuovoTask;
    }

    private class AzioneAggiungiNuovoTask implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("logMio","Inizio azione AzioneAggiungiNuovoTask");
            String testoNuovoTask = (String) Applicazione.getInstance().getModello().getBean("testoNuovoTask");
            int numeroTask = (int) Applicazione.getInstance().getModello().getBean("numeroTaskAttuale");
            int idNuovoTask = numeroTask;
            int statusNuovoTask = 0;
            Log.d("logMio","AzioneAggiungiNuovoTask : dati nuovo task ottenuti");
            inserisciSingoloTaskInFirebaseDB(new ModelloToDo(numeroTask,statusNuovoTask,testoNuovoTask));
            Log.d("logMio","AzioneAggiungiNuovoTask : chiamata a funzone inserisciSingoloTaskInFirebaseDB fatta");
            EditText campoTestoNuovoTask = (EditText) Applicazione.getInstance().getModello().getBean("campoTestoNuovoTask");
            campoTestoNuovoTask.setText("");
        }
    }

    private void inserisciSingoloTaskInFirebaseDB(ModelloToDo nuovoToDo){
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        String idToDo = Integer.toString(nuovoToDo.getId());
        firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("toDoList").child(idToDo).setValue(nuovoToDo);
    }
}
