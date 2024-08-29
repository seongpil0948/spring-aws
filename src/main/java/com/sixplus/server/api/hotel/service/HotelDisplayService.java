package com.sixplus.server.api.hotel.service;

import com.sixplus.server.api.core.aspect.ElapseLoggable;
import com.sixplus.server.api.hotel.dto.HotelRequest;
import com.sixplus.server.api.hotel.dto.HotelResponse;
import com.sixplus.server.api.hotel.model.HotelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class HotelDisplayService implements DisplayService {

    @Override
    @ElapseLoggable
    public List<HotelResponse> getHotelsByName(HotelRequest hotelRequest) {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error("error", e);
        }
        return List.of(
                new HotelResponse()
        );
    }
}
