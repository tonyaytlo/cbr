package com.dev.anton.cbr.domain.model;

public class Currency {

    public Currency(String id) {
        this.id = id;
    }

    private String id;

    private int numCode;

    private String charCode;

    private int nominal;

    private String name;

    private CurrencyValue value;

    public String getId() {
        return id;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyValue getValue() {
        return value;
    }

    public void setValue(CurrencyValue value) {
        this.value = value;
    }
}
