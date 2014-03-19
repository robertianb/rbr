-- Table ${tableName} : remove primary key ${previousConstraintId}, add new primary key ${nextConstraintId} : ${nextPrimaryKey}
declare @username varchar(200)
set @username = (select user_name())
declare @userid int
set @userid = (select user_id(@username))

declare @nm varchar(128)
select @nm = i.name from sysindexes i join sysobjects o on o.id = i.id where o.name = '${tableName}' and o.type = 'U' and o.uid=@userid and i.status & 2 = 2
if (@nm is not null)
begin
 execute ('alter table ${tableName} drop constraint ' + @nm)
end
go
alter table ${tableName} add ${nextPrimaryKey}
go