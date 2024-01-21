create table
    auth_users (
        is_email_verified boolean not null,
        created_at TIMESTAMP not null,
        deleted_at TIMESTAMP,
        updated_at TIMESTAMP not null,
        created_by uuid,
        deleted_by uuid,
        id uuid not null,
        updated_by uuid,
        email varchar(64) not null unique,
        password varchar(64) not null,
        username varchar(64) not null unique,
        primary key (id)
    );

create table
    codes (
        code varchar(6) not null unique,
        created_at TIMESTAMP not null,
        deleted_at TIMESTAMP,
        updated_at TIMESTAMP not null,
        created_by uuid,
        deleted_by uuid,
        id uuid not null,
        updated_by uuid,
        user_id uuid not null,
        primary key (id)
    );

create table
    roles (
        created_at TIMESTAMP not null,
        deleted_at TIMESTAMP,
        updated_at TIMESTAMP not null,
        created_by uuid,
        deleted_by uuid,
        id uuid not null,
        updated_by uuid,
        name varchar(20) unique check (name in ('USER', 'ADMIN')),
        primary key (id)
    );

create table
    user_roles (
        role_id uuid not null,
        user_id uuid not null,
        primary key (role_id, user_id)
    );

alter table if exists auth_users
add
    constraint FKmk6dx14sdb8j2g1wwsluhy55k foreign key (created_by) references auth_users;

alter table if exists auth_users
add
    constraint FK4338a71g8nlkyed4c1k52s7ad foreign key (deleted_by) references auth_users;

alter table if exists auth_users
add
    constraint FKctkxylsk7kvumxwnmt093dkig foreign key (updated_by) references auth_users;

alter table if exists codes
add
    constraint FKivu9bu3s1xdoc1bxx7da0g138 foreign key (created_by) references auth_users;

alter table if exists codes
add
    constraint FKejfpvtvkah8j7ak023pwmuko7 foreign key (deleted_by) references auth_users;

alter table if exists codes
add
    constraint FKasbii8e4avgsflwmlqvpkvxgj foreign key (updated_by) references auth_users;

alter table if exists codes
add
    constraint FKtaduaggc6hpbsr5frcupsygjc foreign key (user_id) references auth_users;

alter table if exists roles
add
    constraint FK30fwrfb2sw4h50vihggtqv5jc foreign key (created_by) references auth_users;

alter table if exists roles
add
    constraint FK5k6g4na0d1qstyvcdxufaq8ii foreign key (deleted_by) references auth_users;

alter table if exists roles
add
    constraint FKcdyrf94ik348gxjns86cqstiw foreign key (updated_by) references auth_users;

alter table if exists user_roles
add
    constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles;

alter table if exists user_roles
add
    constraint FK333amu2tr91i761renbrkwxgk foreign key (user_id) references auth_users;