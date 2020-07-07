package com.example.meteorecycler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RicercaActivity extends AppCompatActivity {

    ArrayList<Citta> citta;
    String arrayId[];
    RecyclerView recyclerView;
    EditText editText;
    AdapterRicerca adapterRicerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        recyclerView = findViewById(R.id.recyclerView2);
        troveTutte();
        editText = findViewById(R.id.editText2);

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 0)
                    troveTutte();
                else
                    doMySearch("%"+s.toString()+"%");
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void trovaNonPreferite() {
        CittaRepository cr = new CittaRepository(getApplication());

        cr.trovaTutteNonPreferite().observe(this, new Observer<List<Citta>>() {
            @Override
            public void onChanged(List<Citta> listaCitta) {
                setCitta((ArrayList<Citta>) listaCitta);
                adapterRicerca = new AdapterRicerca(getApplication(), citta);
                recyclerView.setAdapter(adapterRicerca);
                recyclerView.invalidate();
            }
        });
    }

    public void troveTutte() {
        final CittaRepository cr = new CittaRepository(getApplication());

        cr.getTasks().observe(this, new Observer<List<Citta>>() {
            @Override
            public void onChanged(@Nullable List<Citta> listaCitta) {
                if(listaCitta.size() > 0) {
                    trovaNonPreferite();
                }
                else {
                    JSONArray jsonArray = LeggiJson.loadJSONFromAsset(getApplication(), "cityRidotta.json");
                    String nome;
                    String stato;
                    String id_citta;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            nome = jsonArray.getJSONObject(i).get("name").toString();
                            id_citta = jsonArray.getJSONObject(i).get("id").toString();
                            stato = jsonArray.getJSONObject(i).get("country").toString();
                            listaCitta.add(new Citta(nome, id_citta, stato));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("myTag", String.valueOf(listaCitta.get(0)));
                        cr.insertTask(listaCitta.get(i));
                    }
                    setCitta((ArrayList<Citta>) listaCitta);
                    adapterRicerca = new AdapterRicerca(getApplication(), citta);
                    recyclerView.setAdapter(adapterRicerca);
                    recyclerView.invalidate();
                }
            }
        });
    }

    public void doMySearch(String query) {
        CittaRepository cr = new CittaRepository(getApplication());

        cr.ricercaNonPreferite(query).observe(this, new Observer<List<Citta>>() {
            @Override
            public void onChanged(@Nullable List<Citta> listaCitta) {
                if(listaCitta.size() > 0) {
                    setCitta((ArrayList<Citta>) listaCitta);
                    Log.i("myTag", String.valueOf(listaCitta.get(0)));
                    adapterRicerca = new AdapterRicerca(getApplication(), citta);
                    recyclerView.setAdapter(adapterRicerca);
                    recyclerView.invalidate();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public ArrayList<Citta> getCitta() {
        return citta;
    }

    public void setCitta(ArrayList<Citta> citta) {
        this.citta = citta;
    }
}
