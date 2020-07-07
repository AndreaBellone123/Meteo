package com.example.meteorecycler;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Citta {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private String id_citta;
    private String stato;
    private int preferito;

    public Citta(String nome, String id_citta, String stato) {
        this.nome = nome;
        this.id_citta = id_citta;
        this.stato = stato;
        this.preferito = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_citta() {
        return id_citta;
    }

    public void setId_citta(String id_citta) {
        this.id_citta = id_citta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getPreferito() {
        return preferito;
    }

    public void setPreferito(int preferito) {
        this.preferito = preferito;
    }
}
