CREATE DATABASE IF NOT EXISTS airlinereservationsys  ;

USE airlinereservationsys;

CREATE TABLE IF NOT EXISTS user_category(
    category_id ENUM('0','1','2') not null,
    category_name ENUM('Admin','Gold','Frequent') not null,
    discount float (5,3),
	primary key (category_id)
);

DELIMITER $$
CREATE TRIGGER user_category_trigeer BEFORE INSERT ON user_category FOR EACH ROW
BEGIN
  IF (New.discount <0) THEN
    CALL `"Invalid discount"`;
    SIGNAL SQLSTATE '40000';
  END IF;
  IF (New.category_name NOT IN('Admin','Gold','Frequent')) THEN
    CALL `"Invalid category_name"`;
    SIGNAL SQLSTATE '40000';
  END IF;
END$$
DELIMITER ;

INSERT INTO user_category VALUES('0','Admin',0.0);
INSERT INTO user_category VALUES('1','Gold',0.09);
INSERT INTO user_category VALUES('2','Frequent',0.05);

create table  if not exists user (
    user_id int auto_increment,
    username varchar(30) not null unique,
    password varchar(30) not null,
    firstname varchar(30) not null,
    lastname varchar(30) not null,
    user_category_id ENUM('0','1','2') not null,
    email varchar(50) not null,
    phonenumber varchar(10) not null,
    address varchar(50) not null,
    age int not null,
    primary key(user_id)
);

CREATE TABLE user_audit(
    id int auto_increment PRIMARY KEY,
	username varchar(30) not null unique,
    password varchar(30) not null,
    firstname varchar(30) not null,
    lastname varchar(30) not null,
	phonenumber varchar(10) not null,
	action varchar(10) not null,
	changedat DATETIME DEFAULT NULL
);

DELIMITER $$
CREATE TRIGGER before_user_update BEFORE UPDATE ON user FOR EACH ROW
BEGIN
	INSERT INTO user_audit(username,password,firstname,lastname,phonenumber,action,changedat) 
					VALUES(OLD.username,OLD.password,OLD.firstname,OLD.lastname,OLD.phonenumber,'update',NOW());
END$$

CREATE TRIGGER before_user_delete BEFORE DELETE ON user FOR EACH ROW
BEGIN
	INSERT INTO user_audit(username,password,firstname,lastname,phonenumber,action,changedat) 
					VALUES(OLD.username,OLD.password,OLD.firstname,OLD.lastname,OLD.phonenumber,'delete',NOW());
END$$

CREATE TRIGGER trg_entity_input_insert BEFORE INSERT ON user FOR EACH ROW
BEGIN
  IF (New.age <0 or new.age>150) THEN
    CALL `"Invalid Age"`;
    SIGNAL SQLSTATE '40000';
  END IF;
	IF NOT(SELECT NEW.email REGEXP '^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$') THEN
		CALL `"Invalid email"`;
		SIGNAL SQLSTATE '40000';
	END IF;
	IF NOT(LENGTH(NEW.password)>6) THEN
		CALL `"Invalid password"`;
		SIGNAL SQLSTATE '40000';
	END IF;
	IF NOT(SELECT NEW.phonenumber REGEXP '^0[0-9]') AND LENGTH(NEW.phonenumber =10) THEN
		CALL `"Invalid phonenumber"`;
		SIGNAL SQLSTATE '40000';
	END IF;
	IF NOT(LENGTH(NEW.address)>5) THEN
		CALL `"Invalid address"`;
		SIGNAL SQLSTATE '40000';
	END IF;
	IF NOT(LENGTH(NEW.firstname)>0) THEN
		CALL `"Invalid firstname"`;
		SIGNAL SQLSTATE '40000';
	END IF;
	IF NOT(LENGTH(NEW.lastname)>0) THEN
		CALL `"Invalid lastname"`;
		SIGNAL SQLSTATE '40000';
	END IF;

END$$
DELIMITER ;

INSERT INTO user(username,password,firstname,lastname,user_category_id,email,phonenumber,address) VALUES('Chamoda','123Chamoda','chamoda','jeewantha','1','DSF@GDG.GFH','0464587962','adder no ,werwer');
INSERT INTO user(username,password,firstname,lastname,user_category_id,email,phonenumber,address) VALUES('Chamith','123chamith','chamith','nirmal','2','cham@sds.GFsdH','0234567894','addresss no ,bnbmbnmbhmbhmbvbj');
INSERT INTO user(username,password,firstname,lastname,user_category_id,email,phonenumber,address) VALUES ('admin1', '12345678', 'Udhan', 'Isuranga','0','udhan@gmail.com', '0769009568', 'baddegama');
INSERT INTO user(username,password,firstname,lastname,user_category_id,email,phonenumber,address) VALUES ('kasun', '123kasun', 'Kasun', 'Fernando','2','kasun@gmail.com', '0979009568', 'Weligama');

UPDATE user SET password='newPW123' WHERE username='Chamith';
DELETE FROM user WHERE username = 'kasun';

create table  if not exists airplane_type (
    type_id varchar(30) not null,
	type varchar(30) not null,
	no_of_seats_econ int(30),
	no_of_seats_business int(30),
	no_of_seats_platinum int(30),
    primary key(type_id)

);

DELIMITER $$
CREATE TRIGGER airplane_type_trigeer BEFORE INSERT ON airplane_type FOR EACH ROW
BEGIN
    IF (length(new.type_id)=0) THEN
			CALL `"Wrong type_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (length(new.type)=0) THEN
			CALL `"Wrong type"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (new.no_of_seats_econ<0 or new.no_of_seats_business<0 or new.no_of_seats_platinum<0) THEN
			CALL `"Illegal Seat No"`;
			SIGNAL SQLSTATE '40000';
    END IF;
END$$
DELIMITER ;

insert into airplane_type values ("type1","aaaa",45,35,35);

create table  if not exists airplane (
    airplane_id varchar(30) not null,
    type_id varchar(30) not null,

    primary key(airplane_id),
    foreign key (type_id) references airplane_type(type_id)

);

insert into airplane values("KL 445","type1");
insert into airplane values("KN 334","type1");
insert into airplane values("RM 64","type1");
insert into airplane values("MG 64","type1");

create table  if not exists plane_seats (
    airplane_id varchar(30) not null,
	seat_no int(4) not null,
    type ENUM('Economy','Business','Platinum') not null,
    primary key(airplane_id, seat_no),
    foreign key (airplane_id) references airplane(airplane_id)
);

DELIMITER $$
CREATE TRIGGER plane_seats_trigeer BEFORE INSERT ON plane_seats FOR EACH ROW
BEGIN
	IF (new.seat_no<0 or new.seat_no>400) THEN
			CALL `"Invalid Seat No"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (new.type NOT IN('Economy','Business','Platinum')) THEN
			CALL `"Invalid type"`;
			SIGNAL SQLSTATE '40000';
    END IF;
END$$
DELIMITER ;

insert into plane_seats values("KN 334",1,'Economy');
insert into plane_seats values("RM 64",100,'Economy');
insert into plane_seats values("MG 64",1,'Platinum');

create table  if not exists price (
    price_id varchar(30) not null,
	econ_price float(10,3),
	business_price float(10,3),
	platinum_price float(10,3),
    primary key(price_id)
);

DELIMITER $$
CREATE TRIGGER price_trigeer BEFORE INSERT ON price FOR EACH ROW
BEGIN
	IF (new.econ_price<=0 or new.business_price<=0 or new.platinum_price<=0) THEN
			CALL `"Illegal Price"`;
			SIGNAL SQLSTATE '40000';
    END IF;
END$$
DELIMITER ;


insert into price values('1',334,556,788);
insert into price values('2',3500,5500,7000);
insert into price values('3',1000,5500,7400);
insert into price values('4',1000,5500,7400);

create table  if not exists delay (
    delay_id int not null auto_increment,
	new_departure_date date,
	new_departure_time time,
	new_arrival_date date,
	new_arrival_time time,
	reson varchar(30),
    primary key(delay_id)
);

DELIMITER $$
CREATE TRIGGER delay_trigeer BEFORE INSERT ON delay FOR EACH ROW
BEGIN
	 IF (NEW.new_departure_date > NEW.new_arrival_date OR (NEW.new_departure_date = NEW.new_arrival_date AND NEW.new_departure_time > NEW.new_arrival_time) ) THEN
	 		CALL `"Invalid date"`;
	 		SIGNAL SQLSTATE '40000';
     END IF;
END$$
DELIMITER ;

insert into delay values(1,'2018-11-06','13:00:00','2018-11-10','14:00:00',"rain");
insert into delay values(2,'2018-11-11','2:00:00','2018-11-11','5:00:00',"rain");
insert into delay values(3,'2018-11-12','5:00:00','2018-11-12','5:50:00',"ice");
insert into delay values(4,'2018-11-05','8:00:00','2018-11-13','8:20:00',"ice");

create table  if not exists location (
    location_id varchar(30) not null,
	name varchar(30) not null,
	parent_id varchar(30),
    primary key(location_id)
);

DELIMITER $$
CREATE TRIGGER location_trigeer BEFORE INSERT ON location FOR EACH ROW
BEGIN
	 IF (length(NEW.location_id)=0 OR length(NEW.name)=0 ) THEN
	 		CALL `"Invalid location"`;
	 		SIGNAL SQLSTATE '40000';
     END IF;
END$$
DELIMITER ;

insert into location values ("loc1","fsd","");
insert into location values ("loc2","fsd","ff");
insert into location values ("loc3","fsd","ff");
insert into location values ("loc4","fsd","ff");

create table  if not exists airport (
    airport_code varchar(30) not null,
	primary_location_id varchar(30) not null,
	name varchar(30) not null,
    primary key(airport_code),
	foreign key (primary_location_id) references location(location_id)
);

DELIMITER $$
CREATE TRIGGER airport_trigeer BEFORE INSERT ON airport FOR EACH ROW
BEGIN
	 IF (length(NEW.airport_code)=0 OR length(NEW.primary_location_id)=0 OR length(NEW.name)=0) THEN
	 		CALL `"Invalid airport"`;
	 		SIGNAL SQLSTATE '40000';
     END IF;
END$$
DELIMITER ;

INSERT INTO airport VALUES (12,"loc1","Mattala");
INSERT INTO airport VAlUES (24,"loc2","Colombo");
INSERT INTO airport VAlUES (30,"loc3","India");
INSERT INTO airport VAlUES (10,"loc4","China");

create table if not exists route (
    route_id varchar(30) not null,
	from_port_id varchar(30) not null,
	to_port_id varchar(30) not null,
    primary key(route_id),
	foreign key (from_port_id) references airport(airport_code),
	foreign key (to_port_id) references airport(airport_code)
);

DELIMITER $$
CREATE TRIGGER route_trigeer BEFORE INSERT ON route FOR EACH ROW
BEGIN
	IF (LENGTH(NEW.route_id)=0 or LENGTH(NEW.from_port_id)=0 or LENGTH(NEW.to_port_id)=0) THEN
			CALL `"Invalid route"`;
			SIGNAL SQLSTATE '40000';
    END IF;
END$$
DELIMITER ;

INSERT INTO route VALUES ("r1",12,24);
INSERT INTO route VALUES ("r2",12,30);
INSERT INTO route VALUES ("r3",10,30);
INSERT INTO route VALUES ("r4",12,24);

create table  if not exists flight (
    flight_id varchar(30) not null,
	departure_day_of_week ENUM('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') not null,
	departure_time time not null,
	arrival_day_of_week ENUM('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') not null,
	arrival_time time not null,
	route_id varchar(30) not null,
    primary key(flight_id),
	foreign key (route_id) references route(route_id)
);

CREATE TABLE flight_audit(
    flight_id varchar(30) not null PRIMARY KEY,
	departure_day_of_week ENUM('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') not null,
	departure_time time not null,
	arrival_day_of_week ENUM('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') not null,
	arrival_time time not null,
	route_id varchar(30) not null,
	action varchar(10) not null,
	changedat DATETIME DEFAULT NULL
);

DELIMITER $$
CREATE TRIGGER before_flight_update BEFORE UPDATE ON flight FOR EACH ROW
BEGIN
	INSERT INTO flight_audit(flight_id,departure_day_of_week,departure_time,arrival_day_of_week,arrival_time,route_id,action,changedat) 
					VALUES(OLD.flight_id,OLD.departure_day_of_week,OLD.departure_time,OLD.arrival_day_of_week,OLD.arrival_time,OLD.route_id,'update',NOW());
END$$

CREATE TRIGGER before_flight_delete BEFORE DELETE ON flight FOR EACH ROW
BEGIN
	INSERT INTO flight_audit(flight_id,departure_day_of_week,departure_time,arrival_day_of_week,arrival_time,route_id,action,changedat) 
					VALUES(OLD.flight_id,OLD.departure_day_of_week,OLD.departure_time,OLD.arrival_day_of_week,OLD.arrival_time,OLD.route_id,'delete',NOW());
END$$

CREATE TRIGGER flight_trigeer BEFORE INSERT ON flight FOR EACH ROW
BEGIN
	IF (LENGTH(NEW.flight_id)=0) THEN
			CALL `"Invalid flight_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (LENGTH(NEW.route_id)=0) THEN
			CALL `"Invalid route_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (New.departure_day_of_week NOT IN('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') OR New.arrival_day_of_week NOT IN('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') ) THEN
		CALL `"Invalid day"`;
		SIGNAL SQLSTATE '40000';
	END IF;
END$$
DELIMITER ;

insert into flight values ('AA-1',"Monday",'10:10:00','Monday','11:00:00',"r1");
insert into flight values ('AB-1',"Tuesday",'2:10:00','Tuesday','2:50:00',"r2");
insert into flight values ('EB-12',"Wednesday",'1:00:00','Thursday','1:50:00',"r3");
insert into flight values ('WZ-11',"Thursday",'7:00:00','Thursday','7:45:00',"r4");

UPDATE flight SET departure_day_of_week ="Wednesday" WHERE flight_id='WZ-11';

create table if not exists schedule(
    schedule_id varchar(30) not  null,
    date date not null,
	flight_id varchar(30) not null,
    booked_seats_econ int,
    booked_seats_business int,
    booked_seats_platinum int,
    price_id varchar(30) not null,
    airplane_id varchar(10) not null,
    delay_id int,
    primary key(schedule_id),
	foreign key (flight_id) references flight(flight_id),
	foreign key (price_id) references price(price_id),
	foreign key (airplane_id) references airplane(airplane_id),
	foreign key (delay_id) references delay(delay_id)
);

CREATE TABLE schedule_audit(
    schedule_id varchar(30) not  null PRIMARY KEY,
    date date not null,
	flight_id varchar(30) not null,
    booked_seats_econ int,
    booked_seats_business int,
    booked_seats_platinum int,
    price_id varchar(30) not null,
    airplane_id varchar(10) not null,
    delay_id int,
	action varchar(10) not null,
	changedat DATETIME DEFAULT NULL
);

DELIMITER $$

CREATE TRIGGER before_schedule_update BEFORE UPDATE ON schedule FOR EACH ROW
BEGIN
	INSERT INTO schedule_audit(schedule_id,date,flight_id,booked_seats_econ,booked_seats_business,booked_seats_platinum,price_id,airplane_id,delay_id,action,changedat) 
					VALUES(OLD.schedule_id,OLD.date,OLD.flight_id,OLD.booked_seats_econ,OLD.booked_seats_business,OLD.booked_seats_platinum,OLD.price_id,OLD.airplane_id,OLD.delay_id,'update',NOW());
END$$

CREATE TRIGGER before_schedule_delete BEFORE DELETE ON schedule FOR EACH ROW
BEGIN
	INSERT INTO schedule_audit(schedule_id,date,flight_id,booked_seats_econ,booked_seats_business,booked_seats_platinum,price_id,airplane_id,delay_id,action,changedat) 
					VALUES(OLD.schedule_id,OLD.date,OLD.flight_id,OLD.booked_seats_econ,OLD.booked_seats_business,OLD.booked_seats_platinum,OLD.price_id,OLD.airplane_id,OLD.delay_id,'delete',NOW());
END$$

CREATE TRIGGER schedule_trigeer BEFORE INSERT ON schedule FOR EACH ROW
BEGIN
	IF (LENGTH(NEW.schedule_id)=0) THEN
			CALL `"Invalid schedule_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (LENGTH(NEW.flight_id)=0) THEN
			CALL `"Invalid flight_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (LENGTH(NEW.price_id)=0) THEN
			CALL `"Invalid price_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (LENGTH(NEW.airplane_id)=0) THEN
			CALL `"Invalid airplane_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (NEW.delay_id<0) THEN
			CALL `"Invalid delay_id"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (NEW.booked_seats_econ <0 OR NEW.booked_seats_business <0 OR NEW.booked_seats_platinum <0) THEN
			CALL `"Invalid Seat_no"`;
			SIGNAL SQLSTATE '40000';
    END IF;
END$$
DELIMITER ;

insert into schedule values ('0001','2018-11-06','AA-1',40,30,30,'1',"KL 445",1);
insert into schedule values ('0002','2018-11-11','AB-1',40,30,30,'2',"KN 334",2);
insert into schedule values ('0003','2018-11-12','EB-12',50,50,50,'3',"RM 64",3);
insert into schedule values ('0004','2018-11-05','WZ-11',80,10,10,'4',"MG 64",4);

UPDATE schedule SET date ='2018-11-04' WHERE schedule_id='0004';

create table if not exists booking(
    booking_id int not  null auto_increment,
    user_id int not null,
    schedule_id varchar(30) not null ,
    payment_status boolean not null,
    seat_no int not null,
    primary key(booking_id),
	foreign key (user_id) references user(user_id),
	foreign key (schedule_id) references schedule(schedule_id)
);

CREATE TABLE booking_audit(
    booking_id int not null PRIMARY KEY,
	user_id int not null,
    schedule_id varchar(30) not null ,
    payment_status boolean not null,
    seat_no int not null,
	action varchar(10) not null,
	changedat DATETIME DEFAULT NULL
);

DELIMITER $$
CREATE TRIGGER before_booking_update BEFORE UPDATE ON booking FOR EACH ROW
BEGIN
	INSERT INTO booking_audit(booking_id,user_id,schedule_id,payment_status,seat_no,action,changedat) 
					VALUES(OLD.booking_id,OLD.user_id,OLD.schedule_id,OLD.payment_status,OLD.seat_no,'update',NOW());
END$$

CREATE TRIGGER before_booking_delete BEFORE DELETE ON booking FOR EACH ROW
BEGIN
	INSERT INTO booking_audit(booking_id,user_id,schedule_id,payment_status,seat_no,action,changedat) 
					VALUES(OLD.booking_id,OLD.user_id,OLD.schedule_id,OLD.payment_status,OLD.seat_no,'delete',NOW());
END$$

CREATE TRIGGER booking_trigger BEFORE INSERT ON booking FOR EACH ROW
BEGIN
	IF (new.seat_no<0 or new.seat_no>400) THEN
			CALL `"Invalid Seat No"`;
			SIGNAL SQLSTATE '40000';
    END IF;
	IF (LENGTH(NEW.user_id)=0 or LENGTH(NEW.schedule_id)=0) THEN
		CALL `"Invalid ID"`;
		SIGNAL SQLSTATE '40000';
	END IF;
END$$
DELIMITER ;

INSERT INTO booking(user_id,schedule_id,payment_status,seat_no) VALUES(1,'0001',true,1),
																	  (2,'0004',true,1),	
																	  (3,'0003',false,100);
UPDATE booking SET payment_status=false WHERE user_id=2;
