CREATE DATABASE mybatis;
USE mybatis;

CREATE TABLE location (
    id INT PRIMARY KEY,
    location VARCHAR(255)
);

INSERT INTO location (id, location) VALUES
(1, 'Location A'),
(2, 'Location B'),
(3, 'Location C');

CREATE TABLE user (
    id INT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

INSERT INTO user (id, username, password) VALUES
(1, 'user1', 'password1'),
(2, 'user2', 'password2'),
(3, 'user3', 'password3');
