CREATE TABLE if not exists candidates (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  experience int,
  salary decimal
);