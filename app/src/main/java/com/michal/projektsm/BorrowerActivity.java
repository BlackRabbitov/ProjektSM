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

import com.michal.projektsm.roomdatabase.ActiveUser;
import com.michal.projektsm.roomdatabase.DebtEntity;
import com.michal.projektsm.roomdatabase.UserDao;
import com.michal.projektsm.roomdatabase.UserDatabase;
import com.michal.projektsm.roomdatabase.UserWithDebts;

import java.util.ArrayList;
import java.util.List;

public class BorrowerActivity extends AppCompatActivity {
    public static final String TAG = "BorrowerActivity";

    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
    UserDao userDao = userDatabase.userDao();

    RecyclerView borrowersView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowers);

        /*List<DebtEntity> borrowers = new ArrayList<>();
        borrowers.add(new DebtEntity("Name", 1));
        borrowers.add(new DebtEntity("Name", 2));
        borrowers.add(new DebtEntity("Name", 3));
        borrowers.add(new DebtEntity("Name", 4));
        borrowers.add(new DebtEntity("Name", 5));
        borrowers.add(new DebtEntity("Name", 6));
        borrowers.add(new DebtEntity("Name", 7));
        borrowers.add(new DebtEntity("Name", 8));
        borrowers.add(new DebtEntity("Name", 9));
        borrowers.add(new DebtEntity("Name", 10));
        borrowers.add(new DebtEntity("Name", 11));*/

        List<UserWithDebts> userWithDebtsList = userDao.getUserWithDebtsLists(ActiveUser.getInstance().getUser().getName());
        List<DebtEntity> borrowers = userWithDebtsList.get(0).getDebts();

        borrowersView = (RecyclerView) findViewById(R.id.rvBorrowers);
        BorrowerAdapter adapter = new BorrowerAdapter(borrowers);
        borrowersView.setAdapter(adapter);
        borrowersView.setLayoutManager(new LinearLayoutManager(this));

        OnClickListener btnClick = v -> {
            DebtEntity debt1 = new DebtEntity();
            debt1.setBorrower(((TextView) findViewById(R.id.etBorrower)).getText().toString());
            debt1.setAmount(Integer.parseInt(((TextView)findViewById(R.id.etAmount)).getText().toString()));
            borrowers.add(debt1);
            adapter.notifyItemInserted(borrowers.size() - 1);
            Log.d(TAG, "Added new borrower: " + debt1.getBorrower() + " amount: " + debt1.getAmount());
        };
        findViewById(R.id.btnAddBorrower).setOnClickListener(btnClick);
    }
}