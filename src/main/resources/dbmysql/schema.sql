drop database if exists Projectmanagement;
create database Projectmanagement;

use Projectmanagement;
CREATE TABLE `users` (
                         `user_id` int(11) NOT NULL AUTO_INCREMENT,
                         `username` varchar(45) NOT NULL,
                         `password` varchar(64) NOT NULL,
                         `role` varchar(45) NOT NULL,
                         `enabled` tinyint(4) DEFAULT NULL,
                         PRIMARY KEY (`user_id`)
);

create table project
(
    projectId   int auto_increment,
    projectName varchar(20) not null,
    startDate varchar(10),
    deadline varchar(10),
    user_id int(11) ,
    primary key (projectId),
    foreign key (user_id) references `users` (`user_id`)
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
    projectId int         ,
    primary key (taskId),
    foreign key (projectId) references project (projectid)
);


create table assignment
(
    assignmetId int auto_increment,
    taskId int,
    userId int,
    primary key (assignmentId),
    foreign key (taskId) references task (taskId),
    foreign key (userId) references `users` (`user_id`)
);