package com.dibenedetto.potito.tourapp.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Relation;

@Dao
public interface LocationDAO {

    @Insert
    void insertCategoriaPrimaria(CategoriaPrimaria categoriaPrimaria);

    @Query("SELECT * from Categoria_primaria ORDER BY nome ASC")
    List<CategoriaPrimaria> getAllCategriaPrimaria();

    @Query("SELECT _id FROM Categoria_primaria WHERE nome = :nome LIMIT 1")
    int getIdCategoriaPrimaria(String nome);

    @Insert()
    void insertCategoriaSecondaria(CategoriaSecondaria categoriaSecondaria);

    @Query("SELECT * from Categoria_secondaria ORDER BY nome ASC")
    List<CategoriaSecondaria> getAllCategriaSecondaria();

    @Query("SELECT _id FROM Categoria_secondaria WHERE nome = :nome LIMIT 1")
    int getIdCategoriaSecondaria(String nome);

    @Insert
    void insertLocation(Location location);

    @Query("SELECT * FROM Location ORDER BY nome ASC")
    List<Location> getAllLocation();

    @Query("SELECT * FROM Location ORDER BY nome ASC")
    LiveData<List<Location>> getAllLocationLiveData();

    @Query("SELECT * FROM Location WHERE categoria = :category ORDER BY nome ASC")
    List<Location> getLocationByCategory(int category);


    @Query("SELECT Location.*, Categoria_secondaria.* FROM Location INNER JOIN Categoria_secondaria " +
            "ON Location.categoria = Categoria_secondaria._id INNER JOIN Categoria_primaria " +
            "ON Categoria_secondaria.categoria_primaria = Categoria_primaria._id")
    LiveData<List<LocationWithCategory>> getLocationsWithCategory();


    public static class LocationWithCategory {

        @Embedded
        public Location location;

        @Embedded
        public CategoriaSecondaria categoriaSecondaria;

        @Embedded
        public CategoriaPrimaria categoriaPrimaria;

    }



}
