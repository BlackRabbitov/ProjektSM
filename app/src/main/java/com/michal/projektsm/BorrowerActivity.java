package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.michal.projektsm.roomdatabase.ActiveUser;
import com.michal.projektsm.roomdatabase.DebtEntity;
import com.michal.projektsm.roomdatabase.UserDao;
import com.michal.projektsm.roomdatabase.UserDatabase;
import com.michal.projektsm.roomdatabase.UserWithDebts;

import java.util.ArrayList;
import java.util.List;

public class BorrowerActivity extends AppCompatActivity {
    public static final String TAG = "BorrowerActivity";
    private UserDatabase database;
    private BorrowerAdapter adapter;
    private List<DebtEntity> borrowers;
    private UserWithDebts userWithDebts;
    private DebtEntity sameDebtEntity;
    private RecyclerView borrowersView;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private ImageView imgSearch;

    //only for help with exeptions
    private EditText a1;
    private EditText a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowers);

        //Changing Android status bar color
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_700));
        //
        linearLayout = findViewById(R.id.LayoutName2);
        linearLayout.setVisibility(View.GONE);

        toolbar = findViewById(R.id.toolbar3);
        database = UserDatabase.getUserDatabase(this);
        userWithDebts = database.userDao().getUserWithDebts(ActiveUser.getInstance().getUser().getUserName());
        borrowers = userWithDebts.getDebts();

        //toolbar go home page
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //toolbar navigate to main menu
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BorrowerActivity.this, MainActivity.class));
            }
        });


        borrowersView = (RecyclerView) findViewById(R.id.rvBorrowers);
        adapter = new BorrowerAdapter(this, borrowers);
        borrowersView.setAdapter(adapter);
        borrowersView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(borrowersView);

        OnClickListener btnClick = v -> {
            //check if user or amount is correct
            a1 = (EditText) findViewById(R.id.etBorrower);
            a2 = (EditText) findViewById(R.id.etAmount);

            if (a1.length() == 0 || a2.length() == 0 ) {
                Toast.makeText(getApplicationContext(), "You need to fill fields", Toast.LENGTH_SHORT).show();
                return;
            } else if(Float.parseFloat(((TextView)findViewById(R.id.etAmount)).getText().toString()) < 0.01) {

                Toast.makeText(getApplicationContext(), "Need positive numbers!", Toast.LENGTH_SHORT).show();
                return;
            }

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

        //Search is here!!!
        imgSearch= (ImageView) findViewById(R.id.img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSearch.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);

            }
        });

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