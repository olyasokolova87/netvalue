-- insert charge connectors
insert into charge_connectors(connector_number, meter_value, charge_point_id)
values (1, 0, (select id from charge_points where serial_number = 'number1'));
insert into charge_connectors(connector_number, meter_value, charge_point_id)
values (2, 0, (select id from charge_points where serial_number = 'number1'));

insert into charge_connectors(connector_number, meter_value, charge_point_id)
values (1, 0, (select id from charge_points where serial_number = 'number2'));
insert into charge_connectors(connector_number, meter_value, charge_point_id)
values (2, 0, (select id from charge_points where serial_number = 'number2'));
