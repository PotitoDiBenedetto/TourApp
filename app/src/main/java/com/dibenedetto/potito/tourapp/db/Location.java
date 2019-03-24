package com.dibenedetto.potito.tourapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Location", foreignKeys = @ForeignKey(entity = CategoriaSecondaria.class,
        parentColumns = "_id__", childColumns = "categoria"))
public class Location {

    @PrimaryKey(autoGenerate = true)
    public int _id_;

    @ColumnInfo
    public double latitude;

    @ColumnInfo
    public double longitude;

    @ColumnInfo
    public String nomeLocation;

    @ColumnInfo
    public String indirizzo;

    @ColumnInfo
    public String orari;

    @ColumnInfo
    public int costo;

    @ColumnInfo
    public int categoria;

    Location(){}
}
