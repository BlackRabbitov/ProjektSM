package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.michal.projektsm.roomdatabase.ActiveUser;
import com.michal.projektsm.roomdatabase.UserDatabase;

import java.util.stream.Collectors;

public class ProfileActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ConstraintLayout constraintlayout;
    private AnimationDrawable animationDrawable;
    private Button logButton;
    private Button changeButton;
    private LinearLayout linearLayoutChange;
    private LinearLayout linearLayoutAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar3);
        linearLayoutChange = (LinearLayout) findViewById(R.id.changeNameLayout);
        linearLayoutAccept = (LinearLayout) findViewById(R.id.accept_layout);
        linearLayoutAccept.setVisibility(View.INVISIBLE);

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

        //background animation
        constraintlayout = (ConstraintLayout) findViewById(R.id.profile);

        animationDrawable = (AnimationDrawable) constraintlayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        //Changing Android status bar color
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_700));
        //

        //Change your username!!!
        changeButton = findViewById(R.id.changeUsername);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutChange.setVisibility(View.INVISIBLE);
                linearLayoutAccept.setVisibility(View.VISIBLE);

            }
        });


    }
}