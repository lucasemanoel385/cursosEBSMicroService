CREATE TABLE lesson (
 id bigint NOT NULL AUTO_INCREMENT,
 course_id bigint NOT NULL,
 title VARCHAR(255) NOT NULL,
 description TEXT NOT NULL,
 video_url varchar(500),
 duration int,
 position int NOT NULL,
 PRIMARY KEY(id),
 FOREIGN KEY (course_id) REFERENCES course(id)
);