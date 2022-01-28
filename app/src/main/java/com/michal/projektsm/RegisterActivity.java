package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.michal.projektsm.roomdatabase.UserDao;
import com.michal.projektsm.roomdatabase.UserDatabase;
import com.michal.projektsm.roomdatabase.UserEntity;
import com.michal.projektsm.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText userId, password, name, email;
    Button register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Create user entity
                UserEntity userEntity = new UserEntity();
                userEntity.setUserName(userId.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setName(name.getText().toString());
                userEntity.setEmail(email.getText().toString());

                if(validateInput(userEntity)){
                    if(validateEmail(userEntity.getEmail())) {
                        // Insert user into database
                        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                        final UserDao userDao = userDatabase.userDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Register user
                                userDao.registerUser(userEntity);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Registration completed!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect email!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    // Validate if data is empty
    private Boolean validateInput(UserEntity userEntity){
        if(userEntity.getName().isEmpty() || userEntity.getPassword().isEmpty() || userEntity.getName().isEmpty() || userEntity.getEmail().isEmpty()){
            return false;
        }
        return true;
    }

    private Boolean validateEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }
}