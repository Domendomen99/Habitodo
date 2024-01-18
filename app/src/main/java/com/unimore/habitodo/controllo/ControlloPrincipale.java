package com.unimore.habitodo.controllo;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;
import com.unimore.habitodo.activity.ActivityPrincipale;
import com.unimore.habitodo.vista.VistaPrincipale;

public class ControlloPrincipale {

    /*private Button.OnClickListener azioneMostraMessaggio = new AzioneMostraMessaggio();
    private Button.OnClickListener azioneMostraActivityDueSenzaReturn = new AzioneMostraActivityDueSenzaReturn();
    private View.OnClickListener azioneMostraActivityDueConreturn = new AzioneMostraActivityDueConReturn();
    private View.OnClickListener azioneLanciaIntentImplicito = new AzioneLanciaIntentImplicito();
    private View.OnClickListener azioneLanciaAsyncTask = new AzioneLanciaAsyncTask();

    public View.OnClickListener getAzioneLanciaAsyncTask() {
        return azioneLanciaAsyncTask;
    }

    public View.OnClickListener getAzioneLanciaIntentImplicito() {
        return azioneLanciaIntentImplicito;
    }

    public View.OnClickListener getAzioneMostraActivityDueConreturn() {
        return azioneMostraActivityDueConreturn;
    }

    public Button.OnClickListener getAzioneMostraMessaggio() {
        return azioneMostraMessaggio;
    }

    public Button.OnClickListener getAzioneMostraActivityDueSenzaReturn() {
        return azioneMostraActivityDueSenzaReturn;
    }

    private class AzioneMostraMessaggio implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.mostraMessaggio();
        }
    }

    private class AzioneMostraActivityDueSenzaReturn implements Button.OnClickListener {
        @Override
        public void onClick(View view) {

            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.mostraActivityDueSenzaReturn();
        }
    }

    private class AzioneMostraActivityDueConReturn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("logg", "AZIONE :  AzioneMostraActivityDueConReturn");
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.mostraActivityDueConreturn();
        }
    }

    private class AzioneLanciaIntentImplicito implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            //activityPrincipale.mostraSveglie();
            activityPrincipale.apriFotocamera();
        }
    }


    private class AzioneLanciaAsyncTask implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ProgressBar pbar = Applicazione.getInstance().getCurrentActivity().findViewById(R.id.progressBar);
            Log.d("logg","esecuzione azione task");
            int numero = 12983;
            AsyncTaskProva asyncTaskProva = new AsyncTaskProva(numero,pbar);
            Log.d("logg","dichiarazione riuscita");
            asyncTaskProva.execute(numero);
            Log.d("logg","avvio task riuscito");
        }

        private class AsyncTaskProva extends AsyncTask<Integer,Integer,Integer>{

            //private ProgressBar pbar;
            private int progresso;
            private ProgressBar pbar;

            public AsyncTaskProva(int progresso,ProgressBar pbar) {
                this.pbar = pbar;
                this.progresso = progresso;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pbar.setProgress(0);
            }

            @Override
            protected Integer doInBackground(Integer... integers) {
                Log.d("logg","multitasking in corso");
                int num = integers[0];
                for(int i = 0; i<=num;i++){
                    Log.d("logg","contatore : " + i);
                    publishProgress(i);
                }
                /*int div=2;
                int num = integers[0];
                Log.d("logg","num : " + num);
                for(int i=2;i<num;i++){
                    Log.d("logg","divBackground : " + div);
                    if(i%10==0){
                        publishProgress(i);
                    }
                    if((num%i)==0){
                        div++;
                    }
                }*/
                /*return num;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                pbar.setProgress(100);
            }

            // gestione avanzamento prograsso bg
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                float progress = ( int)(values[0])/(float)progresso;
                pbar.setProgress(( int)(progress* 100));
            }
        }

    }*/
}
