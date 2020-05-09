# Use these queries to create the tables in your database

CREATE TABLE client(
                       ID VARCHAR(255) PRIMARY KEY NOT NULL ,
                       Name VARCHAR(255),
                       Address VARCHAR(255),
                       Reference_person VARCHAR(255),
                       Email VARCHAR(255),
                       Password VARCHAR(255),
                       created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE user_actions(
                             id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                             user_id VARCHAR(255) NOT NULL,
                             action ENUM('ADD_CONTAINER', 'CONTAINER_HISTORY', 'FIND_JOURNEY') NOT NULL,
                             started TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             ended TIMESTAMP NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES client(ID)
);

create table containers
(
    id VARCHAR(40) PRIMARY KEY NOT NULL,
    position VARCHAR(255) NOT NULL ,
    temperature NUMERIC not null,
    humidity NUMERIC not null,
    pressure NUMERIC not null,
    in_journey BOOLEAN DEFAULT FALSE,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE journeys(
                         id VARCHAR(40) PRIMARY KEY NOT NULL,
                         origin VARCHAR(255) NOT NULL,
                         destination VARCHAR (255) NOT NULL,
                         container VARCHAR (255) NOT NULL,
                         description VARCHAR (255) NOT NULL,
                         company VARCHAR (255) NOT NULL,
                         ongoing BOOLEAN DEFAULT FALSE,
                         position VARCHAR(255) NOT NULL,
                         created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    #FOREIGN KEY (container) REFERENCES containers(id)
    # FOREIGN KEY (position) REFERENCES containers(position)
);

CREATE TABLE container_status(
                                 id VARCHAR(40) PRIMARY KEY NOT NULL,
                                 temperature NUMERIC not null,
                                 humidity NUMERIC not null,
                                 pressure NUMERIC not null,
                                 journey_id VARCHAR(40) NOT NULL,
                                 container_id VARCHAR(40) NOT NULL,
                                 position VARCHAR(255) NOT NULL ,
                                 created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (position) REFERENCES containers(position),
                                 FOREIGN KEY (container_id) REFERENCES containers(id),
                                 FOREIGN KEY (journey_id) REFERENCES journeys(id)
);