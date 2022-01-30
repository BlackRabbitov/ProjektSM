package com.michal.projektsm.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class UserDao {

    public void insert(UserWithDebts userWithDebts, DebtEntity debtEntity){
        long id = getUserId(userWithDebts.getUser().getUserName());
        debtEntity.setUserCreatorId(id);
        //insertAllDebts(userWithDebts.getDebts());
        insertDebt(debtEntity);

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAllDebts(List<DebtEntity> debts);

    @Query("SELECT id FROM users WHERE userName=(:userName)")
    abstract long getUserId(String userName);

    @Query("SELECT * FROM users WHERE userName=(:userName)")
    abstract UserEntity getUser(String userName);

    @Insert
    public abstract void insertDebt(DebtEntity debtEntity);

    @Delete
    public abstract void deleteDebt(DebtEntity debtEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateAmount(DebtEntity debtEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateAllDebts(List<DebtEntity> debtEntities);

    @Insert
    public abstract void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE userName=(:userName) AND password=(:password)")
    public abstract UserEntity login(String userName, String password);

    @Query("SELECT * FROM users")
    public abstract List<UserEntity> getAllUsers();

    @Transaction
    @Query("SELECT * FROM users WHERE userName=(:userName)")
    public abstract UserWithDebts getUserWithDebts(String userName);
}
