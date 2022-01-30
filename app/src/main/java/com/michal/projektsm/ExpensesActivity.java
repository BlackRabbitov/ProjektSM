package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.michal.projektsm.roomdatabase.ActiveUser;
import com.michal.projektsm.roomdatabase.DebtEntity;
import com.michal.projektsm.roomdatabase.UserDatabase;
import com.michal.projektsm.roomdatabase.UserWithDebts;

import java.util.List;
import java.util.stream.Collectors;

public class ExpensesActivity extends AppCompatActivity {
    public static final String TAG = "ExpensesActivity";
    private UserDatabase database;
    private ExpensesAdapter adapter;
    private List<DebtEntity> expenses;
    private UserWithDebts userWithDebts;
    private DebtEntity sameExpenseEntity;
    private RecyclerView expensesView;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private ImageView imgSearch;

    //only for help with exeptions
    private EditText a1;
    private EditText a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

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
        expenses = userWithDebts.getDebts().stream().filter(item -> item.getAmount().floatValue() < 0.0f).collect(Collectors.toList());

        //toolbar go home page
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //toolbar navigate to main menu
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        expensesView = (RecyclerView) findViewById(R.id.rvExpenses);
        adapter = new ExpensesAdapter(this, expenses);
        expensesView.setAdapter(adapter);
        expensesView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(expensesView);

        OnClickListener btnClick = v -> {
            //check if user or amount is correct
            a1 = (EditText) findViewById(R.id.etExpense);
            a2 = (EditText) findViewById(R.id.etAmount);

            if (a1.length() == 0 || a2.length() == 0 ) {
                Toast.makeText(getApplicationContext(), "You need to fill fields", Toast.LENGTH_SHORT).show();
                return;
            } else if(Float.parseFloat(((TextView)findViewById(R.id.etAmount)).getText().toString()) < 0.01f) {

                Toast.makeText(getApplicationContext(), "Need positive numbers!", Toast.LENGTH_SHORT).show();
                return;
            }

            DebtEntity debt1 = new DebtEntity();

            debt1.setBorrower(((TextView) findViewById(R.id.etExpense)).getText().toString());
            debt1.setAmount(Float.parseFloat(((TextView)findViewById(R.id.etAmount)).getText().toString())*-1.0f);
            debt1.setUserCreatorId(ActiveUser.getInstance().getUser().getId());
            // Check if there is debt with the same name
            sameExpenseEntity = null;
            for (DebtEntity debtEntity : expenses){
                if(debtEntity.getBorrower().equals(debt1.getBorrower())){
                    // Notify that there is similar Debt
                    sameExpenseEntity = debtEntity;
                }
            }
            if(sameExpenseEntity != null){
                sameExpenseEntity.setAmount(sameExpenseEntity.getAmount() + debt1.getAmount());
                if(sameExpenseEntity.getAmount() == 0){
                    expenses.remove(sameExpenseEntity);
                    database.userDao().deleteDebt(sameExpenseEntity);
                    adapter.notifyItemRemoved(expenses.indexOf(sameExpenseEntity));
                    Log.d(TAG, "Expense: " + debt1.getBorrower() + " paid. Deleting expense");
                } else {
                    database.userDao().updateAmount(sameExpenseEntity);
                    adapter.notifyItemChanged(expenses.indexOf(sameExpenseEntity));
                    Log.d(TAG, "Found similar expense: " + debt1.getBorrower() + " changed amount to: " + sameExpenseEntity.getAmount());
                }
            } else {
                expenses.add(debt1);
                database.userDao().insertDebt(debt1);
                //database.userDao().insert(userWithDebts, debt1);
                adapter.notifyItemInserted(expenses.size());
                userWithDebts = database.userDao().getUserWithDebts(ActiveUser.getInstance().getUser().getUserName());
                expenses = userWithDebts.getDebts().stream().filter(item -> item.getAmount().floatValue() < 0.0f).collect(Collectors.toList());
                adapter.setmDataSet(expenses);
                Log.d(TAG, "Added new expense: " + debt1.getBorrower() + " amount: " + debt1.getAmount());
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

        findViewById(R.id.btnAddExpense).setOnClickListener(btnClick);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            database.userDao().deleteDebt(expenses.get(viewHolder.getAdapterPosition()));
            expenses.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };
}