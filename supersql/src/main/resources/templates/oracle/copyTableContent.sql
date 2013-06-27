-- Copy table contents from ${sourceTableName} to ${targetTableName}
declare
  l_count NUMBER;
begin
 select count(*) into l_count from user_tables WHERE upper(table_name)= upper('${targetTableName}');
  if (l_count = 1) then
   execute immediate 'insert into ${targetTableName} (select * from ${sourceTableName})' ;
 end if;
end;
/
