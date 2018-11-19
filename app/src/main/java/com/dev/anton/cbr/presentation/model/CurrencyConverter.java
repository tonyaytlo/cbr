package com.dev.anton.cbr.presentation.model;

import com.dev.anton.cbr.domain.model.Valute;
import com.dev.anton.cbr.domain.model.ValuteValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

    private int scale = 4;
    private RoundingMode roundingDivider = RoundingMode.HALF_DOWN;

    public CurrencyConverter() {
    }

    public CurrencyConverter(int scale) {
        this.scale = scale;
    }

    public ValuteValue convert(ValuteValue currency, Valute currencyFrom, Valute currencyTo) {
        final BigDecimal absFrom = getAbsValue(currencyFrom);
        final BigDecimal absTo = getAbsValue(currencyTo);
        ValuteValue result = new ValuteValue(absFrom.multiply(currency).divide(absTo, scale, roundingDivider).toString());
        return result;
    }

    private BigDecimal getAbsValue(Valute valute) {
        return valute.getValue().divide(new BigDecimal(valute.getNominal()), scale, roundingDivider);
    }
}
