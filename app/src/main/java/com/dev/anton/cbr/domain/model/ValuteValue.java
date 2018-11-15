package com.dev.anton.cbr.domain.model;

import java.math.BigDecimal;

public class ValuteValue extends BigDecimal {

    public ValuteValue(String decimal) {
        super(decimal);
        setScale(4);
    }

}
