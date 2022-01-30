package com.michal.projektsm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.GET;

public class Rates {

    private String no;
    private String effectiveDate;
    private float mid;


    // Getter Methods

    public String getNo() {
        return no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public float getMid() {
        return mid;
    }

    // Setter Methods

    public void setNo(String no) {
        this.no = no;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setMid(float mid) {
        this.mid = mid;
    }
}
