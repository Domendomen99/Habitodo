package com.unimore.habitodo.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unimore.habitodo.R;

public class ActivityProva1 extends AppCompatActivity {

    public static final String TAG = ActivityProva1.class.getSimpleName();
    //public static final String permesso = Manifest.permission.READ_CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_1);
        //controlloPermessi();
    }

    public void mostraMessaggio(){
        Toast.makeText(this, "Ciao", Toast.LENGTH_SHORT).show();
    }

    /*private void controlloPermessi() {
        if(this.getApplicationContext().checkCallingOrSelfPermission(permesso)!= PackageManager.PERMISSION_GRANTED){
            Log.d("logg","permessi non concessi");
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                int codiceRichiesta=99;
                this.requestPermissions(new String[]{permesso},codiceRichiesta);
            }
        }else{
            Log.d("logg","ho i permessi per continuare");
        }
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // controllo permesso 99 - READ_CONTACTS
        if(requestCode==99){
            for(int i = 0; i<permissions.length;i++){
                if(permissions[i].equals(permesso)){
                    if(grantResults.length>i){
                        if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                            Log.d("logg","permessi concessi");
                        }else{
                            Log.d("logg","permesso non concesso");
                        }
                    }
                }
            }
        }
    }*/

    /*public void mostraActivityDueSenzaReturn(){
        Intent intent = new Intent(this,ActivityDue.class);
        Log.d("logg","intent creato");
        this.startActivity(intent);
        Log.d("logg","fine intent");
    }*/

    /*public void mostraActivityDueConreturn(){
        Intent intent = new Intent(this,ActivityDue.class);
        intent.putExtra("Stringa","Stringa");
        Log.d("logg","stringa inviata : " + intent.getExtras().getString("Stringa"));
        this.startActivityForResult(intent,1);
        Log.d("logg","activity due lanciata");
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            Log.d("logg","ottenuto un risultato da fine activity");
            String stringa = data.getExtras().getString("Stringa");
            Log.d("logg","stringa ricevuta : " + stringa);
        } else if (requestCode==2 && resultCode==RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            Log.d("logg","cattura immagine andata a buon fine");
        } else if (resultCode!=RESULT_OK) {
            Log.d("logg","result dell'activity non andato a buon fine : " + resultCode);
        }
    }*/

    /*public void mostraSveglie(){
        Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        this.startActivity(intent);
    }*/

    /*public void apriFotocamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent,2);
    }*/


}
