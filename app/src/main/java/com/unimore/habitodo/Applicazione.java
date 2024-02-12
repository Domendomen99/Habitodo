package com.unimore.habitodo;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.unimore.habitodo.controllo.ControlloAdapterToDo;
import com.unimore.habitodo.controllo.ControlloAggiungiNuovoTask;
import com.unimore.habitodo.controllo.ControlloDopoLogIn;
import com.unimore.habitodo.controllo.ControlloLogIn;
import com.unimore.habitodo.controllo.ControlloProva1;
import com.unimore.habitodo.vista.ServizioNotifiche;
import com.unimore.habitodo.modello.Modello;

public class Applicazione extends Application {

    // singleton - oggetto di cui si crea una unica istanza in modo da poter condividere dati in più parti del programma
    private static Applicazione singleton;

    // metodo che ci permette di richiamare il singletone e da qui spostarci al suo interno per ottenere informazioni
    // - utilizzo più frequente è quello di ottenere da d'ovunque ci si trovi il riferimento alla current activity in
    //   esecuzione che viene memorizzato ogni volta che ci si sposta su una nuova activity
    public static Applicazione getInstance() {
        return singleton;
    }

    public static final String CANALE_NOTIFICHE = "notificaTodo";

    public void onCreate() {
        super.onCreate();
        Log.d("logMio","Singleton");

        // inizializzazione di Applicazione
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestoreAttivita());
        creazioneCanaleNotifiche();
    }

    // operazione necessaria al lancio dell'app in modo che siano dichiarate tutte le notifiche che può lanciare
    private void creazioneCanaleNotifiche() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANALE_NOTIFICHE,
                    "notificaToDO",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("sending todo to be completed");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    // Dichiarazione dei componenti che vanno a formare l'applicazione
    public static Applicazione getSingleton() {
        return singleton;
    }
    private Activity currentActivity = null;
    private Modello modello = new Modello();

    // Refuso della fase di progettazione
    private ControlloProva1 controlloProva1 = new ControlloProva1();
    private ControlloLogIn controlloLogIn = new ControlloLogIn();
    private ControlloDopoLogIn controlloDopoLogIn = new ControlloDopoLogIn();
    private ControlloAggiungiNuovoTask controlloAggiungiNuovoTask = new ControlloAggiungiNuovoTask();
    private ControlloAdapterToDo controlloAdapterToDo = new ControlloAdapterToDo();
    private ServizioNotifiche servizioNotifiche = new ServizioNotifiche();

    ////////////////////////////////////////////////////////////////////////////////////////////////

    // metodi di get dei componenti in modo che siano ottnibili da ovunque ci si trovi
    public ServizioNotifiche getServizioNotifiche() {return servizioNotifiche;}
    public ControlloAdapterToDo getControlloAdapterToDo() {
        return controlloAdapterToDo;
    }
    public ControlloAggiungiNuovoTask getControlloAggiungiNuovoTask(){return controlloAggiungiNuovoTask;}
    public ControlloProva1 getControlloProva1() {return controlloProva1;}
    public ControlloDopoLogIn getControlloDopoLogIn() {return controlloDopoLogIn;}
    public ControlloLogIn getControlloLogIn() {return controlloLogIn;}
    public ControlloProva1 getControlloPrincipale() {return controlloProva1;}
    public Activity getCurrentActivity() {
        return this.currentActivity;
    }
    public Modello getModello() {
        return modello;
    }

    private class GestoreAttivita implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.d("logMio", "ACTIVITY : onActivityCreated: " + activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d("logMio", "ACTIVITY : onActivityDestroyed: " + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d("logMio", "ACTIVITY : onActivityStarted: " + activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d("logMio", "ACTIVITY : currentActivity initialized: " + activity);
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d("logMio", "ACTIVITY : onActivityPaused: " + activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (currentActivity == activity) {
                Log.d("logMio", "ACTIVITY : currentActivity stopped: " + activity);
                currentActivity = null;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d("logMio", "ACTIVITY : onActivitySaveInstanceState: " + activity);
        }
    }
}

