package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tName;

    //navigation view variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                intent = new Intent(MainActivity.this, Debtors.class);
                startActivity(intent);
                break;

            case R.id.nav_expenses:
                intent = new Intent(MainActivity.this, Expenses.class);
                startActivity(intent);
                break;

            case R.id.nav_notifications:
                intent = new Intent(MainActivity.this, Notifications.class);
                startActivity(intent);
                break;

            case R.id.nav_user:
                intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}