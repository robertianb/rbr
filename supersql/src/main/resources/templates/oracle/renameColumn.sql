-- Table ${tableName} : rename column ${previous} to ${next}
declare l_count NUMBER;
begin
select count(*) into l_count from USER_TAB_COLUMNS WHERE upper(table_name)= upper('${tableName}') and upper(COLUMN_NAME) = upper('${previous}');
if (l_count > 0) then
 execute immediate 'alter table ${tableName} rename column ${previous} to ${next}';
end if;
end;
/