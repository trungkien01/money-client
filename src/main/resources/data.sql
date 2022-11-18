CREATE TABLE IF NOT EXISTS bank
(
    id bigint NOT NULL,
    name VARCHAR(255),
    CONSTRAINT bank_pkey PRIMARY KEY (id)
);

insert into bank(id, name) VALUES(1,'Vietcombank');
insert into bank(id, name) VALUES(2,'Techcombank');
insert into bank(id, name) VALUES(3,'MBBank');
insert into bank(id, name) VALUES(4,'TPBANK');
insert into bank(id, name) VALUES(5,'Agribank');
insert into bank(id, name) VALUES(6,'SHB');
insert into bank(id, name) VALUES(7,'BIDV');
insert into bank(id, name) VALUES(8,'VPBank');

CREATE TABLE persistent_logins (
  username VARCHAR(64) NOT NULL,
  series VARCHAR(64) NOT NULL,
  token VARCHAR(64) NOT NULL,
  last_used TIMESTAMP NOT NULL,
  PRIMARY KEY (series)
);