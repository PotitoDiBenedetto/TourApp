package com.dibenedetto.potito.tourapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Coupon", foreignKeys = @ForeignKey(entity = Location.class,
        parentColumns = "_id_", childColumns = "location"))
public class Coupon {

    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo
    public String scadenza;

    @ColumnInfo
    public String dettaglio;

    @ColumnInfo
    public String nome;

    @ColumnInfo
    public String file;

    @ColumnInfo
    public int location;

}
