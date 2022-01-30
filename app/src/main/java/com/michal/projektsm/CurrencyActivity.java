package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyActivity extends AppCompatActivity {

    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);



    }

    public Retrofit getRetrofitInstance()
    {

        HttpLoggingInterceptor interceptror = new HttpLoggingInterceptor();
        interceptror.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptror)
                .build();


        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.nbp.pl/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Currency convert()
    {
        return null;
    }


}