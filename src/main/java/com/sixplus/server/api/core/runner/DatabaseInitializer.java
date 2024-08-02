package com.sixplus.server.api.core.runner;

import com.sixplus.server.api.hotel.model.HotelEntity;
import com.sixplus.server.api.hotel.repository.HotelRepository;
import com.sixplus.server.api.user.model.SimpleGrantedAuthority;
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

import static com.sixplus.server.api.user.model.UserEntity.createUser;


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
        // Initialize Users
        if (userRepository.count() < 3) {
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setName("ROLE_ADMIN");
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setName("ROLE_USER");
            userRoleEntityRepository.saveAll(Arrays.asList(userRole, adminRole));

            UserEntity user1 = createUser("user1", "User One", "user1@example.com", "123-456-7890", "Gold", pw);
            UserEntity user2 = createUser("user2", "User Two", "user2@example.com", "123-456-7891", "Silver", pw);
            UserEntity user3 = createUser("user3", "User Three", "user3@example.com", "123-456-7892", "Platinum", pw);
            Set<UserRoleEntity> roles = new HashSet<>();
            roles.add(userRole);
            user2.setRoles(roles);
            user3.setRoles(roles);
            userRepository.saveAll(Arrays.asList(user2, user3));

            roles.add(adminRole);
            user1.setRoles(roles);
            userRepository.save(user1);
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