package com.dibenedetto.potito.tourapp.model;

import java.nio.file.Path;
import java.util.Date;

public class PhotoSouvenir {

    public Date data;
    public String commento;
    public Path photoPath;
    public Location location;
    //private Diario diario;

    public PhotoSouvenir(Date data, String commento, Path photoPath, Location location) {
        this.data = data;
        this.commento = commento;
        this.location = location;
        this.photoPath = photoPath;
    }

    public PhotoSouvenir() {

    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public Path getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(Path photoPath) {
        this.photoPath = photoPath;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
