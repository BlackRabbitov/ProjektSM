package com.michal.projektsm;



public class CurrencyConverter {
    private static CurrencyConverter instance = null;
    private String currencyName = "PLN";
    private Float multiplier = 1.0f;
    protected CurrencyConverter() { }

    public static CurrencyConverter getInstance() {
        if(instance == null) {
            instance = new CurrencyConverter();
        }
        return instance;
    }

    public void setCurrency(String currencyName, Float multiplier) {
        this.currencyName = currencyName;
        this.multiplier = multiplier;
    }

    public void setMultiplier(Float multiplier) {
        this.multiplier = multiplier;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public Float getMultiplier() {
        return this.multiplier;
    }

    public String getCurrency(Float value)
    {
        switch(currencyName)
        {
            case "PLN":
                return String.format("%.2f", (value / multiplier)) + "zł";
            case "EUR":
                return "€" + String.format("%.2f", (value / multiplier));
            case "USD":
                return "$" + String.format("%.2f", (value / multiplier));
            case "GBP":
                return "£" + String.format("%.2f", (value / multiplier));
            default:
                return "Currency not handled";
        }
    }
}
