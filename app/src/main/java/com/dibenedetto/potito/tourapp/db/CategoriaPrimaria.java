package com.dibenedetto.potito.tourapp.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categoria_primaria")
public class CategoriaPrimaria {

    @PrimaryKey(autoGenerate = true)
    public int _id_categoria_primaria;

    @ColumnInfo(name = "nome_categoria_primaria")
    public String nome_categoria_primaria;

    public CategoriaPrimaria(){}


}

