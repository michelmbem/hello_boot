alter session set "_ORACLE_SCRIPT"=true;

create user testuser identified by testpwd;
grant all privileges to testuser;

connect testuser/testpwd@//localhost:1521/free;

create table dummy
(
    id number(10) not null primary key,
    name varchar2(50) not null check (length(trim(name)) > 0),
    description varchar2(200)
);

create table dummy_item
(
    id number(10) not null primary key,
    name varchar2(50) not null check (length(trim(name)) > 0),
    price number(30, 10) not null check (price >= 0),
    parent_id number(10) not null references dummy on delete cascade
);

create unique index ix_dummy_name on dummy (name);

create unique index ix_dummy_item_parent_name on dummy_item (parent_id, name);

create sequence seq_dummy_id minvalue 1 increment by 1 nocycle;

create sequence seq_dummy_item_id minvalue 1 increment by 1 nocycle;

create or replace trigger trg_dummy_id before insert on dummy
    for each row when (new.id is null or new.id <= 0)
begin
    select seq_dummy_id.NextVal into :new.id
    from dual;
end;
/

alter trigger trg_dummy_id enable;

create or replace trigger trg_dummy_item_id before insert on dummy_item
    for each row when (new.id is null or new.id <= 0)
begin
    select seq_dummy_item_id.NextVal into :new.id
    from dual;
end;
/

alter trigger trg_dummy_item_id enable;
