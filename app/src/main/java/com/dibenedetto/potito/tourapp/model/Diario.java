package com.dibenedetto.potito.tourapp.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Diario {
    private int date;
    private String nome;
    //private List<PhotoSouvenir> photos;

    //default constructor
    public Diario() {

    }

    //full constructor
    public Diario(String nome) {
        this.nome = nome;
        this.date = new GregorianCalendar().get(Calendar.YEAR);


    }

    //name setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    //date setter
    public void setData() {
        this.date = new GregorianCalendar().get(Calendar.YEAR);
    }

    //name getter
    public String getNome(){
        return this.nome;
    }

    //date getter
    public int getDate(){
        return this.date;
    }

    //TODO: getter and setters, gestione delle foto

}
