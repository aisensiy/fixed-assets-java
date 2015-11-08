CREATE TABLE categories (
  id integer PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255)
);

CREATE TABLE policies (
  id integer PRIMARY KEY AUTO_INCREMENT,
  factor integer,
  percentage integer,
  termLengthByMonth integer,
  categoryId integer
);

CREATE TABLE assets (
  id integer PRIMARY KEY AUTO_INCREMENT,
  createdAt TIMESTAMP,
  name VARCHAR(255),
  price INTEGER,
  sold INTEGER,
  categoryId INTEGER
);

CREATE TABLE bases (
  id integer PRIMARY KEY AUTO_INCREMENT,
  assetId integer,
  createdAt TIMESTAMP,
  amount INTEGER,
  depreciation INTEGER
);
