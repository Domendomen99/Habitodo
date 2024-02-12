package com.unimore.habitodo.controllo;

import android.util.Log;
import android.view.View;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.activity.ActivityAggiungiNuovoTask;
import com.unimore.habitodo.activity.ActivityDopoLogIn;
import com.unimore.habitodo.activity.ActivityLogIn;

public class ControlloDopoLogIn {

    // classe controllo semplice che lancia una activity come un dialog per eseguire aggiunta di task alla lista

    private View.OnClickListener azioneMostraActivityAggiungiNuovoTask = new AzioneMostraNuovoTask();

    public View.OnClickListener getAzioneMostraActivityAggiungiNuovoTask() {
        return azioneMostraActivityAggiungiNuovoTask;
    }

    private class AzioneMostraNuovoTask implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ActivityDopoLogIn activityDopoLogIn = (ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity();
            ActivityAggiungiNuovoTask.nuovaInstanza().show(activityDopoLogIn.getSupportFragmentManager(),"ActionBottomDialog");
        }
    }
}
