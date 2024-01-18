package com.unimore.habitodo.controllo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.activity.ActivityLogIn;

public class ControlloLogIn {

    private View.OnClickListener azionePremiBottoneLogIn = new AzionePremiBottoneLogIn();

    public View.OnClickListener getAzionePremiBottoneLogIn() {
        return azionePremiBottoneLogIn;
    }

    private class AzionePremiBottoneLogIn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("logMio","bottoneLogIn premuto");
            googleSignIn();
        }

        private void googleSignIn() {
            Log.d("logMio","esecuzione del metodo googleSignIn");
            ActivityLogIn activityLogIn = (ActivityLogIn) Applicazione.getInstance().getCurrentActivity();
            activityLogIn.lanciaIntentLogInGoogle();
        }
    }
}
