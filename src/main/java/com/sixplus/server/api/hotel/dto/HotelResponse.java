package com.sixplus.server.api.hotel.dto;

import com.sixplus.server.api.hotel.model.HotelEntity;
import com.sixplus.server.api.hotel.model.HotelRoomEntity;
import com.sixplus.server.api.hotel.model.HotelStatus;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;
import java.util.Random;

@Data
public class HotelResponse {

    public static final HotelResponse EMPTY = new HotelResponse();

    private Long hotelId;
    private HotelStatus hotelStatus;
    private String name;
    private String phoneNumber;
    private String address;
    private HotelRoomResponse[] rooms;

    public HotelResponse() {}

    public static HotelResponse onlyEntity(HotelEntity hotelEntity) {
        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.hotelId = hotelEntity.getHotelId();
        hotelResponse.hotelStatus = hotelEntity.getStatus();
        hotelResponse.name = hotelEntity.getName();
        hotelResponse.address = hotelEntity.getAddress();
        hotelResponse.phoneNumber = hotelEntity.getPhoneNumber();
        return hotelResponse;
    }

    public static HotelResponse of(HotelEntity hotelEntity, HotelRoomEntity[] hotelRoomEntities) {
        HotelResponse hotelResponse = HotelResponse.onlyEntity(hotelEntity);
        if (hotelRoomEntities != null) {
            hotelResponse.rooms = new HotelRoomResponse[hotelRoomEntities.length];
            for (int i = 0; i < hotelRoomEntities.length; i++) {
                hotelResponse.rooms[i] = HotelRoomResponse.of(hotelRoomEntities[i]);
            }
        } else {
            hotelResponse.rooms = new HotelRoomResponse[0]; // 빈 배열로 초기화
        }
        return hotelResponse;
    }

    public static HotelResponse createRandom(int roomCount) {
        Random random = new Random();
        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.hotelId = random.nextLong();
        hotelResponse.hotelStatus = HotelStatus.values()[random.nextInt(HotelStatus.values().length)];
        hotelResponse.name = "Hotel " + random.nextInt(1000);
        hotelResponse.phoneNumber = "123-456-" + String.format("%04d", random.nextInt(10000));
        hotelResponse.address = "Address " + random.nextInt(1000);

        hotelResponse.rooms = new HotelRoomResponse[roomCount];
        for (int i = 0; i < roomCount; i++) {
            hotelResponse.rooms[i] = HotelRoomResponse.createRandom();
        }

        return hotelResponse;
    }
}
