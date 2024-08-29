package com.sixplus.server.api.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "tb_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public static AddressEntity createRandom(UserEntity user) {
        Random random = new Random();
        AddressEntity address = new AddressEntity();
        address.id = UUID.randomUUID();
        address.alias = "Home";
        address.latitude = random.nextDouble() * 180 - 90;
        address.longitude = random.nextDouble() * 360 - 180;
        address.detailLocate = "Detail Location " + random.nextInt(100);
        address.firstName = "FirstName" + random.nextInt(100);
        address.lastName = "LastName" + random.nextInt(100);
        address.phone = "010-" + String.format("%04d", random.nextInt(10000)) + "-" + String.format("%04d", random.nextInt(10000));
        address.postalCode = "PostalCode" + random.nextInt(10000);
        address.country = "Country" + random.nextInt(100);
        address.city = "City" + random.nextInt(100);
        address.county = "County" + random.nextInt(100);
        address.town = "Town" + random.nextInt(100);
        address.user = user;
        return address;
    }
}
