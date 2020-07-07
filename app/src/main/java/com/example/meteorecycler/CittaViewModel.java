package com.example.meteorecycler;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CittaViewModel extends ViewModel {
    private Application application;
    private CittaRepository cr;
    private LiveData<List<Citta>> listaCitta;

    public CittaViewModel(@NonNull Application application){
        this.application = application;
        cr = new CittaRepository(application);
        listaCitta = cr.getTasks();
    }

    public void insertTask(Citta citta){
        cr.insertTask(citta);


    }

    public void updateTask(Citta citta){
        cr.updateTask(citta);

    }

    public void insertTask(String nome,String stato){
        cr.insertTask(nome,stato);
    }

    public void deleteFromIdCitta(String id){
        cr.deleteFromIdCitta(id);

    }

    public void deleteTask(int id){
        cr.deleteTask(id);
    }

    public void getTask(int id){
        cr.getTask(id);
    }

    public LiveData<List<Citta>> getTasks(){
        return listaCitta;
    }

    public void aggiornaPreferenza(int preferito,String id_citta){
        cr.aggiornaPreferenza(preferito,id_citta);

    }

    public void ricerca(String nomeCercato){
        cr.ricercaNonPreferite(nomeCercato);
    }

    public void trovaTutteNonPreferite(){
        cr.trovaTutteNonPreferite();

    }

    public void deleteTask(ArrayList<Citta> listaD){
        cr.deleteTask(listaD);
    }

    public void ricercaNonPreferite(String nomeCercato){
        cr.ricercaNonPreferite(nomeCercato);
    }
}
