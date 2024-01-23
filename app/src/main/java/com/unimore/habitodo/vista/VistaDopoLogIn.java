package com.unimore.habitodo.vista;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;
import com.unimore.habitodo.activity.ActivityDopoLogIn;
import com.unimore.habitodo.modello.AdapterToDo;
import com.unimore.habitodo.modello.ModelloToDo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VistaDopoLogIn extends Fragment {

    private TextView labelNomeUtente;
    private RecyclerView recyclerTask;
    private AdapterToDo adapterToDo;
    private List<ModelloToDo> listaToDo;
    private FloatingActionButton bottoneAggiungiTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_dopo_login, container, false);
        Log.d("logMio","ingresso in onCreateView di VistaDopoLogIn");
        inizializzaVista(vista);
        inizializzaAzioni();

        Log.d("logMio","fine inizializzazioni vistaDopoLogIn");
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("logMio","CurrentActivity in VistaDopoLogIn.onResume : " + Applicazione.getInstance().getCurrentActivity().toString());

        this.adapterToDo = new AdapterToDo((ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity());
        Log.d("logMio","inizializzazioneAdapterToDo andata a buon fine");

        recyclerTask.setAdapter(adapterToDo);
        Log.d("logMio","setAdapter andata a buon fine");

        //inserisciSingoloTaskInFirebaseDB(new ModelloToDo(0,0,"prova"));
        //inserisciSingoloTaskInFirebaseDB(new ModelloToDo(1,1,"prova1"));

        ottieniListaToDoDaFirebaseDB();

        // codice per capire come funziona inserimento utente
        //provaPutUtente();
        Log.d("logMio","listaToDo inizializzata per bene");
        //Log.d("logMio","listaToDo in OnResume : " + listaToDo.toString());

        adapterToDo.setListaToDo(listaToDo);

        Log.d("logMio","adapterToDo.setListaToDo(listaToDo); andato");

    }

    // codice utile in debugging
    // RISULTATO : ogni volta che accedo a app viene sovrascritto utente attuale e tutti i dati collegati vengono eliminati
    // SOLUZIONE : fare controllo durante login e evitare di reinserire utente quando già presente
    private void provaPutUtente() {
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",user.getUid());
        map.put("name",user.getDisplayName());
        map.put("profile",user.getPhotoUrl().toString());
        firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(map);
    }


    private void ottieniListaToDoDaFirebaseDB() {
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d("logMio","Path utente corrente : " + firebaseDatabase.getReference().child("users").child(user.getUid()));
        Log.d("logMio","Path listaToDo : " + firebaseDatabase.getReference().child("users").child(user.getUid()).child("toDoList"));
        Query queryOttieniListaToDoUtente = firebaseDatabase.getReference().child("users").child(user.getUid()).child("toDoList");
        Log.d("logMio","costruzione query andata a buon fine");
        Log.d("logMio","ESECUZIONE QUERY richiesta dati");
        queryOttieniListaToDoUtente.addValueEventListener(ValueEventListenerOttieniTask());
        Log.d("logMio","QUERY andata a buon fine");
    }

    private ValueEventListener ValueEventListenerOttieniTask() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c = 0;
                int id;
                int status;
                String testoToDo;
                ArrayList<ModelloToDo> listaAppoggio = new ArrayList<>();

                // PROBLEMA : il metodo di scorrere gli id con contatore non funziona in quanto quando si elimina un'elemento in mezzo si ricerca un elemento con id=c e non viene trovato
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //versione nuova
                    Log.d("logOttenimentoDati","ottenuto da db : " + snapshot.getChildren());
                    Log.d("logOttenimentoDati","ottenuto da db : " + dataSnapshot.child("id"));
                    id = Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue()));
                    //Log.d("logMio","id toDo letto : " + id);
                    status = Integer.parseInt(String.valueOf(dataSnapshot.child("status").getValue()));
                    testoToDo = String.valueOf(dataSnapshot.child("testoToDo").getValue());
                    listaAppoggio.add(new ModelloToDo(id,status,testoToDo));
                    Applicazione.getInstance().getModello().putBean("ultimoID",id);


                    // versione vecchia che da problemi con rimozione in testa
                    /*
                    Log.d("logMio","ottenuto da db : " + snapshot.child(Integer.toString(c)).getValue());
                    id = Integer.parseInt(String.valueOf(snapshot.child(Integer.toString(c)).child("id").getValue()));
                    //Log.d("logMio","id toDo letto : " + id);
                    status = Integer.parseInt(String.valueOf(snapshot.child(Integer.toString(c)).child("status").getValue()));
                    testoToDo = String.valueOf(snapshot.child(Integer.toString(c)).child("testoToDo").getValue());
                    listaAppoggio.add(new ModelloToDo(id,status,testoToDo));
                    c++;
                    */
                }
                //Log.d("logMio","listaToDo in ValueEventListener : " + listaToDo.toString());
                listaToDo = listaAppoggio;
                adapterToDo.setListaToDo(listaToDo);
                Log.d("logMio","dimensione lista TODO : " + listaToDo.size());
                Applicazione.getInstance().getModello().putBean("numeroTaskAttuale",listaToDo.size());
                Applicazione.getInstance().getModello().putBean("listaToDo",listaToDo);
                Log.d("contenutoModello","MODELLO : " + Applicazione.getInstance().getModello().getMappaBean().keySet());

                programmaInvioNotifiche();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        };
        return valueEventListener;
    }


    private void inserisciListaTaskInFirebaseDB(List<ModelloToDo> listaToDo) {
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        for (ModelloToDo todo: listaToDo) {
            String idToDo = Integer.toString(todo.getId());
            firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("toDoList").child(idToDo).setValue(todo);
        }
    }

    private void inserisciSingoloTaskInFirebaseDB(ModelloToDo toDoDiProva){
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        String idToDo = Integer.toString(toDoDiProva.getId());
        firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("toDoList").child(idToDo).setValue(toDoDiProva);
    }

    private void inizializzaVista(View vista) {
        Log.d("logMio","CurrentActivity in VistaDopoLogIn.inizializzaVista : " + Applicazione.getInstance().toString());
        this.labelNomeUtente = vista.findViewById(R.id.labelNomeUtente);
        this.recyclerTask = vista.findViewById(R.id.listaTask);
        this.recyclerTask.setLayoutManager(new LinearLayoutManager(getContext()));
        this.bottoneAggiungiTask = vista.findViewById(R.id.bottoneAggiungiTask);
        // metodo spostato in onResume in quanto inizializzaVista viene chiamato in oncreate che esegue quando ancora la currentActivity non è ActivityDopoLogIn
        //this.adapterToDo = new AdapterToDo((ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity());
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        this.labelNomeUtente.setText(user.getEmail());
    }

    private void inizializzaAzioni() {
        bottoneAggiungiTask.setOnClickListener(Applicazione.getInstance().getControlloDopoLogIn().getAzioneMostraActivityAggiungiNuovoTask());
    }

    public AdapterToDo getAdapterToDo() {
        return adapterToDo;
    }

    public void programmaInvioNotifiche(){
        ActivityDopoLogIn activityDopoLogIn = (ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity();
        ComponentName componentName = new ComponentName(activityDopoLogIn.getApplicationContext(),ServizioNotifiche.class);
        JobInfo jobInfo = new JobInfo.Builder(123,componentName)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) activityDopoLogIn.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(jobInfo);
        if(resultCode==JobScheduler.RESULT_SUCCESS){
            Log.d("logNot","job schedulato con successo");
        }else{
            Log.d("logNot","job non schedulato");
        }
    }

    public void cancellaInvioNotidiche(){
        ActivityDopoLogIn activityDopoLogIn = (ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity();
        JobScheduler scheduler = (JobScheduler) activityDopoLogIn.getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
    }

}
