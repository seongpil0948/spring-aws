package com.sixplus.server.api.hotel.model;

import com.sixplus.server.api.hotel.model.converter.HotelRoomTypeConverter;
import lombok.Data;
import lombok.ToString;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.IntStream;

@Data
@ToString
@Entity(name = "hotelRooms")
@Table(name = "tn_hotel_rooms")
public class HotelRoomEntity extends AbstractManageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_room_id")
    private Long hotelRoomId;

    private String code;

    @Column(name = "hotels_hotel_id")
    private Long hotelId;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_type")
    @Convert(converter = HotelRoomTypeConverter.class)
    private HotelRoomType roomType;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    private Integer floor;

    @Column(name = "bed_count")
    private Integer bedCount;

    @Column(name = "bath_count")
    private Integer bathCount;

    public static HotelRoomEntity of(String code, String roomNumber, HotelRoomType hotelRoomType, BigDecimal originalPrice, Integer floor, Integer bedCount, Integer bathCount) {
        HotelRoomEntity hotelRoomEntity = new HotelRoomEntity();
        hotelRoomEntity.code = code;
        hotelRoomEntity.roomNumber = roomNumber;
        hotelRoomEntity.roomType = hotelRoomType;
        hotelRoomEntity.originalPrice = originalPrice;
        hotelRoomEntity.floor = floor;
        hotelRoomEntity.bedCount = bedCount;
        hotelRoomEntity.bathCount = bathCount;
        return hotelRoomEntity;
    }

    public static HotelRoomEntity createRandomEntity() {
        Random random = new Random();
        String code = "Code" + random.nextInt(1000);
        String roomNumber = "Room" + random.nextInt(1000);
        HotelRoomType roomType = HotelRoomType.values()[random.nextInt(HotelRoomType.values().length)];
        BigDecimal originalPrice = BigDecimal.valueOf(100 + random.nextInt(400));
        Integer floor = random.nextInt(10) + 1;
        Integer bedCount = random.nextInt(5) + 1;
        Integer bathCount = random.nextInt(3) + 1;

        return HotelRoomEntity.of(code, roomNumber, roomType, originalPrice, floor, bedCount, bathCount);
    }

    public static HotelRoomEntity[] createRandomEntities(int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> createRandomEntity())
                .toArray(HotelRoomEntity[]::new);
    }
}
