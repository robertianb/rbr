-- Table ${tableName} : drop column ${columnName}
declare l_count NUMBER;
begin
select count(*) into l_count from USER_TAB_COLUMNS WHERE upper(table_name)= upper('${tableName}') and upper(COLUMN_NAME) = upper('${columnName}');
if (l_count = 1) then
  execute immediate 'alter table ${tableName} drop column ${columnName}';
end if;
end;
/