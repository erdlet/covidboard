CREATE TABLE counties (
	ags VARCHAR(8) PRIMARY KEY,
	name VARCHAR(1024) NOT NULL
);

CREATE TABLE statistics (

	id INT PRIMARY KEY AUTO_INCREMENT,
	seven_day_incidence DECIMAL(5,2) NOT NULL,
	rki_date DATE NOT NULL,
	county_ags VARCHAR(8),
	FOREIGN KEY COUNTY_FK (county_ags) REFERENCES counties (ags)
);