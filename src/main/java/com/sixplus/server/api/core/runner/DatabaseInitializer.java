package com.sixplus.server.api.core.runner;

import com.sixplus.server.api.hotel.model.HotelEntity;
import com.sixplus.server.api.hotel.repository.HotelRepository;
import com.sixplus.server.api.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.sixplus.server.api.user.model.UserEntity.createUser;


@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    public DatabaseInitializer(UserRepository userRepository, HotelRepository hotelRepository) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Initialize Users
        if (userRepository.count() < 3) {
            userRepository.saveAll(Arrays.asList(
                    createUser("user1", "User One", "user1@example.com", "123-456-7890", "Gold"),
                    createUser("user2", "User Two", "user2@example.com", "123-456-7891", "Silver"),
                    createUser("user3", "User Three", "user3@example.com", "123-456-7892", "Platinum")
            ));
        }

        // Initialize Hotels
        if (hotelRepository.count() < 3) {
            hotelRepository.saveAll(Arrays.asList(
                    HotelEntity.of("Hotel California", "42 Sunset Blvd", "123-456-7890"),
                    HotelEntity.of("The Grand Budapest Hotel", "24 Mount Ave", "123-456-7891"),
                    HotelEntity.of("Hotel Transylvania", "13 Dark Alley", "123-456-7892")
            ));
        }

    }

}