use metabolic_app_db;

create table clinician(
  id integer not null unique auto_increment,
  username varchar(20) not null,
  full_name varchar(30) not null,
  password_hash longtext not null,
  GMC_no varchar(30) not null,
  practice_no varchar(30) not null,
  primary key(id)
);

create table patient(id integer not null unique auto_increment,
                     username varchar(20) not null,
                     first_name varchar(20) not null,
                     last_name varchar(20) not null,
                     password_hash varchar(30) not null,
                     DoB date not null,
                     NHS_no bigint not null,
                     clinician_id integer,
                     date_created datetime not null,
  primary key(id)
);

create table inbox(
  id integer not null unique auto_increment,
  clinician_id integer not null,
  patient_id integer not null,
  entry_type enum('request', 'submission'),
  image blob, date_created datetime,
  primary key(id)
);

create table test_result(
  id integer not null unique auto_increment,
  test_type enum('glucose_test','ECG', 'BMI', 'HbA1c', 'liver'),
  glucose_level integer, ECG integer, BMI integer, HbA1c integer, liver integer,
  clinician_id integer not null,
  patient_id integer not null,
  next_test date,
  message longtext,
  date_created datetime,
  primary key(id)
);

create trigger set_patient_account_createdate before insert on patient for each row set new.date_created = now();
create trigger set_test_result_createdate before insert on test_result for each row set new.date_created = now();
create trigger set_inbox_createdate before insert on inbox for each row set new.date_created = now();

delimiter $$
create trigger set_default_next_date
before insert on test_result
for each row
  begin
    if new.next_test is null then
      case new.test_type
        when 'glucose_test' then set new.next_test = now() + interval 2 month;
        when 'ECG' then set new.next_test = now() + interval 1 year;
        when 'BMI' then set new.next_test = now() + interval 3 week;
        when 'HbA1c' then set new.next_test = now() + interval 4 month;
      else set new.next_test = now() + interval 2 year;
      end case;
    end if;
  end;
$$
delimiter ;


                    
                    
