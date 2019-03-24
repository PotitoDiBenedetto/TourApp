package com.dibenedetto.potito.tourapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Foto_ricordo", foreignKeys = @ForeignKey(entity = Diari.class,
        parentColumns = "_id", childColumns = "diario"), indices= @Index( name = "diario_index", unique=false, value="diario"))
public class FotoRicordo {

    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo
    public String data;

    @ColumnInfo
    public String commento;

    @ColumnInfo
    public String foto_path;

    @ColumnInfo
    public int diario;

    @ColumnInfo
    public double latitude;

    @ColumnInfo
    public double longitude;

    public FotoRicordo() {}
}
