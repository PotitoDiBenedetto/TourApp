package com.dibenedetto.potito.tourapp.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DiaryDAO {

    @Insert
    void insertDiary(Diari diario);

    @Query("SELECT * FROM Diari ORDER BY _id ASC")
    List<Diari> getDiaries();

    @Insert
    void insertPhoto(FotoRicordo foto);

    @Query("SELECT * FROM Foto_ricordo WHERE diario = :diario ORDER BY _id ASC")
    List<FotoRicordo> getPhotoesByDiary(int diario);

}
