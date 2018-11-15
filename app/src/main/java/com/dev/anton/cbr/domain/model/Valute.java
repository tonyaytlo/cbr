package com.dev.anton.cbr.domain.model;

public class Valute {

    private String id;

    private int numCode;

    private String charCode;

    private int nominal;

    private String name;

    private ValuteValue value;

    public Valute(String id) {
        this.id = id;
    }

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

    public ValuteValue getValue() {
        return value;
    }

    public void setValue(ValuteValue value) {
        this.value = value;
    }
}
