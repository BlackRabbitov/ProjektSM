package com.michal.projektsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.michal.projektsm.roomdatabase.UserDao;
import com.michal.projektsm.roomdatabase.UserDatabase;
import com.michal.projektsm.roomdatabase.UserEntity;

public class RegisterActivity extends AppCompatActivity {

    EditText userId, password, name;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Create user entity
                UserEntity userEntity = new UserEntity();
                userEntity.setUserId(userId.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setName(name.getText().toString());

                if(validateInput(userEntity)){
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
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateInput(UserEntity userEntity){
        if(userEntity.getName().isEmpty() || userEntity.getPassword().isEmpty() || userEntity.getName().isEmpty()){
            return false;
        }
        return true;
    }
}