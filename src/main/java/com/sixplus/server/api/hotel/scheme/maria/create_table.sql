USE `six_plus`;


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