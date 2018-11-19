package com.dev.anton.cbr.data.model.mapper;

import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.CurrencyEntity;
import com.dev.anton.cbr.data.model.core.BaseMapper;
import com.dev.anton.cbr.domain.model.Currency;
import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.domain.model.CurrencyValue;

import java.util.ArrayList;
import java.util.List;

public class CurrencyInfoEntityMapper extends BaseMapper<CurrencyInfoEntity, CurrencyInfo> {

    @Override
    public CurrencyInfo mapTo(CurrencyInfoEntity currencyInfoEntity) {
        CurrencyInfo currencyInfo = new CurrencyInfo();
        currencyInfo.setCurrencies(mapTo(currencyInfoEntity.getCurrencyEntities()));
        currencyInfo.setDate(currencyInfoEntity.getDate());
        currencyInfo.setName(currencyInfoEntity.getName());
        return currencyInfo;
    }

    private Currency mapTo(CurrencyEntity currencyEntity) {
        Currency currency = new Currency(currencyEntity.getId());
        currency.setName(currencyEntity.getName());
        currency.setCharCode(currencyEntity.getCharCode());
        currency.setNominal(currencyEntity.getNominal());
        currency.setValue(new CurrencyValue(currencyEntity.getValue()
                .replace(",", ".")));
        return currency;
    }

    private List<Currency> mapTo(List<CurrencyEntity> currencyEntityList) {
        final List<Currency> currencyList = new ArrayList<>(currencyEntityList.size());
        for (CurrencyEntity currencyEntity : currencyEntityList) {
            final Currency currency = mapTo(currencyEntity);
            if (currency != null) {
                currencyList.add(currency);
            }
        }
        return currencyList;
    }
}
