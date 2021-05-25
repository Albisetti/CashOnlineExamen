CREATE DATABASE cash;

use cash;

create table cash.users(
id int NOT NULL AUTO_INCREMENT,
first_name varchar(50) NOT NULL,
last_name varchar(50) NOT NUll,
email varchar(100) NOT NULL,
PRIMARY KEY (id)
);

INSERT INTO cash.users (first_name,last_name,email)
	VALUES ('Juan','Perez','juanPerez@gmail.com');
INSERT INTO cash.users (first_name,last_name,email)
	VALUES ('Marta','Gomez','martaGomez@gmail.com');

create table cash.loans(
id int NOT NULL AUTO_INCREMENT,
total int NOT NULL,
user_id int NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES cash.users(id)
);

INSERT INTO cash.loans (total,user_id)
	VALUES (3000,1);
INSERT INTO cash.loans (total,user_id)
	VALUES (60000,1);
INSERT INTO cash.loans (total,user_id)
	VALUES (80000,2);
INSERT INTO cash.loans (total,user_id)
	VALUES (1000,2);


