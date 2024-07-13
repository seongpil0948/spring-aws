package com.sixplus.server.api.hotel.model;


import com.sixplus.server.api.hotel.model.converter.HotelRoomTypeConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@ToString
@Entity(name = "hotelRooms")
@Table(name = "hotel_rooms")
public class HotelRoomEntity extends AbstractManageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_room_id")
    private Long hotelRoomId;

    @Column(name = "hotels_hotel_id")
    private Long hotelId;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_type")
    @Convert(converter = HotelRoomTypeConverter.class)
    private HotelRoomType roomType;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    public static HotelRoomEntity of(String roomNumber, HotelRoomType hotelRoomType, BigDecimal originalPrice) {
        HotelRoomEntity hotelRoomEntity = new HotelRoomEntity();
        hotelRoomEntity.roomNumber = roomNumber;
        hotelRoomEntity.roomType = hotelRoomType;
        hotelRoomEntity.originalPrice = originalPrice;
        return hotelRoomEntity;
    }
}
