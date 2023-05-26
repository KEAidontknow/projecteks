drop database if exists Projectmanagement;
create database Projectmanagement;

use Projectmanagement;
CREATE TABLE `users` (
                         `username` varchar(45) NOT NULL unique,
                         `password` varchar(64) NOT NULL,
                         `role` varchar(45) NOT NULL,
                         `enabled` tinyint(4) DEFAULT NULL,
                         PRIMARY KEY (`username`)
);

create table project
(
    projectId   int auto_increment,
    projectName varchar(20) not null,
    startDate varchar(10),
    deadline varchar(10),
    username varchar(45) ,
    primary key (projectId),
    foreign key (username) references `users` (`username`)
);

create table task
(
    taskId     int auto_increment,
    taskName   varchar(20) not null,
    taskState  tinyint not null ,
    creationDate varchar(10) not null ,
    startDate   varchar(10) not null ,
    deadline    varchar(10) not null ,
    timeEstimate    int not null ,
    taskStar tinyint not null,
    projectId int         ,
    primary key (taskId),
    foreign key (projectId) references project (projectId)
);

create table assign
(
    assignId 	int auto_increment,
    taskId		int,
    username	varchar(45),
    startDate 	varchar(10),
    endDate 	varchar(10),
    primary key (assignId),
    foreign key (taskId) references task (taskId),
    foreign key (username) references `users` (`username`),
    unique index (taskId, username)

);
