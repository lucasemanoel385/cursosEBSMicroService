CREATE TABLE user (
 id bigint NOT NULL AUTO_INCREMENT,
 name VARCHAR(100) NOT NULL,
 email VARCHAR(100) UNIQUE NOT NULL,
 password VARCHAR(255) NOT NULL,
 created_at datetime NOT NULL,
 primary key(id)
);