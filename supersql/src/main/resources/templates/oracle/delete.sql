-- Delete from table ${tableName}
declare
  l_count NUMBER;
begin
 select count(*) into l_count from user_tables WHERE upper(table_name)= upper('${tableName}');
  if (l_count = 1) then
   execute immediate 'delete from ${tableName}' ;
 end if;
end;
/
