-- Table ${tableName} : modify column ${columnName} 's type from ${columnPreviousType} to ${columnType} ${mandatory} default ${defaultValue}
declare @username varchar(200)
declare @userid int
set @username = (select user_name())
set @userid = (select user_id(@username))

declare @details_count INT
set @details_count = (select count(*) from syscolumns where upper(name)=upper('${columnName}') and id in (select id from sysobjects where upper(name) = upper('${tableName}') and uid=@userid))
if @details_count = 1
begin
  execute ('alter table ${tableName} modify ${columnName}  ${columnType} ${mandatory} default ${{defaultValue}}')
end
go
