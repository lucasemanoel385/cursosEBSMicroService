CREATE TABLE course (
 id bigint NOT NULL AUTO_INCREMENT,
 img_url VARCHAR(555),
 title VARCHAR(255) NOT NULL,
 description TEXT NULL,
 playlist_id VARCHAR(255),
 created_at DATETIME NOT NULL,
 instructor_id bigint NOT NULL,
 category_id bigint NOT NULL,
PRIMARY KEY(id),
constraint fk_category_id foreign key(category_id) references category(id)
);