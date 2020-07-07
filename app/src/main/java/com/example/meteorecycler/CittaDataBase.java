package com.example.meteorecycler;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Citta.class,CittaPreferite.class}, version = 1)
    public abstract class CittaDataBase extends RoomDatabase {
        private static CittaDataBase instance;
        public abstract DaoAccess daoAccess();
        public abstract DaoPAccess daopAccess();
        public static synchronized CittaDataBase getInstance(Context context){
            if(instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),CittaDataBase.class
                ,"citta_db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }
}

