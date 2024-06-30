package com.sixplus.server.api.user.domain;

import com.sixplus.server.api.domain.AbstractManageEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_USER")
public class TB_USER extends AbstractManageEntity {
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
    private List<TB_ADDRESS> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private TB_QUEUE queue;

    // getters and setters
}



