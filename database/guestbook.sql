desc guestbook;

-- insert
insert into guestbook(name, password, contents, reg_date) values('김김김', password('1234'), "안녕하세용", now());

-- select
select no, name, contents, date_format(reg_date, '%Y/%m/%d %h:%i:%s') from guestbook order by reg_date desc;

-- delete
delete from guestbook where no = 1 and password = password('1234');

-- test
select * from guestbook;