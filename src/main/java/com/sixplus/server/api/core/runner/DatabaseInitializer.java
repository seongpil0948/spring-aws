package com.sixplus.server.api.core.runner;

import com.sixplus.server.api.hotel.model.HotelEntity;
import com.sixplus.server.api.hotel.model.HotelRoomEntity;
import com.sixplus.server.api.hotel.repository.HotelRepository;
import com.sixplus.server.api.hotel.repository.HotelRoomRepository;
import com.sixplus.server.api.user.model.UserEntity;
import com.sixplus.server.api.user.model.UserRoleEntity;
import com.sixplus.server.api.user.repository.UserRepository;
import com.sixplus.server.api.user.repository.UserRoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final UserRoleEntityRepository userRoleEntityRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(UserRepository userRepository, HotelRepository hotelRepository,
                               UserRoleEntityRepository userRoleEntityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.userRoleEntityRepository = userRoleEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String pw = passwordEncoder.encode("123456");

        // Initialize User Roles
        if (userRoleEntityRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setName("ROLE_ADMIN");
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setName("ROLE_USER");
            userRoleEntityRepository.saveAll(Arrays.asList(userRole, adminRole));
        }

        // Initialize Users
        if (userRepository.count() < 3) {
            UserRoleEntity adminRole = userRoleEntityRepository.findByName("ROLE_ADMIN").orElseThrow();
            UserRoleEntity userRole = userRoleEntityRepository.findByName("ROLE_USER").orElseThrow();

            UserEntity user1 = UserEntity.createRandom(pw, Set.of(userRole, adminRole));
            UserEntity user2 = UserEntity.createRandom(pw, Set.of(userRole));
            UserEntity user3 = UserEntity.createRandom(pw, Set.of(userRole));
            userRepository.saveAll(Arrays.asList(user1, user2, user3));
        }

        // Initialize Hotels
        if (hotelRepository.count() < 3) {
            hotelRepository.saveAll(Arrays.asList(
                    HotelEntity.createRandom(),
                    HotelEntity.createRandom(),
                    HotelEntity.createRandom()
            ));
        }
    }
}
