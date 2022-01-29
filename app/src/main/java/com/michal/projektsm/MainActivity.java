package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.widget.Button;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tName;

    //navigation view variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        //navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar3);

        //toolbar
        setSupportActionBar(toolbar);

        //navigation menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
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
                Intent intent = new Intent(MainActivity.this, Currency.class);
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
                intent = new Intent(MainActivity.this, Expenses.class);
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
                intent = new Intent(MainActivity.this, Profile.class);
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