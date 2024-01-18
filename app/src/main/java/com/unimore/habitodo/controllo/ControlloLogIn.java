package com.unimore.habitodo.controllo;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ControlloLogIn {

    private View.OnClickListener azionePremiBottoneLogIn = new AzionePremiBottoneLogIn();

    public View.OnClickListener getAzionePremiBottoneLogIn() {
        return azionePremiBottoneLogIn;
    }

    private class AzionePremiBottoneLogIn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("logMio","bottoneLogIn premuto");
        }
    }
}
