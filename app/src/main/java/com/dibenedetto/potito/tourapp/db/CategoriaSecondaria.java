package com.dibenedetto.potito.tourapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categoria_secondaria", foreignKeys = @ForeignKey(entity = CategoriaPrimaria.class,
        parentColumns = "_id", childColumns = "categoria_primaria"))
public class CategoriaSecondaria {

    @PrimaryKey(autoGenerate = true)
    public int _id__;

    @ColumnInfo(name = "nomeSec")
    public String nomeSec;

    @ColumnInfo(name = "categoria_primaria")
    public int categoria_primaria;

    public CategoriaSecondaria(){}
}

