package com.michal.projektsm.roomdatabase;

import android.content.Context;
import android.service.autofill.UserData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class, BillEntity.class}, version = 3)
public abstract class UserDatabase extends RoomDatabase{

    private static final String dbName = "user";
    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context){
        if (userDatabase == null){
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, dbName)
                    .fallbackToDestructiveMigration().build();
        }

        return userDatabase;
    }

    public abstract UserDao userDao();

    public abstract  BillDao billDao();
}
