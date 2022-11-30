create table if not exists Users
(
    User_Id    Serial        not null primary key,
    Full_Name  Varchar(256)  not null,
    Gender     Smallint      not null,
    Birth_Date Date          not null,
    Address    Varchar(1024) not null,
    Oms_Number Decimal(16)   not null unique
);
