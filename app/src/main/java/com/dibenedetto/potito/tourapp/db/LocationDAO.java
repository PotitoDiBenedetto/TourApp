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

    @Query("SELECT * from Categoria_primaria ORDER BY nomePri ASC")
    List<CategoriaPrimaria> getAllCategriaPrimaria();

    @Query("SELECT _id FROM Categoria_primaria WHERE nomePri = :nome LIMIT 1")
    int getIdCategoriaPrimaria(String nome);

    @Insert()
    void insertCategoriaSecondaria(CategoriaSecondaria categoriaSecondaria);

    @Query("SELECT * from Categoria_secondaria ORDER BY nomeSec ASC")
    List<CategoriaSecondaria> getAllCategriaSecondaria();

    @Query("SELECT _id__ FROM Categoria_secondaria WHERE nomeSec = :nome LIMIT 1")
    int getIdCategoriaSecondaria(String nome);

    @Insert
    void insertLocation(Location location);

    @Query("SELECT * FROM Location ORDER BY nomeLocation ASC")
    List<Location> getAllLocation();

    @Query("SELECT * FROM Location ORDER BY nomeLocation ASC")
    LiveData<List<Location>> getAllLocationLiveData();

    @Query("SELECT * FROM Location WHERE categoria = :category ORDER BY nomeLocation ASC")
    LiveData<List<Location>> getLocationByCategory(int category);


    @Query("SELECT Location.*, Categoria_secondaria.*, Categoria_primaria.* FROM Location INNER JOIN Categoria_secondaria " +
            "ON Location.categoria = Categoria_secondaria._id__ INNER JOIN Categoria_primaria " +
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
