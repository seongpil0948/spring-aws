package com.sixplus.server.api.service;

import com.sixplus.server.api.hotel.dto.HotelRoomResponse;
import com.sixplus.server.api.hotel.model.HotelRoomEntity;
import com.sixplus.server.api.hotel.repository.HotelRoomRepository;
import com.sixplus.server.api.hotel.service.HotelRoomDisplayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class HotelRoomDisplayServiceTest02 {

    @Autowired
    private HotelRoomDisplayService hotelRoomDisplayService;

    @MockBean
    // 실제 DB를 조회하는 코드가 아니라, 목업 객체
    private HotelRoomRepository hotelRoomRepository;

    @Test
    public void testMockBean() {
        HotelRoomEntity hotelRoom = (HotelRoomEntity.createRandomEntity());
        hotelRoom.setHotelRoomId(10L);

        // 목업 객체의 행동 정의
        // 조건에 맞는 스텁이 호출되면, 입력값 리턴
        given(this.hotelRoomRepository.findById(any())).willReturn(Optional.of(hotelRoom));

        HotelRoomResponse hotelRoomResponse = hotelRoomDisplayService.getHotelRoomById(hotelRoom.getHotelRoomId());

        Assertions.assertNotNull(hotelRoomResponse);
        Assertions.assertEquals(10L, hotelRoomResponse.getHotelRoomId());
    }
}
