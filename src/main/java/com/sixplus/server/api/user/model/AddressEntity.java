package com.sixplus.server.api.user.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_address")
public class AddressEntity {
    @Id
    private UUID id;

    private String alias;
    private Double latitude;
    private Double longitude;
    private String detailLocate;
    private String firstName;
    private String lastName;
    private String phone;
    private String postalCode;
    private String country;
    private String city;
    private String county;
    private String town;

    @ManyToOne
    private UserEntity user;

    // getters and setters
}