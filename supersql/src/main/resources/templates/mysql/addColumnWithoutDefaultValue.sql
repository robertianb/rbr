-- Table ${tableName} : add column ${columnName}
declare @username varchar(200);
set @username = (select user_name())
declare @userid int;
set @userid = (select user_id(@username))

declare @details_count int;
set @details_count = (select count(*) from syscolumns where upper(name)=upper('${columnName}') and id in (select id from sysobjects where upper(name) = upper('${tableName}') and uid=@userid))
if @details_count = 0
begin
  execute ('alter table ${tableName} add ${columnName} ${columnType} ${mandatory} ')
end
go