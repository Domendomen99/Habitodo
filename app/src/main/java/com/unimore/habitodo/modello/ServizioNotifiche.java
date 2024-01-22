package com.unimore.habitodo.modello;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.modello.ModelloToDo;

import java.util.ArrayList;
import java.util.List;

public class ServizioNotifiche extends JobService {
    private boolean isActive = true;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("logNot","onStartJob");
        inviaNotificaConriepilogoTuttiToDo(jobParameters);
        return true;
    }

    private void inviaNotificaConriepilogoTuttiToDo(JobParameters jobParameters) {
        Log.d("logNot","inviaNotificaConriepilogoTuttiToDo");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isActive){
                    return;
                }
                Log.d("logNot","run");
                List<ModelloToDo> listaToDo = new ArrayList<>();
                if(Applicazione.getInstance().getModello().getBean("listaToDo")!=null){
                    listaToDo= (List<ModelloToDo>) Applicazione.getInstance().getModello().getBean("listaToDo");
                }
                Log.d("logNot","lista : " + listaToDo.toString());
                String testoToDo="";
                for (ModelloToDo toDo:listaToDo) {
                    if(toDo.getStatus()==0){
                        Log.d("logNot","elementi lista : " + listaToDo.toString());
                        testoToDo = toDo.getTestoToDo();
                        // TODO: 22/01/2024 Gestire notifica
                    }
                }
                Log.d("logNot","FINE");
                jobFinished(jobParameters,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("logNot","Servizio notifiche terminato prima del previsto");
        isActive = false;
        return false;
    }
}
