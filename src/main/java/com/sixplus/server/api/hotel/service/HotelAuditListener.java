package com.sixplus.server.api.hotel.service;

import com.sixplus.server.api.hotel.model.HotelEntity;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;

@Slf4j
public class HotelAuditListener {

    @PostPersist
    public void logWhenCreated(HotelEntity hotelEntity){
        log.info("hotel created. id:{}", hotelEntity.getHotelId());
    }

    @PostUpdate
    @PostRemove
    public void logWhenChanged(HotelEntity hotelEntity) {
        log.info("hotel changed. id:{}, name:{}", hotelEntity.getHotelId(), hotelEntity.getName());
    }
}
