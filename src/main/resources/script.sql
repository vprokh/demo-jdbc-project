CREATE TABLE IF NOT EXISTS categories
    (id identity primary key, name varchar(100) default 'hello', description VARCHAR(255));
TRUNCATE TABLE categories;

INSERT INTO categories(description) VALUES ('description');
INSERT INTO categories(description) VALUES ('description1');