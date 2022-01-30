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
import android.widget.TextView;
import android.widget.Toast;

import com.michal.projektsm.roomdatabase.ActiveUser;
import com.michal.projektsm.roomdatabase.UserDatabase;
import com.michal.projektsm.roomdatabase.UserEntity;

import java.util.stream.Collectors;

public class ProfileActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ConstraintLayout constraintlayout;
    private AnimationDrawable animationDrawable;
    private Button logButton;
    private Button changeButton;
    private Button acceptChangeButton;
    private LinearLayout linearLayoutChange;
    private LinearLayout linearLayoutAccept;
    private TextView userName;
    private TextView newUserName;
    private UserDatabase database;
    private UserEntity loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //DB
        database = UserDatabase.getUserDatabase(this);
        loggedInUser = ActiveUser.getInstance().getUser();

        toolbar = findViewById(R.id.toolbar3);
        linearLayoutChange = (LinearLayout) findViewById(R.id.changeNameLayout);
        linearLayoutAccept = (LinearLayout) findViewById(R.id.accept_layout);
        linearLayoutAccept.setVisibility(View.INVISIBLE);
        userName = findViewById(R.id.userName);
        newUserName = findViewById(R.id.newUsername);
        userName.setText(loggedInUser.getUserName());

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
        acceptChangeButton = findViewById(R.id.accept_button);
        acceptChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newUserName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "You need to write new username, dummy!", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserEntity exists = database.userDao().getUser(newUserName.getText().toString());

                if(exists != null){
                    Toast.makeText(getApplicationContext(), "Username is taken!", Toast.LENGTH_SHORT).show();
                    return;
                }

                loggedInUser.setUserName(newUserName.getText().toString());
                database.userDao().updateUser(loggedInUser);

                linearLayoutChange.setVisibility(View.VISIBLE);
                linearLayoutAccept.setVisibility(View.INVISIBLE);

                newUserName.setText("");
                userName.setText(loggedInUser.getUserName());
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutChange.setVisibility(View.INVISIBLE);
                linearLayoutAccept.setVisibility(View.VISIBLE);

            }
        });


    }
}