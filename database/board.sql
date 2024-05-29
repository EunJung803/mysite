desc board;

select * from board; 

-- insert
select no as user_no from user order by no desc limit 1;
insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values('하이', 'ㅎㅅㅎ', 0, now(), 1, 1, 0, 3);

-- select
select a.no, b.name, a.title, date_format(a.reg_date, '%Y/%m/%d %h:%i:%s') 
from board a, user b 
where a.user_no = b.no
order by g_no desc, o_no asc;

select a.no, b.name, a.title, a.contents, date_format(a.reg_date, '%Y/%m/%d %h:%i:%s')
from board a, user b 
where a.user_no = b.no
and a.no = 3;

-- delete
delete from board where no = 5;

-- update
update board set title = '하이하이', contents = 'ㅎㅅㅎ 히히' where no = 1;