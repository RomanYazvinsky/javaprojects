-- product
insert into product values ('Integral', 'P4450', 'Printer');
insert into product values ('Integral', 'P4451', 'Printer');
insert into product values ('Integral', 'P4455', 'Printer');
insert into product values ('Integral', 'P6200', 'Printer');
insert into product values ('Integral', 'P4450X', 'Printer');
insert into product values ('Roma', 'P10', 'Printer');
insert into product values ('Roma', 'P20', 'Printer');
insert into product values ('Integral', 'C3PO', 'PC');
insert into product values ('Integral', 'R2D2', 'PC');
insert into product values ('Integral', 'K2SO', 'PC');
insert into product values ('Cosine', 'H20', 'PC');
insert into product values ('Cosine', 'C2H5OH', 'PC');
insert into product values ('Integral', 'Potato1', 'Laptop');
insert into product values ('Integral', 'Potato2', 'Laptop');
insert into product values ('Integral', 'Potato3', 'Laptop');
insert into product values ('Integral', 'Tomato', 'Laptop');
insert into product values ('GoodINC', 'Diablo', 'Laptop');
insert into product values ('GoodINC', 'Mefisto', 'Laptop');
-- laptop
insert into laptop values (0, 'Potato1', 1234, 4096, 80, null, 21);
insert into laptop values (0, 'Potato2', 2345, 4096, 720, 5000, 24);
insert into laptop values (0, 'Potato3', 3456, 16000, 720, 1505, 16);
insert into laptop values (0, 'Mefisto', 4, 4444, 444, 4444, 44);
insert into laptop values (0, 'Tomato', 1001, 4004, 707, 5, 22);
insert into laptop values (0, 'Diablo', 666, 666, 666, 666666, 66);
-- pc
insert into pc values (0, 'C3PO', 1234, 4096, 80,'4x', 40);
insert into pc values (0, 'R2D2', 2345, 4096, 720,'4x4', 501);
insert into pc values (0, 'K2SO', 1234, 4096, 720, 'bluray', 1555);
insert into pc values (0, 'H20', 134, 512, 720, '12x', 432);
insert into pc values (0, 'C2H5OH', 123, 4096, 720,'24x', 777);
-- printer
insert into printer values (0, 'P4450','n', 'Laser', 40);
insert into printer values (0, 'P4451','n', 'Laser', null);
insert into printer values (0, 'P4455','y', 'Laser', 80);
insert into printer values (0, 'P6200','y', 'Jet', 100);
insert into printer values (0, 'P4450X','n', 'Laser', null);
insert into printer values (0, 'P10','y', 'Jet', 80);
insert into printer values (0, 'P20','y', 'Jet', 100);