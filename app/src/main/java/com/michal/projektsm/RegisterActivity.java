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
    UserDatabase database;

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
        database = UserDatabase.getUserDatabase(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Create user entity
                UserEntity userEntity = new UserEntity();
                userEntity.setUserName(userId.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setName(name.getText().toString());
                userEntity.setEmail(email.getText().toString());

                if(Boolean.FALSE.equals(validateInput(userEntity))) {
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Boolean.FALSE.equals(validateEmail(userEntity.getEmail()))){
                    Toast.makeText(getApplicationContext(), "Incorrect email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Boolean.FALSE.equals(validateUsernameAvailability(userEntity))) {
                    Toast.makeText(getApplicationContext(), "Username is taken", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Register user
                        database.userDao().registerUser(userEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Registration completed!", Toast.LENGTH_SHORT).show();
                                userId.setText(null);
                                password.setText(null);
                                name.setText(null);
                                email.setText(null);
                            }
                        });
                    }
                }).start();
                finish();
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

    private Boolean validateUsernameAvailability(UserEntity userEntity){
        if(database.userDao().getUser(userEntity.getUserName()) != null){
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