package com.sixplus.server.api.user.domain;

import com.sixplus.server.api.domain.AbstractManageEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "TB_QUEUE")
@Setter
@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class TB_QUEUE extends AbstractManageEntity {
    @Id
    private String id;

    private String password;
    private String gender;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private TB_USER user;

    // getters and setters
}