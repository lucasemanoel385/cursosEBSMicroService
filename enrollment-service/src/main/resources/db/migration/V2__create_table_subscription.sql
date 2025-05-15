CREATE TABLE subscriptions (
 id CHAR(36) PRIMARY KEY,
 user_id bigint NOT NULL,
 status varchar(35) NOT NULL,
 start_date datetime ,
 expiration_date datetime,
 paid_market_id varchar(255) UNIQUE
);