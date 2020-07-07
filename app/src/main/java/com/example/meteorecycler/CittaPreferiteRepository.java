package com.example.meteorecycler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class CittaPreferiteRepository {

    private DaoPAccess daopAccess;
    private LiveData<List<CittaPreferite>> listaCittaPreferite;
    public CittaPreferiteRepository(Application application) {
        CittaDataBase cittaDataBase = CittaDataBase.getInstance(application);
        daopAccess = cittaDataBase.daopAccess();
        listaCittaPreferite = daopAccess.fetchAllTasks();    }

    public void insertTask(String nome,
                           String stato) {

        insertTask(nome, stato);
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteFromIdCitta(final String id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daopAccess.deleteFromIdCitta(id);
                return null;
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void insertTask(final CittaPreferite cittaP) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daopAccess.insertTask(cittaP);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteTask(final int id) {
        final LiveData<List<CittaPreferite>> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    daopAccess.deleteTask(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteTask(final List<CittaPreferite> cittaP) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daopAccess.deleteTask(cittaP);
                return null;
            }
        }.execute();
    }

    public LiveData<List<CittaPreferite>> getTask(int id) {
        return daopAccess.getTask(id);
    }

    public LiveData<List<CittaPreferite>> getTasks() {
        return daopAccess.fetchAllTasks();
    }

    public LiveData<List<CittaPreferite>> getStringTask(String taskId) {
        return daopAccess.getStringTask(taskId);
    }
}