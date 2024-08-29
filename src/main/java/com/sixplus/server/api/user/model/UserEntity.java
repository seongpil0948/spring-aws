package com.sixplus.server.api.user.model;

import com.sixplus.server.api.hotel.model.AbstractManageEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class UserEntity extends AbstractManageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    private String username;
    private String displayName;
    private String email;
    private boolean emailVerified;
    private String phone;
    private UUID defaultAddressID;
    private boolean prefersNotifications;
    private String seasonalPhoto;
    private String avatar;
    private boolean isFavorite;
    private String membership;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<AddressEntity> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private QueueEntity queue;

    public static UserEntity createRandom(String password, Set<UserRoleEntity> roles) {
        UserEntity user = new UserEntity();
        user.username = "user" + UUID.randomUUID().toString().substring(0, 5);
        user.displayName = "User " + UUID.randomUUID().toString().substring(0, 5);
        user.email = user.username + "@example.com";
        user.emailVerified = true;
        user.phone = "123-456-" + new Random().nextInt(10000);
        user.defaultAddressID = UUID.randomUUID();
        user.prefersNotifications = new Random().nextBoolean();
        user.seasonalPhoto = "default.jpg";
        user.avatar = "default.jpg";
        user.isFavorite = new Random().nextBoolean();
        user.membership = "Gold";
        user.password = password;
        user.roles = roles;
        return user;
    }
}
