package com.dev.anton.cbr.presentation.model;

import com.dev.anton.cbr.domain.model.Currency;
import com.dev.anton.cbr.domain.model.CurrencyValue;

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

    public CurrencyValue convert(CurrencyValue currency, Currency currencyFrom, Currency currencyTo) {
        final BigDecimal absFrom = getAbsValue(currencyFrom);
        final BigDecimal absTo = getAbsValue(currencyTo);
        CurrencyValue result = new CurrencyValue(absFrom.multiply(currency).divide(absTo, scale, roundingDivider).toString());
        return result;
    }

    private BigDecimal getAbsValue(Currency currency) {
        return currency.getValue().divide(new BigDecimal(currency.getNominal()), scale, roundingDivider);
    }
}
