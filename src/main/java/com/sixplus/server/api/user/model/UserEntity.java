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


    static public UserEntity createUser(String username, String displayName, String email, String phone, String membership, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setDisplayName(displayName);
        user.setEmail(email);
        user.setEmailVerified(false);
        user.setPhone(phone);
        user.setPrefersNotifications(false);
        user.setSeasonalPhoto(null);
        user.setAvatar(null);
        user.setFavorite(false);
        user.setMembership(membership);

        user.setPassword(password);
        return user;
    }
}