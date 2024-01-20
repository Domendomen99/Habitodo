package com.unimore.habitodo.vista;

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
import com.unimore.habitodo.adapter.AdapterToDo;
import com.unimore.habitodo.modello.ModelloToDo;

import java.util.ArrayList;
import java.util.List;

public class VistaDopoLogIn extends Fragment {

    private TextView labelNomeUtente;
    private RecyclerView recyclerTask;
    private AdapterToDo adapterToDo;
    private List<ModelloToDo> listaToDo = new ArrayList<>();

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

        // parte della prova fatta dopo
        //ModelloToDo toDoDiProva = new ModelloToDo(00,0,"provaaaaa");
        //Log.d("logMio","provaToDo creato");

        //listaToDo = ottieniListaToDoDaFirebaseDB();
        ottieniListaToDoDaFirebaseDB(listaToDo);
        Log.d("logMio","listaToDo inizializzata per bene");
        Log.d("logMio","listaToDo in OnResume : " + listaToDo.toString());

        // parte della prova fatta dopo
        //listaToDo.add(toDoDiProva);
        //Log.d("logMio","listaToDo.add(provaToDo); andato");

        adapterToDo.setListaToDo(listaToDo);
        Log.d("logMio","adapterToDo.setListaToDo(listaToDo); andato");

        // prova inserimento dati in database legati a utente corrente
        /*
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        String idToDo = Integer.toString(toDoDiProva.getId());
        firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("toDoList").child(idToDo).setValue(toDoDiProva);
        */
        //inserisciSingoloTaskInFirebaseDB(toDoDiProva);
        //inserisciListaTaskInFirebaseDB(adapterToDo.getListaToDo());

    }

    private void ottieniListaToDoDaFirebaseDB(List<ModelloToDo> listaToDo) {
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d("logMio","Path utente corrente : " + firebaseDatabase.getReference().child("users").child(user.getUid()));
        Log.d("logMio","Path listaToDo : " + firebaseDatabase.getReference().child("users").child(user.getUid()).child("toDoList"));
        Query queryOttieniListaToDoUtente = firebaseDatabase.getReference().child("users").child(user.getUid()).child("toDoList");
        Log.d("logMio","costruzione query andata a buon fine");
        Log.d("logMio","ESECUZIONE QUERY richiesta dati");
        queryOttieniListaToDoUtente.addListenerForSingleValueEvent(ValueEventListenerOttieniTask(listaToDo));
        Log.d("logMio","QUERY andata a buon fine");
    }

    private ValueEventListener ValueEventListenerOttieniTask(List<ModelloToDo> listaToDo) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c = 0;
                int id;
                int status;
                String testoToDo;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("logMio","ottenuto da db : " + snapshot.child(Integer.toString(c)).getValue());
                    id = Integer.parseInt(String.valueOf(snapshot.child(Integer.toString(c)).child("id").getValue()));
                    //Log.d("logMio","id toDo letto : " + id);
                    status = Integer.parseInt(String.valueOf(snapshot.child(Integer.toString(c)).child("status").getValue()));
                    testoToDo = String.valueOf(snapshot.child(Integer.toString(c)).child("testoToDo").getValue());
                    listaToDo.add(new ModelloToDo(id,status,testoToDo));
                    c++;
                }
                Log.d("logMio","listaToDo in ValueEventListener : " + listaToDo.toString());
                adapterToDo.setListaToDo(listaToDo);
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
        // metodo spostato in onResume in quanto inizializzaVista viene chiamato in oncreate che esegue quando ancora la currentActivity non è ActivityDopoLogIn
        //this.adapterToDo = new AdapterToDo((ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity());
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        this.labelNomeUtente.setText(user.getEmail());
    }

    private void inizializzaAzioni() {

    }

}
