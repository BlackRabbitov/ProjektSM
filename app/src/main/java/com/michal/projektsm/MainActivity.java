package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.michal.projektsm.roomdatabase.ActiveUser;
import com.michal.projektsm.roomdatabase.DebtEntity;
import com.michal.projektsm.roomdatabase.UserDatabase;
import com.michal.projektsm.roomdatabase.UserEntity;
import com.michal.projektsm.roomdatabase.UserWithDebts;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private TextView tName;
    //DB variables
    private UserDatabase database;
    private List<DebtEntity> borrowers;
    private UserWithDebts userWithDebts;
    private UserEntity loggedInUser;

    //navigation view variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private CardView cardViewOwe;
    private CardView cardViewOwed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        loggedInUser = ActiveUser.getInstance().getUser();

        Float borrowedPlus = 0f; //amount of money people owe you
        Float borrowedMinus = 0f; //amount of money people you owe to people

        //navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar3);

        //card buttons
        cardViewOwe = (CardView) findViewById(R.id.cardViewOwe);
        cardViewOwed = (CardView) findViewById(R.id.cardViewOwed);

        //toolbar
        setSupportActionBar(toolbar);


        //navigation menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //DB
        database = UserDatabase.getUserDatabase(this);
        userWithDebts = database.userDao().getUserWithDebts(ActiveUser.getInstance().getUser().getUserName());
        borrowers = userWithDebts.getDebts();
        for (DebtEntity debtEntity : borrowers) {
            if(debtEntity.getAmount() > 0) {
                borrowedPlus += debtEntity.getAmount();
            } else {
                borrowedMinus -= debtEntity.getAmount();
            }
        }

        ((TextView) findViewById(R.id.textView4)).setText(borrowedMinus + "zł");
        ((TextView) findViewById(R.id.textView6)).setText(borrowedPlus + "zł");
        ((TextView) findViewById(R.id.textView8)).setText(String.format("%.2f", (borrowedPlus - borrowedMinus)) + "zł");

        cardViewOwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpensesActivity.class));
            }
        });

        cardViewOwed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BorrowerActivity.class));
            }
        });

    }

    @Override
    public void onResume() {
        Float borrowedPlus = 0f; //amount of money people owe you
        Float borrowedMinus = 0f; //amount of money people you owe to people

        database = UserDatabase.getUserDatabase(this);
        userWithDebts = database.userDao().getUserWithDebts(ActiveUser.getInstance().getUser().getUserName());
        borrowers = userWithDebts.getDebts();
        for (DebtEntity debtEntity : borrowers) {
            if(debtEntity.getAmount() > 0) {
                borrowedPlus += debtEntity.getAmount();
            } else {
                borrowedMinus -= debtEntity.getAmount();
            }
        }
        ((TextView) findViewById(R.id.textView4)).setText(borrowedMinus + "zł");
        ((TextView) findViewById(R.id.textView6)).setText(borrowedPlus + "zł");
        ((TextView) findViewById(R.id.textView8)).setText(String.format("%.2f", (borrowedPlus - borrowedMinus)) + "zł");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //options menu switch activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_home:
            break;
            case R.id.nav_currency:
                Intent intent = new Intent(MainActivity.this, CurrencyActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_cat:
                intent = new Intent(MainActivity.this, Categories.class);
                startActivity(intent);
                break;

            case R.id.nav_dept:
                intent = new Intent(MainActivity.this, BorrowerActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_expenses:
                intent = new Intent(MainActivity.this, ExpensesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_notifications:

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Notifications")
                        .setSmallIcon(R.drawable.notification_use_icon)
                        .setContentTitle("PaymentManager calling")
                        .setContentText("Check this new handsome looking notification")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(15, builder.build());
                break;

            case R.id.nav_user:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_calc:
                Uri webpage = Uri.parse("https://calc.pl/");
                intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent != null)
                {
                    startActivity(intent);
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notifications", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}