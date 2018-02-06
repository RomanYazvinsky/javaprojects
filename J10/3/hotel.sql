drop database if exists hotel;
CREATE DATABASE hotel;
USE hotel;
CREATE TABLE clients (
    id int not null primary key auto_increment,
    name varchar(50) not null
);
CREATE TABLE rooms (
    id int not null primary key auto_increment,
    number int  unique not null,
    capacity tinyint not null,
    star tinyint not null,
    status enum ('ready', 'service') not null,
    price int not null
);
create table services(
	id int not null primary key auto_increment,
    name varchar(150) not null,
    price int not null,
    date date
);
create table orders(
	id int not null primary key auto_increment,
    client_id int not null,
    room_id int not null,
    order_from date not null,
    order_to date,
    service_id int,
    FOREIGN KEY (client_id)
        REFERENCES clients (id),
	FOREIGN KEY (room_id)
        REFERENCES rooms (id),
	FOREIGN KEY (service_id)
        REFERENCES services (id)
);
delimiter //
create procedure check_positive (in number int)  
begin 	
	if number <=0 then
		SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'bad number';
	end if;
end //
create trigger trig_check_room before insert on rooms
	for each row 
    begin 
		call check_positive(new.number);
		call check_positive(new.capacity);
		call check_positive(new.star);
		call check_positive(new.price);
	end //
create trigger trig_check_service before insert on services
	for each row 
    begin 
		call check_positive(new.price);
	end //
delimiter ;