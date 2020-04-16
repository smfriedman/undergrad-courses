create database coffee;
use coffee;


mysql> create table coffees(
    -> id int unsigned not null auto_increment,
    -> name varchar(50) not null,
    -> primary key(id)
    -> )engine = INNODB DEFAULT character SET = utf8 COLLATE = utf8_general_ci;


mysql> create table ratings(
    -> coffeeId int unsigned not null,
    -> user varchar(50) not null,
    -> rating Decimal(1,1),
    -> comment tinytext,
    -> primary key (coffeeId, user),
    -> foreign key (coffeeId) references coffees (id)
    -> ) engine = INNODB DEFAULT character SET = utf8 COLLATE = utf8_general_ci;

