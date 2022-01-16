package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class BorrowerListActivity extends AppCompatActivity {

    ListView borrowersListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_list);

        borrowersListView = (ListView) findViewById(R.id.borrowersListView);
    }
}