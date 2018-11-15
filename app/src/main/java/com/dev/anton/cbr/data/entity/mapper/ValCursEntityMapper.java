package com.dev.anton.cbr.data.entity.mapper;

import com.dev.anton.cbr.data.entity.ValCursEntity;
import com.dev.anton.cbr.data.entity.ValuteEntity;
import com.dev.anton.cbr.domain.model.ValCurs;
import com.dev.anton.cbr.domain.model.Valute;

import java.util.ArrayList;
import java.util.List;

public class ValCursEntityMapper {

    public ValCurs mapTo(ValCursEntity valCursEntity) {
        ValCurs valCurs = new ValCurs();
        valCurs.setValutes(mapTo(valCursEntity.getValuteEntities()));
        valCurs.setDate(valCursEntity.getDate());
        valCurs.setName(valCursEntity.getName());
        return valCurs;
    }


    public Valute mapTo(ValuteEntity valuteEntity) {
        Valute valute = new Valute(valuteEntity.getId());
        return valute;
    }

    public List<Valute> mapTo(List<ValuteEntity> valuteEntityList) {
        final List<Valute> valuteList = new ArrayList<>(valuteEntityList.size());
        for (ValuteEntity valuteEntity : valuteEntityList) {
            final Valute valute = mapTo(valuteEntity);
            if (valute != null) {
                valuteList.add(valute);
            }
        }
        return valuteList;
    }

}
