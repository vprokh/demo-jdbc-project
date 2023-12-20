CREATE TABLE IF NOT EXISTS categories
    (id identity primary key, name varchar(100) default 'hello', description VARCHAR(255));
TRUNCATE TABLE categories;

INSERT INTO categories VALUES ('description');
INSERT INTO categories VALUES ('description1');