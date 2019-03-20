package com.dibenedetto.potito.tourapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Diari")
public class Diari {

    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo
    public String data;

    @ColumnInfo
    public String nome;

    public Diari() {}
}
