/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  mzhang457
 * Created: Jun 6, 2020
 */
create database TESTMASTER;
use TESTMASTER;
create table table create (
	clientid int primary key identity(1,1)
	,clientName nvarchar(50) unique not null default ''
	, clientDBHost nvarchar(50) not null default ''
	, clientDBPort nvarchar(50) not null default ''
	, clientDBUserName nvarchar(50) not null default ''
	, clientDBPassword nvarchar(50) not null default ''
);

create database client_1;
use client_1;
create table office(
	officeId int primary key identity(1,1)
	, officeName nvarchar(50) unique not null default ''
	, officeStreetAddress nvarchar(128) not null default ''
	, officeLat decimal(10,6) not null default 0.00
	, officeLon decimal(10,6) not null default 0.00
	
);

create database client_2;
use client_2;
create table office(
	officeId int primary key identity(1,1)
	, officeName nvarchar(50) unique not null default ''
	, officeStreetAddress nvarchar(128) not null default ''
	, officeLat decimal(10,6) not null default 0.00
	, officeLon decimal(10,6) not null default 0.00
)


