package com.unimore.habitodo.modello;

public class ModelloToDo {

    private int id;
    private int status;
    private String testoToDo;

    public ModelloToDo(int id, int status, String testoToDo) {
        this.id = id;
        this.status = status;
        this.testoToDo = testoToDo;
    }

    public ModelloToDo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTestoToDo() {
        return testoToDo;
    }

    public void setTestoToDo(String testoToDo) {
        this.testoToDo = testoToDo;
    }
}
