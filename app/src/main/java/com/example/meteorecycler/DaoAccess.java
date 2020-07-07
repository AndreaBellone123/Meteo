package com.example.meteorecycler;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
    public interface DaoAccess {

        @Insert
        Long insertTask(Citta citta);


        @Query("SELECT * FROM Citta ")
        LiveData<List<Citta>> fetchAllTasks();

        @Query("SELECT * FROM Citta WHERE preferito = 0")
        LiveData<List<Citta>> trovaTutteNonPreferite();

        @Query("SELECT * FROM Citta WHERE id =:taskId")
        LiveData<List<Citta>> getTask(int taskId);

        @Query("SELECT * FROM Citta WHERE nome LIKE :nomeCercato LIMIT 5")
        LiveData<List<Citta>> ricerca(String nomeCercato);

        @Query("SELECT * FROM Citta WHERE nome LIKE :nomeCercato AND preferito = 0 LIMIT 5")
        LiveData<List<Citta>> ricercaNonPreferite(String nomeCercato);

        @Query("DELETE FROM Citta WHERE id_citta =:taskId")
        void deleteFromIdCitta(String taskId);

        @Query("UPDATE Citta SET preferito = :preferito WHERE id_citta = :id_citta")
        void aggiornaPreferenza(int preferito, String id_citta);

        @Update
        void updateTask(Citta citta);

        @Delete
        void deleteTask(List<Citta> citta);
    }

