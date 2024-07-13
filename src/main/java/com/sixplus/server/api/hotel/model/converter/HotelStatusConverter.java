package com.sixplus.server.api.hotel.model.converter;


import com.sixplus.server.api.hotel.model.HotelStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter
public class HotelStatusConverter implements AttributeConverter<HotelStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(HotelStatus attribute) {
        if (Objects.isNull(attribute))
            return null;

        return attribute.getValue();
    }

    @Override
    public HotelStatus convertToEntityAttribute(Integer dbData) {

        if (Objects.isNull(dbData))
            return null;

        return HotelStatus.fromValue(dbData);
    }
}
