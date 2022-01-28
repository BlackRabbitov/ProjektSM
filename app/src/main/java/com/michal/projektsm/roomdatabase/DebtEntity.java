package com.michal.projektsm.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity(tableName = "Debtors")
public class DebtEntity {
    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "borrower")
    String borrower;

    @ColumnInfo(name = "owner")
    long userCreatorId;

    @ColumnInfo(name = "amount")
    Float amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public long getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(long userCreatorId) {
        this.userCreatorId = userCreatorId;
    }
    public DebtEntity (String borrower, Float amount)
    {
        this.borrower = borrower;
        this.amount = amount;
    }
    public DebtEntity() {}
}
