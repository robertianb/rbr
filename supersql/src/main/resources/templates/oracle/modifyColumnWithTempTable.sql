-- Table ${tableName} : modify column ${columnName} 's type from ${columnPreviousType} to ${columnType} using a temp table
declare l_count NUMBER;
begin
select count(*) into l_count from USER_TAB_COLUMNS WHERE upper(table_name)= upper('${tableName}') and upper(COLUMN_NAME) = upper('${columnName}');
if (l_count = 1) then
	execute immediate '${createTempTableBody}';
	execute immediate 'insert into ${tempTableName} (select ${columnName} from ${tableName})';
	execute immediate 'delete ${columnName} from ${tableName}';
	execute immediate 'drop ${tempTableName}';
end if;
end;
/
