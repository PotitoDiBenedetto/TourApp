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

    @Query("SELECT * from Categoria_primaria ORDER BY nome_categoria_primaria ASC")
    List<CategoriaPrimaria> getAllCategriaPrimaria();

    @Query("SELECT _id_categoria_primaria FROM Categoria_primaria WHERE nome_categoria_primaria = :nome LIMIT 1")
    int getIdCategoriaPrimaria(String nome);

    @Insert()
    void insertCategoriaSecondaria(CategoriaSecondaria categoriaSecondaria);

    @Query("SELECT * from Categoria_secondaria ORDER BY nome_categoria_secondaria ASC")
    List<CategoriaSecondaria> getAllCategriaSecondaria();

    @Query("SELECT _id_categoria_secondaria FROM Categoria_secondaria WHERE nome_categoria_secondaria = :nome LIMIT 1")
    int getIdCategoriaSecondaria(String nome);

    @Insert
    void insertLocation(Location location);

    @Query("SELECT * FROM Location ORDER BY nome_location ASC")
    List<Location> getAllLocation();

    @Query("SELECT * FROM Location ORDER BY nome_location ASC")
    LiveData<List<Location>> getAllLocationLiveData();

    @Query("SELECT * FROM Location WHERE categoria = :category ORDER BY nome_location ASC")
    LiveData<List<Location>> getLocationByCategory(int category);


    @Query("SELECT Location.*, Categoria_secondaria.*, Categoria_primaria.* FROM Location INNER JOIN Categoria_secondaria " +
            "ON Location.categoria = Categoria_secondaria._id_categoria_secondaria INNER JOIN Categoria_primaria " +
            "ON Categoria_secondaria.categoria_primaria = Categoria_primaria._id_categoria_primaria")
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
