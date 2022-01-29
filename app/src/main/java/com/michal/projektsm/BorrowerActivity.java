package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    UserDatabase database;
    BorrowerAdapter adapter;
    List<DebtEntity> borrowers;
    UserWithDebts userWithDebts;
    DebtEntity sameDebtEntity;
    RecyclerView borrowersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowers);

        database = UserDatabase.getUserDatabase(this);
        userWithDebts = database.userDao().getUserWithDebts(ActiveUser.getInstance().getUser().getUserName());
        borrowers = userWithDebts.getDebts();

        borrowersView = (RecyclerView) findViewById(R.id.rvBorrowers);
        adapter = new BorrowerAdapter(this, borrowers);
        borrowersView.setAdapter(adapter);
        borrowersView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(borrowersView);

        OnClickListener btnClick = v -> {
            DebtEntity debt1 = new DebtEntity();
            debt1.setBorrower(((TextView) findViewById(R.id.etBorrower)).getText().toString());
            debt1.setAmount(Float.parseFloat(((TextView)findViewById(R.id.etAmount)).getText().toString()));
            // Check if there is debt with the same name
            sameDebtEntity = null;
            for (DebtEntity debtEntity : borrowers){
                if(debtEntity.getBorrower().equals(debt1.getBorrower())){
                    // Notify that there is similar Debt
                    sameDebtEntity = debtEntity;
                }
            }

            if(sameDebtEntity != null){
                sameDebtEntity.setAmount(sameDebtEntity.getAmount() + debt1.getAmount());
                if(sameDebtEntity.getAmount() == 0){
                    borrowers.remove(sameDebtEntity);
                    database.userDao().deleteDebt(sameDebtEntity);
                    adapter.notifyItemRemoved(borrowers.indexOf(sameDebtEntity));
                    Log.d(TAG, "Debt from borrower: " + debt1.getBorrower() + " paid. Deleting debt");
                } else {
                    borrowers.get(borrowers.indexOf(sameDebtEntity)).setAmount(sameDebtEntity.getAmount());
                    database.userDao().updateAmount(sameDebtEntity);
                    adapter.notifyItemChanged(borrowers.indexOf(sameDebtEntity));
                    Log.d(TAG, "Found similar borrower: " + debt1.getBorrower() + " changed amount to: " + sameDebtEntity.getAmount());
                }
            } else {
                borrowers.add(debt1);
                database.userDao().insert(userWithDebts, debt1);
                adapter.notifyItemInserted(borrowers.size() - 1);
                Log.d(TAG, "Added new borrower: " + debt1.getBorrower() + " amount: " + debt1.getAmount());
            }
        };
        findViewById(R.id.btnAddBorrower).setOnClickListener(btnClick);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            database.userDao().deleteDebt(borrowers.get(viewHolder.getAdapterPosition()));
            borrowers.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };
}