package com.example.meteorecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    TextView citta, temp, cond, press, umid, vento;
    Intent intent;
    ConstraintLayout layout;
    CittaPreferite cittaDaIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Qui prendiamo dall' onClick dell'activity precedente(MainActivity) la città di cui visualizzare le informazioni sul meteo quali temperatura,umidità ecc
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent(); // Invochiamo il metodo getIntent che ci fornisce il valore("id") della città selezionata con la quale avviare la DetailActivity

        String nome = intent.getStringExtra("nome");
        String id_citta = intent.getStringExtra("id_citta");
        String stato = intent.getStringExtra("stato");

        cittaDaIntent = new CittaPreferite(nome, id_citta, stato);

        citta = findViewById(R.id.textCitta);
        citta.setText(cittaDaIntent.getNome());
        cond = findViewById(R.id.textCond);
        temp = findViewById(R.id.textTemp);
        press = findViewById(R.id.textPress);
        umid = findViewById(R.id.textUmid);
        vento = findViewById(R.id.textVento);

        try {
            richiestaMeteo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void richiestaMeteo() throws JSONException {

        String url = "http://api.openweathermap.org/data/2.5/weather?id=" + cittaDaIntent.getId_citta() + "&APPID=1c5829fd5f599d86f99cb3b9f250259a";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) { // Una volta effettuata l'API call ad OpenWeather per visualizzare temperatura,umidità,pressione ecc.
                        String condizione = "";
                        layout = (ConstraintLayout) findViewById(R.id.detailLayout);
                        try {
                            condizione += response.getJSONArray("weather").getJSONObject(0).get("description").toString(); // Dal JSON fornito dalla chiamata prendiamo dall'array "weather" l'oggetto con indice 0,la descrizione
                            switch(condizione) {
                                case "broken clouds": // Facciamo uno switch sulla condizione(description) per tradurre in italiano le possibile condizioni metereologiche
                                    condizione = "Parzialmente nuvoloso";
                                    break;
                                case "scattered clouds":
                                    condizione = "Nuvole sparse";
                                    break;
                                case "light rain":
                                    condizione = "Pioggia leggera";
                                    break;
                                case "moderate rain":
                                    condizione = "Pioggia moderata";
                                    break;
                                case "mist":
                                    condizione = "Nebbia";
                                    break;
                                case "few clouds":
                                    condizione = "Scarsamente nuvoloso";
                                    break;
                                case "overcast clouds":
                                    condizione = "Nuvoloso";
                                    break;
                                case "sunny":
                                    condizione = "Soleggiato";
                                    break;
                                case "heavy rain":
                                    condizione = "Pioggia Intensa";
                                case "heavy intensity rain":
                                    condizione = "Pioggia Intensa";
                                    break;
                                case "clear sky":
                                    condizione = "Sereno";
                                    break;

                                case "fog":
                                    condizione = "Nebbia";
                                    break;
                                case "haze":
                                    condizione = "Foschia";
                                    break;
                                case "sand/ dust whirls":
                                    condizione = "Tempesta di polvere";
                                    break;
                                case "tornado":
                                    condizione = "Tornado";
                                    break;
                                case "volcanic ash":
                                    condizione = "Cenere vulcanica";
                                    break;
                                case "squall":
                                    condizione = "Burrasca";
                                    break;
                                case "sand":
                                    condizione = "Tempesta di sabbia";
                                    break;
                                case "sleet":
                                    condizione = "Nevischio";
                                    break;
                                default:
                                    break;
                            }
                            if(condizione.contains("thunderstorm")){
                                condizione = "Temporale";

                            }

                            if(condizione.contains("drizzle")){
                                condizione = "Pioggia leggera";

                            }

                            if(condizione.contains("snow")){
                                condizione = "Neve";

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
                        String temperatura_citta = "Temperatura: ";
                        temperatura_citta += formato.format(temperatura) + " °C";

                        String umidita = "Percentuale umidita': ";
                        try {
                            umidita += response.getJSONObject("main").get("humidity").toString() + "%";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String pressione = "Pressione: ";
                        try {
                            pressione += response.getJSONObject("main").get("pressure").toString() + " hPa";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String ventoS = "Velocita' del vento: ";
                        try {
                            ventoS += response.getJSONObject("wind").get("speed").toString() + " m/s";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        temp.setText(temperatura_citta);
                        cond.setText(condizione);
                        umid.setText(umidita);
                        press.setText(pressione);
                        vento.setText(ventoS);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}