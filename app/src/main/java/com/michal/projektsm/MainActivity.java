package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView msg = (TextView)findViewById(R.id.textView);
        String user = getIntent().getExtras().getString("User Name:");
        msg.setText("Welcome "+user); //a
    }
}