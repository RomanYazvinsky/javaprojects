use hotel;
-- clients
insert into clients values (0, 'Roma1');
insert into clients values (0, 'Roma2');
insert into clients values (0, 'Roma3');
insert into clients values (0, 'Roma4');
insert into clients values (0, 'Roma5');
insert into clients values (0, 'Roma6');
insert into clients values (0, 'Roma7');
insert into clients values (0, 'Roma8');
insert into clients values (0, 'Roma9');
insert into clients values (0, 'Roma10');
insert into clients values (0, 'Roma11');
insert into clients values (0, 'Roma12');
-- rooms
insert into rooms values (0, 1, 2, 1, 'ready', 120);
insert into rooms values (0, 2, 6, 3, 'ready', 103);
insert into rooms values (0, 3, 44, 3, 'ready', 101);
insert into rooms values (0, 4, 1, 5, 'service', 10);
insert into rooms values (0, 5, 22, 3, 'ready', 130);
insert into rooms values (0, 6, 6, 4, 'ready', 210);
insert into rooms values (0, 7, 6, 2, 'ready', 410);
insert into rooms values (0, 8, 1, 1, 'ready', 210);
insert into rooms values (0, 9, 2, 3, 'ready', 106);
insert into rooms values (0, 10, 4, 1, 'service', 410);
insert into rooms values (0, 11, 3, 3, 'ready', 10);
insert into rooms values (0, 12, 5, 4, 'ready', 11);
-- services
insert into services values (0, 'bar', 3, null);
insert into services values (0, 'bar+girls', 4, '2018-04-03');
insert into services values (0, 'bar+air', 10, '2018-02-01');
insert into services values (0, 'detructions', 99, '2017-07-21');
insert into services values (0, 'bar+bar', 6, '2018-01-01');
insert into services values (0, 'bar+bar+bar', 7, '2018-02-06');
insert into services values (0, 'pizza', 1, null);
insert into services values (0, 'real pizza', 8, null);
insert into services values (0, 'hot water', 15, current_date());
-- orders
insert into orders values (0, 1, 1, current_date(), '2018-04-03', 9);
insert into orders values (0, 2, 1, '2017-07-21', null, 5);
insert into orders values (0, 3, 6, '2018-02-06', null, null);
insert into orders values (0, 4, 3, '2018-02-01', '2018-02-02', null);
insert into orders values (0, 5, 3, current_date(), null, null);
