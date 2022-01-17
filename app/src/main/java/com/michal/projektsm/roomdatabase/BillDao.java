package com.michal.projektsm.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BillDao {

    @Insert
    void addBill(BillEntity billEntity);
}
