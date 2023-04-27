drop database if exists Projectmanagement;
create database Projectmanagement;

use Projectmanagement;
CREATE TABLE user
(
    userName VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

create table project
(
    projectId   int auto_increment,
    projectName varchar(20) not null,
    userName varchar(255) NOT NULL,
    primary key (projectId),
    foreign key (userName) references user (userName)
);

create table task
(
    taskId     int auto_increment,
    taskName   varchar(20) not null,
    taskState  tinyint,
    project_id int         not null,
    primary key (taskId),
    foreign key (project_id) references project (projectid)
);