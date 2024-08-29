package com.sixplus.server.api.user.model;

import com.sixplus.server.api.hotel.model.AbstractManageEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Entity
@Table(name = "tb_queue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity extends AbstractManageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String password;
    private String gender;

    @OneToOne
    @MapsId
    private UserEntity user;

    public static QueueEntity createRandom(UserEntity user) {
        Random random = new Random();
        QueueEntity queue = new QueueEntity();
        queue.password = "password" + random.nextInt(1000);
        queue.gender = random.nextBoolean() ? "Male" : "Female";
        queue.user = user;
        return queue;
    }
}
