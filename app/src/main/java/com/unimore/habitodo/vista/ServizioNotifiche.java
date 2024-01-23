package com.unimore.habitodo.vista;

import static com.unimore.habitodo.Applicazione.CANALE_NOTIFICHE;

import android.Manifest;
import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;
import com.unimore.habitodo.modello.ModelloToDo;

import java.util.ArrayList;
import java.util.List;

public class ServizioNotifiche extends JobService {
    private NotificationManagerCompat notificationManager;
    private boolean isActive = true;
    private List<ModelloToDo> listaTuttiToDo;
    private List<ModelloToDo> listaToDoToDo = new ArrayList<>();
    String testoNotifica = "";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("logNot", "onStartJob");
        notificationManager = NotificationManagerCompat.from(this);

        inviaNotificaConriepilogoTuttiToDo(jobParameters);
        return true;
    }

    private void inviaNotificaConriepilogoTuttiToDo(JobParameters jobParameters) {
        Log.d("logNot", "inviaNotificaConriepilogoTuttiToDo");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isActive) {
                    return;
                }
                Log.d("logNot", "run");
                listaTuttiToDo = new ArrayList<>();
                if (Applicazione.getInstance().getModello().getBean("listaToDo") != null) {
                    Log.d("logNot", "lista to do popolata");
                    listaTuttiToDo = (List<ModelloToDo>) Applicazione.getInstance().getModello().getBean("listaToDo");
                } else {
                    Log.d("logNot", "lista to do vuota");
                }
                Log.d("logNot", "dimensione lista todo completa : " + listaTuttiToDo.size());
                for (ModelloToDo toDo : listaTuttiToDo) {
                    if (toDo.getStatus() == 0) {
                        Log.d("logNot", "todo con status 0 : " + toDo.toString());
                        listaToDoToDo.add(toDo);
                        testoNotifica = testoNotifica + " - " + toDo.getTestoToDo();
                    }
                }
                Log.d("logNot","testoNotifica : " + testoNotifica);
                Notification notification = new NotificationCompat.Builder(getApplicationContext(), CANALE_NOTIFICHE)
                        .setSmallIcon(R.drawable.ic_check)
                        .setContentTitle("awaiting completion")
                        .setContentText(testoNotifica)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("logNot","permesso notifiche MANCANTE");
                    return;
                }else{
                    Log.d("logNot","permesso notifiche OK");
                    notificationManager.notify(1, notification);
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
