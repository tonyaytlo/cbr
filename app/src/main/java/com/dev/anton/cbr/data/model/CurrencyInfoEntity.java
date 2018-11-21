package com.dev.anton.cbr.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Objects;

@Root(name = "ValCurs")
public class CurrencyInfoEntity {

    @Attribute(name = "Date")
    private String date;

    @Attribute
    private String name;

    @ElementList(inline = true)
    private List<CurrencyEntity> currencyEntities;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CurrencyEntity> getCurrencyEntities() {
        return currencyEntities;
    }

    public void setCurrencyEntities(List<CurrencyEntity> currencyEntities) {
        this.currencyEntities = currencyEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyInfoEntity that = (CurrencyInfoEntity) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(currencyEntities, that.currencyEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, currencyEntities);
    }
}
