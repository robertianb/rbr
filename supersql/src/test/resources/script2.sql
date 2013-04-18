alter table User modify name VARCHAR(50)
/

drop table User
/

alter table User add columns
(
    login  VARCHAR(9),
    id  int
)
/

alter table User rename column id to userId
/


alter table User delete columns
(
  login,
  id,
)
/

rename table User to UserInfo
/







