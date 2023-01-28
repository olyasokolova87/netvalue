create table customer
(
    id identity not null primary key,
    customer_name varchar(255) not null
);
comment
on table customer is 'Customer that use charge point';
comment
on column customer.id is 'Customer ID';
comment
on column customer.customer_name is 'Customer name';

create table vehicle
(
    id identity not null primary key,
    vehicle_name       varchar(255) not null,
    registration_plate varchar(15)  not null
);
comment
on table vehicle is 'Vehicle that need to be charge';
comment
on column vehicle.id is 'Vehicle ID';
comment
on column vehicle.vehicle_name is 'Vehicles name';
comment
on column vehicle.registration_plate is 'Vehicles registration plate';

create table rfid_tag
(
    id identity not null primary key,
    tag_name    varchar(255) not null,
    tag_number  varchar(40)  not null unique,
    customer_id int references customer (id),
    vehicle_id  int references vehicle (id)
);
comment
on table rfid_tag is 'RFID tag that uses during the charge session of a vehicle';
comment
on column rfid_tag.id is 'RFID tag ID';
comment
on column rfid_tag.tag_name is 'RFID tag name';
comment
on column rfid_tag.tag_number is 'RFID tag global unique number';
comment
on column rfid_tag.customer_id is 'Customer that owned RFID tag';
comment
on column rfid_tag.vehicle_id is 'Linked vehicle to RFID tag';

create table charge_point
(
    id identity not null primary key,
    serial_number varchar(100) not null unique,
    point_name    varchar(255) not null
);
comment
on table charge_point is 'Point where vehicle is charging';
comment
on column charge_point.id is 'Charge point ID';
comment
on column charge_point.point_name is 'Charge point name';
comment
on column charge_point.serial_number is 'Charge point serial number';

create table charge_connector
(
    id identity not null primary key,
    connector_number varchar(100) not null,
    charge_point_id  int references charge_point (id),
    meter_value      int          not null
);
comment
on table charge_connector is 'Connector in charge point';
comment
on column charge_connector.id is 'Charge connector ID';
comment
on column charge_connector.charge_point_id is 'Charge point that owns the connector';
comment
on column charge_connector.connector_number is 'Charge connector number in point';
comment
on column charge_connector.meter_value is 'The meter value of charges kWh of energy';

create table charging_session
(
    id identity not null primary key,
    start_time          timestamp not null,
    end_time            timestamp,
    charge_connector_id int references charge_connector (id),
    rfid_tag_id         int references rfid_tag (id),
    error_message       varchar(500)
);
comment
on table charging_session is 'Session of charge vehicle';
comment
on column charging_session.id is 'Session ID';
comment
on column charging_session.start_time is 'Date and time when session starts';
comment
on column charging_session.end_time is 'Date and time when session ends';
comment
on column charging_session.charge_connector_id is 'Connection that used during the session';
comment
on column charging_session.rfid_tag_id is 'RFID tag that used during the session';
comment
on column charging_session.error_message is 'Error message when charge session can not complete';

