package com.sixplus.server.api.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Entity
@Table(name = "tb_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    private String name;

    public static UserRoleEntity createRandom() {
        Random random = new Random();
        UserRoleEntity role = new UserRoleEntity();
        role.name = random.nextBoolean() ? "ROLE_USER" : "ROLE_ADMIN";
        return role;
    }
}
