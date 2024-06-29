package com.sixplus.server.api.domain.converter;

import com.sixplus.server.api.domain.HotelRoomType;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;
import java.util.Objects;

@Component
@Converter
public class HotelRoomTypeConverter implements AttributeConverter<HotelRoomType, Integer> {


    @Override
    public Integer convertToDatabaseColumn(HotelRoomType attribute) {
        if (Objects.isNull(attribute))
            return null;

        return attribute.getValue();
    }

    @Override
    public HotelRoomType convertToEntityAttribute(Integer dbData) {
        if (Objects.isNull(dbData))
            return null;

        return HotelRoomType.fromValue(dbData);
    }
}
