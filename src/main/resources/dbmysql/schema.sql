DROP DATABASE IF EXISTS project_db;

CREATE DATABASE project_DB;
USE project_DB;

CREATE TABLE user
(
    userName VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project (id)
)

CREATE TABLE project
(
    id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (task_id) REFERENCES task (id)
)

CREATE TABLE task
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
)
