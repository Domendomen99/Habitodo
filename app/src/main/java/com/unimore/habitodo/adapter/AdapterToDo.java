package com.unimore.habitodo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimore.habitodo.Applicazione;
import com.unimore.habitodo.R;
import com.unimore.habitodo.activity.ActivityDopoLogIn;
import com.unimore.habitodo.modello.ModelloToDo;

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
    }

    // funizone che ritorna false se status è 0 o true altrimenti
    private boolean toBoolean(int status) {
        return status!=0;
    }

    @Override
    public int getItemCount() {
        return listaToDo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxListaTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
