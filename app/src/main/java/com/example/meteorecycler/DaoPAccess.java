package com.example.meteorecycler;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface DaoPAccess {

    @Insert
    Long insertTask(CittaPreferite cittaP);


    @Query("SELECT * FROM CittaPreferite ")
    LiveData<List<CittaPreferite>> fetchAllTasks();


    @Query("SELECT * FROM CittaPreferite WHERE id =:taskId")
    LiveData<List<CittaPreferite>> getTask(int taskId);

    @Query("SELECT * FROM CittaPreferite WHERE id_citta =:taskId")
    LiveData<List<CittaPreferite>> getStringTask(String taskId);

    @Query("DELETE FROM CittaPreferite WHERE id_citta =:taskId")
    void deleteFromIdCitta(String taskId);

    @Update
    void updateTask(CittaPreferite cittaP);

    @Delete
    void deleteTask(List<CittaPreferite> cittaP);
}

