package com.example.meteorecycler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<CittaPreferite> citta = new ArrayList<CittaPreferite>();
    private Button buttonAggiungi;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       /* CittaViewModel cv = new ViewModelProvider(this).get(CittaViewModel.class); // Classi ViewModel non funzionanti,null object reference
        cv.getTasks().observe(this, new Observer<List<CittaPreferite>>() {
            @Override
            public void onChanged(List<CittaPreferite> cittas) {
                myAdapter.setItems(cittas);
            }
        }); */

        setContentView(R.layout.activity_main);
        buttonAggiungi = findViewById(R.id.buttonAggiungi);
        buttonAggiungi.setOnClickListener(new View.OnClickListener() { // Settiamo l'onclick del bottone
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), RicercaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        Intent intentE = getIntent();
        if (intentE.getStringExtra("id_citta") != null) {
            String id_citta = intentE.getStringExtra("id_citta");
            String nome = intentE.getStringExtra("nome");
            String stato = intentE.getStringExtra("stato");
            aggiungiCitta(id_citta, nome, stato);
            intentE.removeExtra("id_citta");
            intentE.removeExtra("nome");
            intentE.removeExtra("stato");
        }
        updateTaskList();

    }

    public void aggiungiCitta (String id_Citta, String nome, String stato) {
        CittaPreferite nuovaCitta = new CittaPreferite(nome, id_Citta, stato);
        citta.add(nuovaCitta);
        recyclerView.invalidate();
        CittaPreferiteRepository cp = new CittaPreferiteRepository(getApplication());
        cp.insertTask(nuovaCitta);
    }

    public void updateTaskList() {
        CittaPreferiteRepository cp = new CittaPreferiteRepository(getApplication());
        cp.getTasks().observe(this, new Observer<List<CittaPreferite>>() {
            @Override
            public void onChanged(@Nullable List<CittaPreferite> listaCitta) {
                if(listaCitta.size() > 0) {
                    setCitta((ArrayList<CittaPreferite>) listaCitta );
                    Log.i("myTag", String.valueOf(listaCitta.get(0)));
                    recyclerView.setAdapter(new MyAdapter(getApplication(),citta));
                    recyclerView.invalidate();
                }
            }
        });
    }

    public ArrayList<CittaPreferite> getCitta() {
        return citta;
    }

    public void setCitta(ArrayList<CittaPreferite> citta) {
        this.citta =  citta;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onStart() {
        updateTaskList();
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        updateTaskList();
        super.onNewIntent(intent);
    }

    @Override
    public void onStop() {

        updateTaskList();
        super.onStop();
    }
    @Override
    public void onResume(){

        updateTaskList();
        super.onResume();
    }

    @Override
    public void onPause(){
        updateTaskList();
        super.onPause();
    }
}
