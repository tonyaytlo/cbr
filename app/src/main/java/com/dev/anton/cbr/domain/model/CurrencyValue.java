package com.dev.anton.cbr.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyValue extends BigDecimal {

    private static final int DEFAULT_SCALE = 4;
    private int scale = DEFAULT_SCALE;

    public CurrencyValue(String decimal) {
        super(decimal);
        setScale(scale);
    }

    public CurrencyValue(String decimal, int scale) {
        super(decimal);
        this.scale = scale;
        setScale(scale);
    }

    public String getValuteString() {
        return this.stripTrailingZeros().toPlainString();
    }

}
