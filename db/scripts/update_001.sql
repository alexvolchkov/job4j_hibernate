CREATE TABLE if not exists candidates (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  experience int,
  salary decimal
);

CREATE TABLE if not exists car_brands (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE if not exists models (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

