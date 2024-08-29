package com.sixplus.server.api.hotel.dto;

import com.sixplus.server.api.hotel.model.HotelRoomEntity;
import com.sixplus.server.api.hotel.model.HotelRoomType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Random;

@Getter
public class HotelRoomResponse {

    private Long hotelRoomId;
    private String code;
    private String roomNumber;
    private HotelRoomType roomType;
    private BigDecimal originalPrice;
    private Integer floor;
    private Integer bedCount;
    private Integer bathCount;

    public HotelRoomResponse() {}

    public static HotelRoomResponse of(HotelRoomEntity hotelRoomEntity) {
        HotelRoomResponse hotelRoomResponse = new HotelRoomResponse();
        hotelRoomResponse.hotelRoomId = hotelRoomEntity.getHotelRoomId();
        hotelRoomResponse.code = hotelRoomEntity.getCode();
        hotelRoomResponse.roomNumber = hotelRoomEntity.getRoomNumber();
        hotelRoomResponse.roomType = hotelRoomEntity.getRoomType();
        hotelRoomResponse.originalPrice = hotelRoomEntity.getOriginalPrice();
        hotelRoomResponse.floor = hotelRoomEntity.getFloor();
        hotelRoomResponse.bedCount = hotelRoomEntity.getBedCount();
        hotelRoomResponse.bathCount = hotelRoomEntity.getBathCount();
        return hotelRoomResponse;
    }

    public static HotelRoomResponse createRandom() {
        Random random = new Random();
        HotelRoomResponse hotelRoomResponse = new HotelRoomResponse();
        hotelRoomResponse.hotelRoomId = random.nextLong();
        hotelRoomResponse.code = "Code" + random.nextInt(1000);
        hotelRoomResponse.roomNumber = "Room" + random.nextInt(1000);
        hotelRoomResponse.roomType = HotelRoomType.values()[random.nextInt(HotelRoomType.values().length)];
        hotelRoomResponse.originalPrice = BigDecimal.valueOf(100 + random.nextInt(400));
        hotelRoomResponse.floor = random.nextInt(10) + 1;
        hotelRoomResponse.bedCount = random.nextInt(5) + 1;
        hotelRoomResponse.bathCount = random.nextInt(3) + 1;
        return hotelRoomResponse;
    }
}
