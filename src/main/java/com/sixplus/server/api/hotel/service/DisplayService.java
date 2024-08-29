package com.sixplus.server.api.hotel.service;


import com.sixplus.server.api.hotel.dto.HotelRequest;
import com.sixplus.server.api.hotel.dto.HotelResponse;

import java.util.List;

public interface DisplayService {

    List<HotelResponse> getHotelsByName(HotelRequest hotelRequest);
}
