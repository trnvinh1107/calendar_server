CREATE TABLE users (
	userId Long AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50),
    firstName NVARCHAR(50),
    lastName NVARCHAR(50),
    email VARCHAR(255),
    password VARCHAR(255),
    isAdmin BOOLEAN,
    apiToken VARCHAR(50)
);

CREATE TABLE rooms (
	id Long AUTO_INCREMENT PRIMARY KEY,
	name NVARCHAR(50),
	capacity INT,
	description NVARCHAR(255),
	isActivity BOOLEAN
);


CREATE TABLE dayoff (
	id Long AUTO_INCREMENT PRIMARY KEY,
	name NVARCHAR(50),
	dayOff DATETIME,
	description NVARCHAR(255),
	isActivity BOOLEAN
);

CREATE TABLE bookingroom(
	id Long AUTO_INCREMENT PRIMARY KEY,
	userId Long,
	roomId Long,
	startTime DATETIME,
	endTime DATETIME,
	description NVARCHAR(255), 
	color VARCHAR(50),
	FOREIGN KEY (userId) REFERENCES users(userId), 
	FOREIGN KEY (roomId) REFERENCES rooms(id) 
);