package com.michal.projektsm.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE userName=(:userName) AND password=(:password)")
    UserEntity login(String userName, String password);

    @Transaction
    @Query("SELECT * FROM users")
    public List<UserWithDebts> getUserWithDebtsLists();
}
