package com.michal.projektsm.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity(tableName = "bills")
public class BillEntity {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    // To jest datą bo trzeba by było robić cuda wianki żeby zapisać format Date do bazy danych xD
    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "email")
    Integer amount;

    @ColumnInfo(name = "isPaid")
    Boolean isPaid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String  getDate() {
        return date;
    }

    public void setPassword(String  date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
