package com.dibenedetto.potito.tourapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Location", foreignKeys = @ForeignKey(entity = CategoriaSecondaria.class,
        parentColumns = "_id_categoria_secondaria", childColumns = "categoria"), indices = @Index(name="locationCategory", value="categoria"))
public class Location {

    @PrimaryKey(autoGenerate = true)
    public int _id_location;

    @ColumnInfo
    public double latitude;

    @ColumnInfo
    public double longitude;

    @ColumnInfo
    public String nome_location;

    @ColumnInfo
    public String indirizzo;

    @ColumnInfo
    public String orari;

    @ColumnInfo
    public int costo;

    @ColumnInfo
    public int categoria;

    @ColumnInfo
    public int telefono;

    @ColumnInfo
    public String dettaglio;

    @ColumnInfo
    public String web_url;

    @ColumnInfo
    public String img_url;

    @ColumnInfo
    public String email;

    Location(){}
}
