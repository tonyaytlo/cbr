package com.dev.anton.cbr.domain.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency that = (Currency) o;

        return numCode == that.numCode &&
                Objects.equals(id, that.id) &&
                Objects.equals(charCode, that.charCode) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numCode, charCode, value);
    }
}
