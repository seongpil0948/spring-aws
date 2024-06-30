package com.sixplus.server.api.user.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_ADDRESS")
public class TB_ADDRESS {
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
    @JoinColumn(name = "user_id")
    private TB_USER user;

    // getters and setters
}