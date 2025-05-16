insert into user(id, name, email, password, created_at) values(1, "Admin", "admin@gmail.com", "admin", NOW());
insert into user_profile(user_id, profile_id) values (1, 4);