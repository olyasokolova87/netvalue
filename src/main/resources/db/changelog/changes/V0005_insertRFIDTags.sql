-- insert RFID tags
insert into rfid_tag(tag_name, tag_number, customer_id, vehicle_id)
values ('tag1', '9382134b-46f1-437f-b581-49c533a49661',
        (select id from customer where customer_name = 'Smirnova Olga'),
        (select id from vehicle where vehicle_name = 'Lada XRAY'));
insert into rfid_tag(tag_name, tag_number, customer_id, vehicle_id)
values ('tag2', '8d88c18d-18b4-464c-ace4-f69c8e7af3ab',
        (select id from customer where customer_name = 'Smith Kevin'),
        (select id from vehicle where vehicle_name = 'Ford Mandel'));
insert into rfid_tag(tag_name, tag_number, customer_id, vehicle_id)
values ('tag3', '86a06f1e-1009-487d-ad31-bee7553c58de',
        (select id from customer where customer_name = 'Carlos Roberto'),
        (select id from vehicle where vehicle_name = 'Land Cruiser'));