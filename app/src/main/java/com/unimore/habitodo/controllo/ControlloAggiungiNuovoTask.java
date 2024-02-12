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

// gestione delle operazioni da eseguire quando si preme il pulsante add dopo aver inserito il testo di
// un'attivtà da svolgere

public class ControlloAggiungiNuovoTask {

    private View.OnClickListener azioneAggiungiNuovoTask = new AzioneAggiungiNuovoTask();

    public View.OnClickListener getAzioneAggiungiNuovoTask() {
        return azioneAggiungiNuovoTask;
    }

    private class AzioneAggiungiNuovoTask implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("logMio","Inizio azione AzioneAggiungiNuovoTask");

            // ottengo testo da modello
            String testoNuovoTask = (String) Applicazione.getInstance().getModello().getBean("testoNuovoTask");

            // inizializzazione del numero totale dei task
            int numeroTask;
            if(Applicazione.getInstance().getModello().getBean("ultimoID")==null){
                numeroTask = 0;
            }else{
                numeroTask = (int) Applicazione.getInstance().getModello().getBean("ultimoID");
            }
            Log.d("logMio","numero task prima : " + numeroTask);

            // inizizalizzazione dell'id del nuovo task
            int idNuovoTask = numeroTask+1;
            Log.d("logMio","numero task dopo : " + idNuovoTask);
            //Applicazione.getInstance().getModello().putBean("numeroTaskAttuale",idNuovoTask);

            // lo status indica se la check box deve essere spuntata o meno
            int statusNuovoTask = 0;
            Log.d("logMio","AzioneAggiungiNuovoTask : dati nuovo task ottenuti");

            // inseriemento del task nel DB
            inserisciSingoloTaskInFirebaseDB(new ModelloToDo(idNuovoTask,statusNuovoTask,testoNuovoTask));
            Log.d("logMio","AzioneAggiungiNuovoTask : chiamata a funzone inserisciSingoloTaskInFirebaseDB fatta");

            // il campo nell'activity aggiungi task viene pulito
            EditText campoTestoNuovoTask = (EditText) Applicazione.getInstance().getModello().getBean("campoTestoNuovoTask");
            campoTestoNuovoTask.setText("");
        }
    }

    // operazioni per aggiungere il task a DB
    private void inserisciSingoloTaskInFirebaseDB(ModelloToDo nuovoToDo){
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        String idToDo = Integer.toString(nuovoToDo.getId());
        firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("toDoList").child(idToDo).setValue(nuovoToDo);
    }
}
