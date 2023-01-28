-- insert RFID tags
insert into rfid_tag(tag_name, tag_number, customer_id, vehicle_id)
values ('tag1', '1',
        (select id from customer where customer_name = 'Smirnova Olga'),
        (select id from vehicle where vehicle_name = 'Lada XRAY'));
insert into rfid_tag(tag_name, tag_number, customer_id, vehicle_id)
values ('tag2', '2',
        (select id from customer where customer_name = 'Smith Kevin'),
        (select id from vehicle where vehicle_name = 'Ford Mandel'));
insert into rfid_tag(tag_name, tag_number, customer_id, vehicle_id)
values ('tag3', '3',
        (select id from customer where customer_name = 'Carlos Roberto'),
        (select id from vehicle where vehicle_name = 'Land Cruiser'));
