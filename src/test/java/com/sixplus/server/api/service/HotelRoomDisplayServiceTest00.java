package com.sixplus.server.api.service;

import com.sixplus.server.api.hotel.dto.HotelRoomResponse;
import com.sixplus.server.api.hotel.service.HotelRoomDisplayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HotelRoomDisplayServiceTest00 {

    @Autowired
    private HotelRoomDisplayService hotelRoomDisplayService;

    @Test
    public void testOriginal() {
        HotelRoomResponse hotelRoomResponse =hotelRoomDisplayService.getHotelRoomById(1L);

        Assertions.assertNotNull(hotelRoomResponse);
        Assertions.assertEquals(1L, hotelRoomResponse.getHotelRoomId());
    }

}
