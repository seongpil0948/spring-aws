package com.sixplus.server.api.service;

import com.sixplus.server.api.config.TestConfig;
import com.sixplus.server.api.hotel.dto.HotelRoomResponse;
import com.sixplus.server.api.hotel.service.HotelRoomDisplayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
//@Import(TestConfig.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class HotelRoomDisplayServiceTest01 {

    @Autowired
    private HotelRoomDisplayService hotelRoomDisplayService;

    @Test
    public void testTestConfiguration() {
        HotelRoomResponse hotelRoomResponse = hotelRoomDisplayService.getHotelRoomById(1L);

        Assertions.assertNotNull(hotelRoomResponse);
        Assertions.assertEquals(1L, hotelRoomResponse.getHotelRoomId());
    }

}
