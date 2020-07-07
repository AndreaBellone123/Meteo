package com.example.meteorecycler;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<CittaPreferite> citta;
    private Application application;

    public MyAdapter(Application app, ArrayList<CittaPreferite> cittaList) {
        this.application = app;
        citta = cittaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(application);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.text1.setText(citta.get(position).getNome());

        String url = "http://api.openweathermap.org/data/2.5/weather?id=" + citta.get(position).getId_citta() + "&APPID=1c5829fd5f599d86f99cb3b9f250259a";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String condizione = "";
                        try {
                            condizione += response.getJSONArray("weather").getJSONObject(0).get("description").toString(); /* Prendiamo la descrizione riguardante il meteo
                                                                                                                                        , dal JSON che restituisce
                                                                                                                                        la chiamata API ad OpenWeather
                                                                                                                                        (Pos 0 dell'array di JSON "weather" che contiene ad esempio"broken clouds") */

                            if(condizione.contains("thunderstorm")) {
                                holder.imageView.setImageResource(R.drawable.ico_temporale_1);

                            }

                            if(condizione.contains("drizzle")){
                                holder.imageView.setImageResource(R.drawable.ico_drizzle);

                            }

                            if(condizione.contains("rain")){
                                holder.imageView.setImageResource(R.drawable.ico_drizzle);

                            }

                            if(condizione.contains("freezing rain")){
                                holder.imageView.setImageResource(R.drawable.ico_neve_1);

                            }

                            if(condizione.contains("snow")){
                                holder.imageView.setImageResource(R.drawable.ico_neve_1);

                            }

                            if(condizione.contains("few clouds")){
                                holder.imageView.setImageResource(R.drawable.fewclouds);

                            }

                            if(condizione.contains("scattered clouds")){
                                holder.imageView.setImageResource(R.drawable.ico_nuvoloso_2);

                            }

                            if(condizione.contains("broken clouds")){
                                holder.imageView.setImageResource(R.drawable.brokenclouds);

                            }

                            if(condizione.contains("overcast clouds")){
                                holder.imageView.setImageResource(R.drawable.brokenclouds);

                            }

                            if(condizione.contains("clear sky")){
                                holder.imageView.setImageResource(R.drawable.icona_soleggiato1);

                            }
                            if(condizione.contains("mist")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }
                            if(condizione.contains("fog")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }

                            if(condizione.contains("haze")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }
                            if(condizione.contains("sand/ dust whirls")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }
                            if(condizione.contains("sand")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }

                            if(condizione.contains("tornado")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }
                            if(condizione.contains("volcanic ash")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }
                            if(condizione.contains("squall")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }
                            if(condizione.contains("sleet")){
                                holder.imageView.setImageResource(R.drawable.ico_nebbia_1);

                            }
                            condizione = "Condizione: " + condizione;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        double temperatura = 0;
                        try {
                            temperatura = response.getJSONObject("main").getDouble("temp");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DecimalFormat formato = new DecimalFormat("0.0");
                        temperatura = temperatura - 273.15;

                        holder.text2.setText(formato.format(temperatura) + "°");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(application).addToRequestQueue(jsonObjectRequest);


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(application,DetailActivity.class);
                intent.putExtra("id_citta",citta.get(position).getId_citta());
                intent.putExtra("nome",citta.get(position).getNome());
                intent.putExtra("stato",citta.get(position).getStato());
                application.startActivity(intent);
            }
        });

        holder.buttonCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CittaPreferiteRepository cp = new CittaPreferiteRepository(application);
                cp.deleteFromIdCitta(citta.get(position).getId_citta());
                Citta cittaDaAggiornare = new Citta(citta.get(position).getNome(), citta.get(position).getId_citta(), citta.get(position).getStato());
                CittaRepository cr = new CittaRepository(application);
                cr.aggiornaPreferenza(0, cittaDaAggiornare.getId_citta());
                citta.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
            return citta.size();
    }

    public void setItems(ArrayList<CittaPreferite> listaCitta) {
        this.citta = listaCitta;
    }

    public void setItemsL(List<CittaPreferite> listaCittaP){
        this.citta = (ArrayList<CittaPreferite>) listaCittaP;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ConstraintLayout mainLayout;
        ImageView imageView;
        Button buttonCancella;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.textView);
            text2 = itemView.findViewById(R.id.textView2);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            imageView = itemView.findViewById(R.id.imageView);
            buttonCancella = itemView.findViewById(R.id.buttonRimuovi);
            AssetManager assetManager = application.getAssets();
            Typeface type = Typeface.createFromAsset(assetManager,"fonts/HelveticaNeue Light.ttf");
            text1.setTypeface(type);
            text2.setTypeface(type);

        }
    }
}
