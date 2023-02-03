create table roles
(
    id        identity    not null primary key,
    role_name varchar(15) not null unique
);
comment
    on table roles is 'User roles';
comment
    on column roles.id is 'User role ID';
comment
    on column roles.role_name is 'Unique user role name';

create table users
(
    id            identity     not null primary key,
    role_id       int          not null references roles (id),
    name          varchar(300) not null,
    username      varchar(40)  not null unique,
    user_password varchar(72)  not null
);
comment
    on table users is 'Users';
comment
    on column users.id is 'User ID';
comment
    on column users.role_id is 'ID of user role';
comment
    on column users.name is 'User name';
comment
    on column users.username is 'Unique user login';
comment
    on column users.user_password is 'encrypted token';

create table vehicles
(
    id                 identity     not null primary key,
    vehicle_name       varchar(255) not null,
    registration_plate varchar(15) not null unique
);
comment
    on table vehicles is 'vehicles that need to be charge';
comment
    on column vehicles.id is 'vehicle ID';
comment
    on column vehicles.vehicle_name is 'vehicle name';
comment
    on column vehicles.registration_plate is 'vehicle registration plate';

create table rfid_tags
(
    id          identity     not null primary key,
    tag_name    varchar(255) not null,
    tag_number  varchar(40)  not null unique,
    customer_id int references users (id)
);
comment
    on table rfid_tags is 'RFID tags that uses during the charge session of a vehicles';
comment
    on column rfid_tags.id is 'RFID tag ID';
comment
    on column rfid_tags.tag_name is 'RFID tag name';
comment
    on column rfid_tags.tag_number is 'RFID tag global unique number';
comment
    on column rfid_tags.customer_id is 'Customer that owned RFID tag';

create table charge_points
(
    id            identity     not null primary key,
    serial_number varchar(100) not null unique,
    point_name    varchar(255) not null
);
comment
    on table charge_points is 'Points where vehicles is charging';
comment
    on column charge_points.id is 'Charge point ID';
comment
    on column charge_points.point_name is 'Charge point name';
comment
    on column charge_points.serial_number is 'Charge point serial number';

create table charge_connectors
(
    id               identity not null primary key,
    connector_number int      not null,
    charge_point_id  int references charge_points (id),
    meter_value      int      not null
);
create unique index UIDX_Connectors_Number_Point on charge_connectors (charge_point_id, connector_number);

comment
    on table charge_connectors is 'Connectors in charge point';
comment
    on column charge_connectors.id is 'Charge connector ID';
comment
    on column charge_connectors.charge_point_id is 'Charge point that owns the connector';
comment
    on column charge_connectors.connector_number is 'Charge connector number in point';
comment
    on column charge_connectors.meter_value is 'The meter value of charges kWh of energy';

create table charging_sessions
(
    id                  identity  not null primary key,
    start_time          timestamp not null,
    end_time            timestamp,
    charge_connector_id int references charge_connectors (id),
    rfid_tag_id         int references rfid_tags (id),
    vehicle_id          int references vehicles (id),
    error_message       varchar(500)
);
comment
    on table charging_sessions is 'Sessions of charging vehicles';
comment
    on column charging_sessions.id is 'Session ID';
comment
    on column charging_sessions.start_time is 'Date and time when session starts';
comment
    on column charging_sessions.end_time is 'Date and time when session ends';
comment
    on column charging_sessions.charge_connector_id is 'Connection that used during the session';
comment
    on column charging_sessions.rfid_tag_id is 'RFID tag that used during the session';
comment
    on column charging_sessions.vehicle_id is 'ID of charged vehicle';
comment
    on column charging_sessions.error_message is 'Error message when charge session can not complete';

