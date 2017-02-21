use metabolic_app_db;
insert into clinician (username, full_name, password_hash, GMC_no, practice_no) values ('kiran','Kiran Gopinthan','5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8','100','200');
insert into patient (username, first_name, last_name, password_hash, DoB, NHS_no, clinician_id) values ('kiran_patient', 'Kiranp', 'Gopip','ahshshs','1998-05-12', '1010', 1);