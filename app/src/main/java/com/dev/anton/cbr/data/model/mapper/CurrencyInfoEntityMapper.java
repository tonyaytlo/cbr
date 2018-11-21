package com.dev.anton.cbr.data.model.mapper;

import com.dev.anton.cbr.data.model.CurrencyEntity;
import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseMapper;
import com.dev.anton.cbr.domain.model.Currency;
import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.domain.model.CurrencyValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CurrencyInfoEntityMapper extends BaseMapper<CurrencyInfoEntity, CurrencyInfo> {

    @Override
    public CurrencyInfo mapTo(CurrencyInfoEntity currencyInfoEntity) {
        CurrencyInfo currencyInfo = null;
        if (currencyInfoEntity != null) {
            currencyInfo = new CurrencyInfo();
            currencyInfo.setCurrencies(mapTo(currencyInfoEntity.getCurrencyEntities()));
            currencyInfo.setDate(currencyInfoEntity.getDate());
            currencyInfo.setName(currencyInfoEntity.getName());
        }
        return currencyInfo;
    }

    private Currency mapTo(CurrencyEntity currencyEntity) {
        Currency currency = null;
        if (currencyEntity != null) {
            currency = new Currency(currencyEntity.getId());
            currency.setName(currencyEntity.getName());
            currency.setCharCode(currencyEntity.getCharCode());
            currency.setNominal(currencyEntity.getNominal());
            currency.setValue(mapTo(currencyEntity.getValue()));
        }
        return currency;
    }

    private CurrencyValue mapTo(String strValue) {
        CurrencyValue currencyValue = null;
        if (strValue != null && !strValue.isEmpty()) {
            currencyValue = new CurrencyValue(strValue.replace(",", "."));
        }
        return currencyValue;
    }

    private List<Currency> mapTo(List<CurrencyEntity> currencyEntityList) {
        final int possibleSize = currencyEntityList != null ? currencyEntityList.size() : 0;
        final List<Currency> currencyList = new ArrayList<>(possibleSize);
        if (possibleSize != 0) {
            for (CurrencyEntity currencyEntity : currencyEntityList) {
                final Currency currency = mapTo(currencyEntity);
                if (currency != null) {
                    currencyList.add(currency);
                }
            }
            Collections.sort(currencyList, new Comparator<Currency>() {
                @Override
                public int compare(Currency o1, Currency o2) {
                    return o1.getCharCode().compareTo(o2.getCharCode());
                }
            });
        }
        return currencyList;
    }
}
