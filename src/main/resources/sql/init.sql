/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  mzhang457
 * Created: Jun 6, 2020
 */


create table student (
    studentId int primary key identity(1,1),
    name varchar(50) not null default ''
);

insert into student values ('mike'),('lee'),('susan')