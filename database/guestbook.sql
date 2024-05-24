desc guestbook;

-- insert
insert into guestbook(name, password, contents, reg_date) values('김김김', password('1234'), "안녕하세용", now());

-- select
select no, name, contents, date_format(reg_date, '%Y/%m/%d %h:%i:%s') from guestbook order by reg_date desc;

-- delete
delete from guestbook where no = 1 and password = password('1234');

-- test
select * from guestbook;

-- 방명록 contents에 이모티콘 삽입되도록
ALTER DATABASE webdb CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
ALTER TABLE guestbook CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
