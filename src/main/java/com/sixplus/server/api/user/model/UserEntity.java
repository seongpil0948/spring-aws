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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private QueueEntity queue;

    // 기존 생성자나 메서드 등을 통해 id와 userName을 설정한 후 호출합니다.
    public void updateId() {
        if (this.userName != null && !this.userName.isEmpty()) {
            this.id = genUUID(this.id, this.userName);
        }
    }

    private String genUUID(String userId, String name) {
        String source = userId + ":" + name;
        UUID uuid = UUID.nameUUIDFromBytes(source.getBytes());
        return uuid.toString();
    }

    public void setUserName(String userName) {
        this.userName = userName;
        updateId();
    }

    static public UserEntity createUser(String id, String userName, String displayName, String email, String phone, String membership) {
        UserEntity user = new UserEntity();
        user.setId(id);
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
        user.updateId();
        return user;
    }
}