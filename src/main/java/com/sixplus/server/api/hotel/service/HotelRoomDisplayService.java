package com.sixplus.server.api.hotel.service;

import com.sixplus.server.api.hotel.dto.HotelRoomResponse;
import com.sixplus.server.api.hotel.model.HotelRoomEntity;
import com.sixplus.server.api.hotel.repository.HotelRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class HotelRoomDisplayService {

    private final HotelRoomRepository hotelRoomRepository;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public HotelRoomDisplayService(HotelRoomRepository hotelRoomRepository,
                                   ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public HotelRoomResponse getHotelRoomById(Long id) {
        Optional<HotelRoomEntity> hotelRoomEntity = hotelRoomRepository.findById(id);

        // Using thread pool to log the entity
        threadPoolTaskExecutor.execute(() -> log.warn("entity :{}", hotelRoomEntity.toString()));

        if (hotelRoomEntity.isPresent()) {
            return HotelRoomResponse.of(hotelRoomEntity.get());
        } else {
            return null; // or throw an exception if preferred
        }
    }
}
