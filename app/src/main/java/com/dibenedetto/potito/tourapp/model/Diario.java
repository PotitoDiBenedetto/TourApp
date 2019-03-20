package com.dibenedetto.potito.tourapp.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Diario {
    public Date date;
    public String nome;
    public List<PhotoSouvenir> photos;

    //default constructor
    public Diario() {

    }

    //full constructor
    public Diario(String nome) {
        this.nome = nome;
        this.date = Calendar.getInstance().getTime();
        this.photos = new ArrayList<>();

    }

    //name setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    //date setter
    public void setData() {
        if(this.date != null) this.date = Calendar.getInstance().getTime();
    }

    //name getter
    public String getNome(){
        return this.nome;
    }

    //date getter
    public Date getDate(){
        return this.date;
    }

    //date as string
    public String getDateAsString(){
        DateFormat df = DateFormat.getInstance(); // .getDateInstance(); // .getTimeInstance();
        return df.format(this.date);
    }

    public List<PhotoSouvenir> getPhotos() {
        return this.photos;
    }

    public void setPhotos(List<PhotoSouvenir> photos) {
        this.photos = photos;
    }

    public boolean addPhoto(PhotoSouvenir photo) {
        return this.photos.add(photo);
    }

    public PhotoSouvenir getPhoto(int index) {
        return photos.get(index);
    }


    //TODO: getter and setters, gestione delle foto

}
