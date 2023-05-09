drop database if exists Projectmanagement;
create database Projectmanagement;

use Projectmanagement;
CREATE TABLE user
(
    userId int auto_increment,
    userName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    primary key (userName)
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
    taskId       int auto_increment,
    taskName     varchar(20) not null,
    taskState    tinyint     not null,
    creationDate varchar(30) not null,
    startDate    varchar(10) not null,
    deadline     varchar(10) not null,
    timeEstimate int         not null,
    project_id   int         not null,
    primary key (taskId),
    foreign key (project_id) references project (projectid)

);

        CREATE TABLE userproject
        (
        userId INT,
        projectId INT,
        PRIMARY KEY (userId, projectId),
        FOREIGN KEY (userId) REFERENCES user (userId),
        FOREIGN KEY (projectId) REFERENCES project (projectId)




);
