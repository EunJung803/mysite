desc user;

-- join
insert into user values(null, '관리자', 'admin@mysite.com', password('1234'), 'female', current_date(), "ADMIN");

-- login
select * from user where email = 'hello@email.com' and password = password('1234');

-- test
select * from user; 

-- role 추가
alter table user add column role enum("USER", "ADMIN") not null default "USER";

update user set role="ADMIN" where no=1;