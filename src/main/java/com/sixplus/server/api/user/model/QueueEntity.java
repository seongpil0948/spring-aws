package com.sixplus.server.api.user.model;

import com.sixplus.server.api.hotel.model.AbstractManageEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tb_queue")
@Setter
@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class QueueEntity extends AbstractManageEntity {
    @Id
    private String id;

    private String password;
    private String gender;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // getters and setters
}