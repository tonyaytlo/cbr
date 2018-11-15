package com.dev.anton.cbr.data.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class ValCursEntity {

    @Attribute(name = "Date")
    private String date;

    @Attribute
    private String name;

    @ElementList
    private List<ValuteEntity> valuteEntities;

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

    public List<ValuteEntity> getValuteEntities() {
        return valuteEntities;
    }

    public void setValuteEntities(List<ValuteEntity> valuteEntities) {
        this.valuteEntities = valuteEntities;
    }
}
