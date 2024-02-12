package com.unimore.habitodo.controllo;

import android.util.Log;
import android.view.View;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.activity.ActivityDopoLogIn;
import com.unimore.habitodo.modello.AdapterToDo;
import com.unimore.habitodo.vista.VistaDopoLogIn;

// gestione dell'azione di click su cestino nella lista di elementi dei todo
// NON UTILIZZATO, definizione del metodo in AdapterToDo

public class ControlloAdapterToDo {

    private View.OnClickListener azioneEliminaTask = new AzioneEliminaTask();

    public View.OnClickListener getAzioneEliminaTask() {
        return azioneEliminaTask;
    }

    private class AzioneEliminaTask implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("logMio","AZIONE : azioneEliminaTask");
            ActivityDopoLogIn activityDopoLogIn = (ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity();
            VistaDopoLogIn vistaDopoLogIn = activityDopoLogIn.getVistaDopoLogIn();
            AdapterToDo adapterToDo = vistaDopoLogIn.getAdapterToDo();
            //adapterToDo.eliminaTask((Integer) Applicazione.getInstance().getModello().getBean("posizione"));
        }
    }
}
