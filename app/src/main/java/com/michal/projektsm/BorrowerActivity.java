package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BorrowerActivity extends AppCompatActivity {
    public static final String TAG = "BorrowerActivity";

    RecyclerView borrowersView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowers);

        List<String> borrowers = new ArrayList<>();

        borrowers.add("adam");
        borrowers.add("janusz");
        borrowers.add("mariusz");
        borrowers.add("dariusz");
        borrowers.add("jariusz");
        borrowers.add("olek");
        borrowers.add("kolek");
        borrowers.add("tolek");
        borrowers.add("jolek");
        borrowers.add("molek");
        borrowers.add("frolek");
        borrowers.add("trollek");

        borrowersView = (RecyclerView) findViewById(R.id.rvBorrowers);
        BorrowerAdapter adapter = new BorrowerAdapter(borrowers);
        borrowersView.setAdapter(adapter);
        borrowersView.setLayoutManager(new LinearLayoutManager(this));

        OnClickListener btnClick = v -> {
            String borrowerName = ((TextView) findViewById(R.id.etBorrower)).getText().toString();
            borrowers.add(borrowerName);
            adapter.notifyItemInserted(borrowers.size() - 1);
            Log.d(TAG, "Added new borrower: " + borrowerName);
        };
        findViewById(R.id.btnAddBorrower).setOnClickListener(btnClick);
    }
}