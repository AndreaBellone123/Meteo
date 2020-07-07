package com.example.meteorecycler;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRicerca extends RecyclerView.Adapter<AdapterRicerca.MyViewHolder> {

    private ArrayList<Citta> listaCitta;
    private Application application;

    public AdapterRicerca(Application app,ArrayList<Citta> listaCitta) {
       this.application = app;
       this.listaCitta = listaCitta;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(application);
        View view = inflater.inflate(R.layout.my_row_ricerca, parent, false);
        return new AdapterRicerca.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Citta citta = listaCitta.get(position);

        holder.text1.setText(citta.getNome());
        holder.text2.setText(citta.getStato());

        holder.ricercaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                citta.setPreferito(1);

                CittaRepository cr = new CittaRepository(application);
                cr.aggiornaPreferenza(1, citta.getId_citta());

                Intent intent = new Intent(application, MainActivity.class);
                intent.putExtra("id_citta", citta.getId_citta());
                intent.putExtra("nome", citta.getNome());
                intent.putExtra("stato", citta.getStato());
                application.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCitta.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        ConstraintLayout ricercaLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.textViewCitta);
            text2 = itemView.findViewById(R.id.textViewStato);
            ricercaLayout = itemView.findViewById(R.id.ricercaLayout);
            AssetManager assetManager = application.getAssets();
            Typeface type = Typeface.createFromAsset(assetManager,"fonts/HelveticaNeue Light.ttf");
            text1.setTypeface(type);
            text2.setTypeface(type);
        }
    }
}
