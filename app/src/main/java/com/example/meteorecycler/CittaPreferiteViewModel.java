package com.example.meteorecycler;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CittaPreferiteViewModel extends ViewModel {
    private Application application;
    private CittaPreferiteRepository cp;
    private LiveData<List<CittaPreferite>> listaCitta;

    public CittaPreferiteViewModel(){
        cp = new CittaPreferiteRepository(application);
        listaCitta = cp.getTasks();
    }

    public void insertTask(CittaPreferite citta){
        cp.insertTask(citta);
    }

    public void insertTask(String nome,String stato){
        cp.insertTask(nome,stato);
    }

    public void deleteFromIdCitta(String id){
        cp.deleteFromIdCitta(id);

    }

    public void deleteTask(int id){
        cp.deleteTask(id);
    }

    public void getTask(int id){
        cp.getTask(id);
    }

    public LiveData<List<CittaPreferite>> getTasks(){
        return listaCitta;
    }

    public void deleteTask(ArrayList<CittaPreferite> listaD){
        cp.deleteTask(listaD);
    }
}
