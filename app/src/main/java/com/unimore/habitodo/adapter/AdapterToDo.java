package com.unimore.habitodo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;
import com.unimore.habitodo.activity.ActivityDopoLogIn;
import com.unimore.habitodo.modello.ModelloToDo;
import com.unimore.habitodo.vista.VistaDopoLogIn;

import java.util.ArrayList;
import java.util.List;

public class AdapterToDo extends RecyclerView.Adapter<AdapterToDo.ViewHolder> {

    private List<ModelloToDo> listaToDo;
    private ActivityDopoLogIn activityDopoLogIn;



    public AdapterToDo(ActivityDopoLogIn activityDopoLogIn) {
        this.activityDopoLogIn = activityDopoLogIn;
    }

    public void setListaToDo(List<ModelloToDo> listaToDo) {
        this.listaToDo = listaToDo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_singolo_task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterToDo.ViewHolder holder, int position) {
        Log.d("logMio","listaTodo : " + listaToDo.size() + " : " + listaToDo.toString());
        ModelloToDo toDo = listaToDo.get(position);
        Log.d("logMio","toDo : " + listaToDo.get(position).getTestoToDo());
        holder.checkBoxListaTask.setText(toDo.getTestoToDo());
        holder.checkBoxListaTask.setChecked(toBoolean(toDo.getStatus()));
        //holder.bottoneEliminaTask.setOnClickListener(Applicazione.getInstance().getControlloAdapterToDo().getAzioneEliminaTask());
    }

    // funizone che ritorna false se status è 0 o true altrimenti
    private boolean toBoolean(int status) {
        return status!=0;
    }

    @Override
    public int getItemCount() {
        if(listaToDo==null){
            return 0;
        }
        return listaToDo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CheckBox checkBoxListaTask;
        ImageButton bottoneEliminaTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxListaTask = itemView.findViewById(R.id.checkBoxListaTask);
            bottoneEliminaTask = itemView.findViewById(R.id.bottoneEliminaTask);
            bottoneEliminaTask.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

                Log.d("logMio","posizione cliccata : " + this.getPosition());
                Applicazione.getInstance().getModello().putBean("posizione",this.getPosition());
            ActivityDopoLogIn activityDopoLogIn = (ActivityDopoLogIn) Applicazione.getInstance().getCurrentActivity();
            VistaDopoLogIn vistaDopoLogIn = activityDopoLogIn.getVistaDopoLogIn();
            AdapterToDo adapterToDo = vistaDopoLogIn.getAdapterToDo();
            adapterToDo.eliminaTask((Integer) Applicazione.getInstance().getModello().getBean("posizione"));
        }
    }

    public List<ModelloToDo> getListaToDo() {
        Log.d("logMio","getListaToDo");
        if(listaToDo==null){
            Log.d("logMio","listaTodo non inizializzata -> verrà ritornata una lista vuota");
            listaToDo = new ArrayList<>();
        }
        return listaToDo;
    }

    public void eliminaTask(int posizione){
        Log.d("logMio","elimina task : " + posizione);
        ModelloToDo toDo = listaToDo.get(posizione);
        Log.d("logMio","todo da eliminare : " + toDo.toString());
        FirebaseDatabase firebaseDatabase = (FirebaseDatabase) Applicazione.getInstance().getModello().getBean("firebaseDatabase");
        FirebaseAuth firebaseAuth = (FirebaseAuth) Applicazione.getInstance().getModello().getBean("firebaseAuth");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d("logMio","utente del todo : " + user.getEmail());
        firebaseDatabase.getReference().child("users").child(user.getUid()).child("toDoList").child(String.valueOf(toDo.getId())).removeValue();
        Toast.makeText(activityDopoLogIn, "task eliminato", Toast.LENGTH_SHORT).show();
        //notifyItemRemoved(posizione);
        //notifyDataSetChanged();
    }


}
