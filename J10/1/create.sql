drop database if exists test;
CREATE DATABASE test;
USE test;
CREATE TABLE product (
    maker VARCHAR(10) NOT NULL,
    model VARCHAR(50) NOT NULL PRIMARY KEY,
    type VARCHAR(50) NOT NULL
);
delimiter //
 CREATE FUNCTION get_type (model1 VARCHAR(50))
		RETURNS VARCHAR(50)
	begin
		RETURN (SELECT type FROM product WHERE model = model1);
	END//
    
create procedure check_type (in tableType varchar(50), in newModel varchar(50))  
begin 	
	if tableType <> (select test.get_type(newModel)) then
		SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'bad model';
	end if;
end //
delimiter ;

CREATE TABLE laptop (
    code INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    speed SMALLINT NOT NULL,
    ram SMALLINT NOT NULL,
    hd REAL NOT NULL,
    price INT,
    screen TINYINT NOT NULL,
    FOREIGN KEY (model)
        REFERENCES product (model)
);
create table pc(
	code int not null auto_increment primary key,
	model varchar(50) not null,
    speed smallint not null,
	ram smallint not null,
	hd real not null,
    cd varchar(10) not null,
    price int,
	foreign key (model) references product(model)
);
CREATE TABLE printer (
    code INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    color CHAR(1) NOT NULL,
    type VARCHAR(10) NOT NULL,
    price INT,
    FOREIGN KEY (model)
        REFERENCES product (model)
);
delimiter //
create trigger trig_check_laptop before insert on laptop
	for each row 
    begin 
		call check_type('Laptop', new.model);
	end //
create trigger trig_check_pc before insert on pc
	for each row 
    begin 
		call check_type('PC', new.model);
	end //
create trigger trig_check_printer before insert on printer
	for each row 
    begin 
		call check_type('Printer', new.model);
	end //
delimiter ;
   