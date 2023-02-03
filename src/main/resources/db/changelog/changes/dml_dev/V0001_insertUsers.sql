-- insert roles
insert into roles (role_name)
values ('ROLE_ADMIN');
insert into roles (role_name)
values ('ROLE_CUSTOMER');

-- insert users
insert into users(name, username, user_password, role_id)
values ('Smirnova Olga', 'smirnova',
        '{bcrypt}$2a$10$5Xev38uWrLQPi06hSSI7ye9caE4O1rMHbLOW4p2Vp1JU.oDWVBOt.',
        (select id from roles where role_name = 'ROLE_CUSTOMER'));

insert into users(name, username, user_password, role_id)
values ('Smith Kevin', 'kevin',
        '{bcrypt}$2a$10$5Xev38uWrLQPi06hSSI7ye9caE4O1rMHbLOW4p2Vp1JU.oDWVBOt.',
        (select id from roles where role_name = 'ROLE_CUSTOMER'));

insert into users(name, username, user_password, role_id)
values ('Carlos Roberto', 'roberto',
        '{bcrypt}$2a$10$5Xev38uWrLQPi06hSSI7ye9caE4O1rMHbLOW4p2Vp1JU.oDWVBOt.',
        (select id from roles where role_name = 'ROLE_CUSTOMER'));

insert into users(name, username, user_password, role_id)
values ('Admin', 'admin',
        '{bcrypt}$2a$10$5Xev38uWrLQPi06hSSI7ye9caE4O1rMHbLOW4p2Vp1JU.oDWVBOt.',
        (select id from roles where role_name = 'ROLE_ADMIN'));