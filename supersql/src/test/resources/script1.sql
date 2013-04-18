create table head (
  b int,
  c int,
  d VARCHAR2(10),
  e int primary key
)
/

alter table head add (
mouth int,
nose int
)
/



alter table head drop ( c, d)
/


alter table head add column eyes int
/

alter table head drop column eyes
/



