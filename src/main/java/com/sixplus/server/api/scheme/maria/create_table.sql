USE `six_plus`;

DROP TABLE IF EXISTS TB_USER;
CREATE TABLE IF NOT EXISTS  TB_USER (
     id VARCHAR(255) NOT NULL,
     userName VARCHAR(255),
     displayName VARCHAR(255),
     email VARCHAR(255),
     emailVerified BOOLEAN,
     phone VARCHAR(255),
     defaultAddressID BINARY(16),
     prefersNotifications BOOLEAN,
     seasonalPhoto VARCHAR(255),
     avatar VARCHAR(255),
     isFavorite BOOLEAN,
     membership VARCHAR(255),
     PRIMARY KEY (id)
);

DROP TABLE IF EXISTS TB_QUEUE;
CREATE TABLE IF NOT EXISTS TB_QUEUE (
      id VARCHAR(255) NOT NULL,
      password VARCHAR(255),
      gender VARCHAR(255),
      user_id VARCHAR(255) NOT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_tb_queue_user
          FOREIGN KEY (user_id) REFERENCES TB_USER(id)
              ON DELETE CASCADE
);

DROP TABLE IF EXISTS TB_ADDRESS;
CREATE TABLE IF NOT EXISTS TB_ADDRESS (
    id BINARY(16) NOT NULL,
    alias VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    detailLocate VARCHAR(255),
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    phone VARCHAR(255),
    postalCode VARCHAR(255),
    country VARCHAR(255),
    city VARCHAR(255),
    county VARCHAR(255),
    town VARCHAR(255),
    user_id VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_tb_address_user
        FOREIGN KEY (user_id) REFERENCES TB_USER(id)
            ON DELETE CASCADE
);


DROP TABLE IF EXISTS `hotels`;
CREATE TABLE IF NOT EXISTS `hotels` (
  `hotel_id` BIGINT NOT NULL AUTO_INCREMENT,
  `status` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(300) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `phone_number` VARCHAR(100) NOT NULL,
  `room_count` INT NOT NULL DEFAULT 0,
  `created_by` VARCHAR(45) NULL,
  `created_at` DATETIME NULL,
  `modified_by` VARCHAR(45) NULL,
  `modified_at` DATETIME NULL,
  PRIMARY KEY (`hotel_id`),
  INDEX `INDEX_NAME_STATUS` (`name`, `status`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `hotel_rooms`;

CREATE TABLE IF NOT EXISTS `hotel_rooms` (
  `hotel_room_id` BIGINT NOT NULL AUTO_INCREMENT,
  `hotels_hotel_id` BIGINT NULL,
  `room_number` VARCHAR(100) NOT NULL,
  `room_type` INT NOT NULL,
  `original_price` DECIMAL(18,2) NOT NULL,
  `created_by` VARCHAR(45) NULL,
  `created_at` DATETIME NULL,
  `modified_by` VARCHAR(45) NULL,
  `modified_at` DATETIME NULL,
  PRIMARY KEY (`hotel_room_id`),
  INDEX `fk_hotel_rooms_hotels_idx` (`hotels_hotel_id`),
  CONSTRAINT `fk_hotel_rooms_hotels`
    FOREIGN KEY (`hotels_hotel_id`)
    REFERENCES `hotels` (`hotel_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;



INSERT INTO TB_USER (
    id, userName, displayName, email, emailVerified, phone, defaultAddressID,
    prefersNotifications, seasonalPhoto, avatar, isFavorite, membership
) VALUES (
     'user1', 'john_doe', 'John Doe', 'john.doe@example.com', TRUE, '123-456-7890',
     UNHEX(REPLACE(UUID(),'-','')), TRUE, 'summer.jpg', 'avatar1.png', TRUE, 'gold'
 );

INSERT INTO TB_QUEUE (
    id, password, gender, user_id
) VALUES (
     'queue1', 'password123', 'male', 'user1'
 );

INSERT INTO TB_ADDRESS (
    id, alias, latitude, longitude, detailLocate, firstName, lastName, phone,
    postalCode, country, city, county, town, user_id
) VALUES (
     UNHEX(REPLACE(UUID(),'-','')), 'Home', 37.7749, -122.4194, '123 Main St',
     'John', 'Doe', '123-456-7890', '94103', 'USA', 'San Francisco', 'San Francisco County',
     'Downtown', 'user1'
 );

INSERT INTO hotels (hotel_id, status, name, address, phone_number, room_count, created_by, created_at, modified_by, modified_at)
values (1, 1, 'Hotel 1', 'Address 1', '123', 10, 'admin', now(), 'admin', now());

insert into hotels (hotel_id, status, name, address, phone_number, room_count, created_by, created_at, modified_by, modified_at)
values (2, 0, 'Hotel 2', 'Address 2', '123', 10, 'admin', now(), 'admin', now());

insert into hotels (hotel_id, status, name, address, phone_number, room_count, created_by, created_at, modified_by, modified_at)
values (3, 2, 'Hotel 3', 'Address 3', '123', 10, 'admin', now(), 'admin', now());

INSERT INTO hotel_rooms (hotels_hotel_id, room_number, room_type, original_price, created_by, created_at, modified_by, modified_at)
VALUES (1, '101', 1, 150.00, 'admin', NOW(), 'admin', NOW());