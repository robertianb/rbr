-- Table ${tableName} : rename column ${previous} to ${next}
declare @username varchar(200);
set @username = (select user_name())
declare @userid int;
set @userid = (select user_id(@username))

declare @details_count int;
set @details_count = (select count(*) from syscolumns where upper(name)=upper('${previous}') and id in (select id from sysobjects where upper(name) = upper('${tableName}') and uid=@userid))
if @details_count > 0
begin
 execute ('sp_rename ''${tableName}.${previous}'', ''${next}'', ''COLUMN''')
end
go
