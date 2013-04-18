-- Table ${tableName} : add column ${columnName}
declare l_count NUMBER;
begin
select count(*) into l_count from USER_TAB_COLUMNS WHERE upper(table_name)= upper('${tableName}') and upper(COLUMN_NAME) = upper('${columnName}');
if (l_count = 0) then
  execute immediate 'alter table ${tableName} add ${columnName} ${columnType} ${mandatory}';
end if;
end;
/
