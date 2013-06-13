-- Create table ${tableName}
declare @username varchar(200);
set @username = (select user_name())
declare @userid int;
set @userid = (select user_id(@username))

declare @l_count INT
set @l_count = (select count(*) from sysobjects WHERE upper(name)= upper('${tableName}') and uid=@userid)
if (@l_count = 0) begin
  execute ('${createTableBody}')
end
go
