package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        findViewById(R.id.btnPLN).setOnClickListener(v -> {
            CurrencyConverter currencyConverter = CurrencyConverter.getInstance();
            currencyConverter.setCurrencyName("PLN");
            currencyConverter.setMultiplier(1.0f);
        });
        findViewById(R.id.btnEUR).setOnClickListener(v -> {
            this.APIFunction("EUR");
        });
        findViewById(R.id.btnUSD).setOnClickListener(v -> {
            this.APIFunction("USD");
        });
        findViewById(R.id.btnGBP).setOnClickListener(v -> {
            this.APIFunction("GBP");
        });
    }

    private void APIFunction(String currencyName) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.nbp.pl/api/exchangerates/rates/A/" + currencyName + "/last/?format=json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CurrencyConverter currencyConverter = CurrencyConverter.getInstance();
                try {
                    currencyConverter.setCurrencyName(response.getString("code"));
                    currencyConverter.setMultiplier((float) response.getJSONArray("rates").getJSONObject(0).getDouble("mid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CurrencyActivity.this, getApplicationContext().getString(R.string.api_error), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
    }
}

