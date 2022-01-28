package com.michal.projektsm.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class UserDao {

    public void insert(UserWithDebts userWithDebts){
        long id = getUserId(userWithDebts.getUser().getUserName());
        userWithDebts.getDebts().forEach(i->i.setUserCreatorId(id));
        insertAllDebts(userWithDebts.getDebts());
    }

    @Insert
    abstract void insertAllDebts(List<DebtEntity> debts);

    @Query("SELECT id FROM users WHERE userName=(:userName)")
    abstract long getUserId(String userName);

    @Insert
    public abstract void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE userName=(:userName) AND password=(:password)")
    public abstract UserEntity login(String userName, String password);

    @Transaction
    @Query("SELECT * FROM users WHERE userName=(:userName)")
    public abstract List<UserWithDebts> getUserWithDebtsLists(String userName);
}
