BEGIN TRANSACTION;

CREATE TABLE IF NOT EXISTS grad (
	id INTEGER primary key AUTOINCREMENT,
	naziv TEXT unique,
	broj_stanovnika INTEGER,
	drzava INTEGER
);

INSERT INTO grad VALUES (0, "London", 8825000, 0);
INSERT INTO grad VALUES (1, "Pariz", 2206488, 1);
INSERT INTO grad VALUES (2, "Beč", 1899055, 2);
INSERT INTO grad  VALUES (3, "Manchester", 545500, 0);
INSERT INTO grad VALUES (4, "Graz", 280200, 2);

CREATE TABLE IF NOT EXISTS drzava (
	id INTEGER primary key AUTOINCREMENT,
	naziv TEXT unique,
	glavni_grad INTEGER
);

INSERT INTO drzava VALUES (0, "Velika Britanija", 0);
INSERT INTO drzava VALUES (1, "Francuska", 1);
INSERT INTO drzava VALUES (2, "Austrija", 2);
COMMIT;