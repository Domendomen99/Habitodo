package com.unimore.habitodo.controllo;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.activity.ActivityDopoLogIn;
import com.unimore.habitodo.activity.ActivityLogIn;
import com.unimore.habitodo.adapter.AdapterToDo;
import com.unimore.habitodo.vista.VistaDopoLogIn;

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
