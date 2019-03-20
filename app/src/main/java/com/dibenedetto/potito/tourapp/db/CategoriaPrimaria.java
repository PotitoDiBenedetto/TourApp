package com.dibenedetto.potito.tourapp.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categoria_primaria")
public class CategoriaPrimaria {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "nome")
    private String nome;

    public CategoriaPrimaria(){}

    public CategoriaPrimaria(int id, String nome) {
        this._id = id;
        this.nome = nome;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

