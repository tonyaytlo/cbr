package com.dev.anton.cbr.data.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ValuteEntity {

    @Element(name = "ID")
    private String id;

    @Element(name = "NumCode")
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    private int nominal;

    private String name;

    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
