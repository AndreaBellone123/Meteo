package com.example.meteorecycler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class CittaRepository {
    private DaoAccess daoAccess;
    private LiveData<List<Citta>> listaCitta;
    public CittaRepository(Application application){
        CittaDataBase cittaDataBase = CittaDataBase.getInstance(application);
        daoAccess = cittaDataBase.daoAccess();
        listaCitta = daoAccess.fetchAllTasks();
    }

    public void insertTask(String nome,
                           String stato) {

        insertTask(nome, stato);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertTask(final Citta citta) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daoAccess.insertTask(citta);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteTask(final int id) {
        final LiveData<List<Citta>> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    daoAccess.deleteTask(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public  LiveData<List<Citta>> ricerca(String nomeCercato){
        return daoAccess.ricerca(nomeCercato);
    }

    public LiveData<List<Citta>> ricercaNonPreferite(String nomeCercato) {
        return daoAccess.ricercaNonPreferite(nomeCercato);
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteTask(final ArrayList<Citta> citta) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daoAccess.deleteTask(citta);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteFromIdCitta(final String id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daoAccess.deleteFromIdCitta(id);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void updateTask(final Citta citta) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daoAccess.updateTask(citta);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void aggiornaPreferenza(final int preferito, final String id_citta) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                daoAccess.aggiornaPreferenza(preferito, id_citta);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Citta>> getTask(int id) {
        return daoAccess.getTask(id);
    }

    public LiveData<List<Citta>> getTasks() {
        return daoAccess.fetchAllTasks();
    }

    public LiveData<List<Citta>> trovaTutteNonPreferite() {
        return daoAccess.trovaTutteNonPreferite();
    }
}