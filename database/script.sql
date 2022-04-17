CREATE TABLE `location`
(
    `location_id`       bigint NOT NULL AUTO_INCREMENT,
    `location_address`  varchar(255) DEFAULT NULL,
    `location_capacity` int    NOT NULL,
    `location_city`     varchar(255) DEFAULT NULL,
    `location_name`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`location_id`)
);

CREATE TABLE `event`
(
    `event_id`          bigint NOT NULL AUTO_INCREMENT,
    `artist`            varchar(255) DEFAULT NULL,
    `event_category`    varchar(255) DEFAULT NULL,
    `event_city`        varchar(255) DEFAULT NULL,
    `event_date`        datetime(6) DEFAULT NULL,
    `event_description` varchar(255) DEFAULT NULL,
    `event_flyer`       varchar(255) DEFAULT NULL,
    `event_name`        varchar(255) DEFAULT NULL,
    `event_organizer`   varchar(255) DEFAULT NULL,
    `event_state`       varchar(255) DEFAULT NULL,
    `event_ticketsurl`  varchar(255) DEFAULT NULL,
    `event_location`    bigint       DEFAULT NULL,
    PRIMARY KEY (`event_id`),
    KEY                 `FK5t6fg2mkll7epg0hqymvu6nkc` (`event_location`),
    CONSTRAINT `FK5t6fg2mkll7epg0hqymvu6nkc` FOREIGN KEY (`event_location`) REFERENCES `location` (`location_id`)
);


INSERT INTO location (location_address, location_capacity, location_name, location_city)
VALUES ('Dg. 61c #26-36', 14000, 'Movistar Arena', 'Bogotá');

INSERT INTO location (location_address, location_capacity, location_name, location_city)
VALUES ('Cl. 63 #60-80', 16000, 'Salitre Mágico', 'Bogotá');