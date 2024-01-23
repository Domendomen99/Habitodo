package com.unimore.habitodo;

import android.app.Activity;
import android.app.Application;
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

    private static Applicazione singleton;

    public static Applicazione getInstance() {
        return singleton;
    }

    public void onCreate() {
        super.onCreate();
        Log.d("logMio","Singleton");
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestoreAttivita());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Applicazione getSingleton() {
        return singleton;
    }
    private Activity currentActivity = null;
    private Modello modello = new Modello();
    private ControlloProva1 controlloProva1 = new ControlloProva1();
    private ControlloLogIn controlloLogIn = new ControlloLogIn();
    private ControlloDopoLogIn controlloDopoLogIn = new ControlloDopoLogIn();
    private ControlloAggiungiNuovoTask controlloAggiungiNuovoTask = new ControlloAggiungiNuovoTask();
    private ControlloAdapterToDo controlloAdapterToDo = new ControlloAdapterToDo();
    private ServizioNotifiche servizioNotifiche = new ServizioNotifiche();

    ////////////////////////////////////////////////////////////////////////////////////////////////


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

