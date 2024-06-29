package com.sixplus.server.api.repository;

import com.sixplus.server.api.domain.HotelEntity;
import com.sixplus.server.api.domain.HotelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    List<HotelEntity> findByStatus(HotelStatus status);

    HotelEntity findByHotelId(Long hotelId);

    @Query("SELECT h FROM hotels AS h WHERE h.hotelId = ?1 AND h.status = 0")
    HotelEntity findReadyOne(Long hotelId);

    @Query("SELECT h FROM hotels AS h WHERE h.hotelId = :hotelId AND h.status = :status")
    HotelEntity findOne(@Param("hotelId") Long hotelId, @Param("status") HotelStatus status);
}

