package com.unimore.habitodo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.unimore.habitodo.controllo.ControlloLogIn;
import com.unimore.habitodo.controllo.ControlloPrincipale;
import com.unimore.habitodo.modello.Modello;

public class Applicazione extends Application {

    public static final String TAG = Applicazione.class.getSimpleName();

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
    private ControlloPrincipale controlloPrincipale = new ControlloPrincipale();
    private ControlloLogIn controlloLogIn = new ControlloLogIn();

    ////////////////////////////////////////////////////////////////////////////////////////////////


    public ControlloLogIn getControlloLogIn() {return controlloLogIn;}
    public ControlloPrincipale getControlloPrincipale() {return controlloPrincipale;}
    public Activity getCurrentActivity() {
        return this.currentActivity;
    }
    public Modello getModello() {
        return modello;
    }

    private class GestoreAttivita implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            //Log.i(TAG, "onActivityCreated: " + activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            //Log.i(TAG, "onActivityDestroyed: " + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            //Log.d(TAG, "onActivityStarted: " + activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "currentActivity initialized: " + activity);
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //Log.d(TAG, "onActivityPaused: " + activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (currentActivity == activity) {
                Log.d("logMio", "currentActivity stopped: " + activity);
                currentActivity = null;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            //Log.d(TAG, "onActivitySaveInstanceState: " + activity);
        }
    }
}

