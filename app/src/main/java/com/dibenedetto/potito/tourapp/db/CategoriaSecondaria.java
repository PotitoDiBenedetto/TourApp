package com.dibenedetto.potito.tourapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categoria_secondaria", foreignKeys = @ForeignKey(entity = CategoriaPrimaria.class,
        parentColumns = "_id_categoria_primaria", childColumns = "categoria_primaria"), indices = @Index(name = "catPriSec", value = "categoria_primaria"))
public class CategoriaSecondaria {

    @PrimaryKey(autoGenerate = true)
    public int _id_categoria_secondaria;

    @ColumnInfo(name = "nome_categoria_secondaria")
    public String nome_categoria_secondaria;

    @ColumnInfo(name = "categoria_primaria")
    public int categoria_primaria;

    public CategoriaSecondaria(){}
}

