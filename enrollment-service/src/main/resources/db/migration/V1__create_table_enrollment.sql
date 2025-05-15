CREATE TABLE enrollment (
 id bigint NOT NULL AUTO_INCREMENT,
 user_id bigint NOT NULL,
 course_id bigint NOT NULL,
 enrollment_date DATETIME NOT NULL,
 PRIMARY KEY(id)
);