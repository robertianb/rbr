-- Create TEMPORARY table ${targetTableName} from ${sourceTableName}
declare
  l_count NUMBER;
begin
 select count(*) into l_count from user_tables WHERE upper(table_name)= upper('${targetTableName}');
  if (l_count = 0) then
   execute immediate 'CREATE TABLE ${targetTableName} AS SELECT * FROM ${sourceTableName}' ;
 end if;
end;
/
