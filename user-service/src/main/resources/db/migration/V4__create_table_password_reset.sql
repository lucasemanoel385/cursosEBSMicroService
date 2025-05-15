CREATE TABLE password_reset_token (
 id bigint NOT NULL AUTO_INCREMENT,
 token VARCHAR(255) NOT NULL,
 user_id bigint not null,
 expiry_date VARCHAR(100) UNIQUE NOT NULL,
 primary key(id),
 foreign key(user_id) references user(id) ON DELETE CASCADE
);