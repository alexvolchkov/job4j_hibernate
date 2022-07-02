CREATE TABLE if not exists db_vacancies (
  id SERIAL PRIMARY KEY
);

CREATE TABLE if not exists vacancy (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  description VARCHAR,
  vacancies_id int not null unique references db_vacancies(id)
);

CREATE TABLE if not exists candidates (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  experience int,
  salary decimal,
  vacancies_id int not null unique references db_vacancies(id)
);

CREATE TABLE if not exists car_brands (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE if not exists models (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE if not exists books (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE if not exists authors (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE if not exists l_car_brands (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE if not exists l_models (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  car_brand_id INTEGER REFERENCES l_car_brands (id)
);

