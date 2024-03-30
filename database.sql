create table organization
(
    id                serial primary key,
    organization_type text unique
);

create table school
(
    id                serial primary key,
    name              text unique,
    phone             text,
    student_open_date text
);

create table school_address
(
    id            serial primary key,
    school_id     int not null unique,
    district_name text,
    address       text,
    town          text,
    zipcode       text,
    latitude      text,
    longitude     text,
    constraint fk_address_school foreign key (school_id) REFERENCES school (id) on delete cascade
);

create table school_details
(
    id                   serial primary key,
    school_id            int not null unique,
    has_pre_kindergarten boolean,
    has_kindergarten     boolean,
    has_grade_1          boolean,
    has_grade_2          boolean,
    has_grade_3          boolean,
    has_grade_4          boolean,
    has_grade_5          boolean,
    has_grade_6          boolean,
    has_grade_7          boolean,
    has_grade_8          boolean,
    has_grade_9          boolean,
    has_grade_10         boolean,
    has_grade_11         boolean,
    has_grade_12         boolean,
    constraint fk_address_school foreign key (school_id) REFERENCES school (id) on delete cascade
);

create table school_organization_mapping
(
    id                serial primary key,
    school_id         int not null unique,
    organization_id   int not null,
    organization_code text,
    constraint school_organization_mapping_school_id foreign key (school_id) REFERENCES school (id) on delete cascade,
    constraint school_organization_mapping_org_id foreign key (organization_id) REFERENCES organization (id) on delete cascade,
    unique (school_id, organization_id)
);