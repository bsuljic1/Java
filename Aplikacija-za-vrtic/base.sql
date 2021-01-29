BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS child (
	id	INTEGER PRIMARY KEY AUTOINCREMENT,
	first_name TEXT,
	last_name TEXT,
	address TEXT,
	parent INTEGER,
	teacher INTEGER,
	start_time TIME,
    end_time TIME,
    behavior INTEGER,
    notes TEXT,
    activity INTEGER
);

CREATE TABLE IF NOT EXISTS teacher (
    id	INTEGER PRIMARY KEY AUTOINCREMENT,
	first_name	TEXT,
	last_name	TEXT,
	address TEXT,
	username TEXT UNIQUE,
	password TEXT,
	phone_number INTEGER,
	start_of_work TIME,
	end_of_work TIME
);

CREATE TABLE IF NOT EXISTS parent (
	id	INTEGER PRIMARY KEY AUTOINCREMENT,
	first_name	TEXT,
	last_name TEXT,
	address TEXT,
	username TEXT UNIQUE,
	password TEXT,
	phone_number INTEGER
);

CREATE TABLE IF NOT EXISTS director (
	id	INTEGER PRIMARY KEY AUTOINCREMENT,
	first_name	TEXT,
	last_name TEXT,
	address TEXT,
	username TEXT UNIQUE,
	password TEXT,
	phone_number INTEGER
);

CREATE TABLE IF NOT EXISTS activity (
	id	INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	description TEXT
);

INSERT INTO parent VALUES (1, "Tom", "Tomic", "Street", "ttomic", "tomictomic1", 062341556);
INSERT INTO teacher VALUES (3, "Sara", "Saric", "Street2", "ssaric3", "saricsaric3", 065564656, '08:00:00', '16:00:00');
INSERT INTO child VALUES (2, "Tomica", "Tomic", "Street", 1, 3, '08:00:00', '16:00:00', 5, "All the best", 5);
INSERT INTO director VALUES (4, "Director", "Director", "Streeeeet", "director", "director", 0626566445);
INSERT INTO activity VALUES (5, "Sleeping", "Sleeping");
INSERT INTO activity VALUES (6, "Undefined", "Activity is not entered yet.");

COMMIT;