package com.sixplus.server.api.user.model;

import com.sixplus.server.api.hotel.model.AbstractManageEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends AbstractManageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String userName;
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
    private String roles;
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<AddressEntity> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private QueueEntity queue;



    static public UserEntity createUser(String userName, String displayName, String email, String phone, String membership) {
        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setDisplayName(displayName);
        user.setEmail(email);
        user.setEmailVerified(false);
        user.setPhone(phone);
        user.setDefaultAddressID(UUID.randomUUID());
        user.setPrefersNotifications(false);
        user.setSeasonalPhoto(null);
        user.setAvatar(null);
        user.setFavorite(false);
        user.setMembership(membership);
        user.setPassword("123");
        user.setRoles("ROLE_USER");
        return user;
    }
}