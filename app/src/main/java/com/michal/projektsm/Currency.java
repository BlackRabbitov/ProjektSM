package com.michal.projektsm;

import java.util.ArrayList;

public class Currency {

    private String table;
    private String currency;
    private String code;
    ArrayList<Rates> rates = new ArrayList <Rates>();


    // Getter Methods

    public String getTable() {
        return table;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    // Setter Methods

    public void setTable(String table) {
        this.table = table;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
