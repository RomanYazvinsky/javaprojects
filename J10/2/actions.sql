-- 1
select model, speed, hd from pc where price<500;
-- 2
select distinct maker from product right join printer using (model); 
-- 3
select model, ram, screen from laptop where price > 1000;
-- 4
select * from printer where color ='y';
-- 5
select model, speed, hd from pc where (cd = '12x' or cd = '24x') and price < 600;
-- 6
select distinct maker, speed from laptop left join product using (model) where hd >= 10;
-- 7
(select model, price from product right join laptop using (model) where maker = 'B') union
(select model, price from product right join pc using (model) where maker = 'B') union
(select model, price from product right join printer using (model) where maker = 'B');
-- 8
select distinct maker from (select * from product where type = 'PC') list left join (select * from product where type = 'Laptop')pr using (maker) where pr.maker is null;
-- 9
select distinct maker from product right join pc using (model) where speed >= 450;
-- 10
select model, price from printer where price = (select max(price) from printer);
-- 11
select avg(speed) from pc;
-- 12
select avg(speed) from laptop where price >1000;
-- 13
select avg(speed) from pc left join product using (model) where maker = 'A';
-- 14
select speed, avg(price) from pc spd group by (speed);
-- 15
select hd from pc group by (hd) having count(model) >=2;
-- 16
select distinct t1.model, t2.model, t1.speed, t1.ram from pc t1, pc t2 where t1.speed = t2.speed and t1.ram = t2.ram and t1.model>t2.model;
-- 17
select type, model, speed from laptop left join product using (model) where speed < (select min(speed) from pc);
-- 18
select maker, price from printer left join product using (model)  where price = (select min(price) from printer where color = 'y') and color = 'y';
-- 19
select maker, avg(screen) from product right join laptop using (model) group by maker;
-- 20
select maker, count(model) from product where type = 'PC' group by maker having count(model) >= 3;
-- 21
select maker, max(price) from product right join pc using (model) group by maker;
-- 22
select distinct speed, avg(price) from pc where speed > 600 group by speed;
-- 23
select distinct maker from product right join laptop using (model) where speed > 750 and maker in (select maker from product right join pc using (model) where speed > 750) and maker not in (select maker from product right join pc using (model) where speed <= 750) and maker not in (select maker from product right join laptop using (model) where speed <= 750);
-- 24
select model from ((select model, price from pc ) union (select model, price from laptop ) union (select model, price from printer)) tbl where price = (select max(price) from ((select price from pc) union (select price from laptop) union (select price from printer)) prc);
-- 25
select distinct maker from product right join (select * from pc where ram = (select min(ram) from pc)) pctbl using (model) where speed = (select max(speed) from (select * from pc where ram = (select min(ram) from pc))tb1) and maker in (select distinct maker from product where type = 'Printer');