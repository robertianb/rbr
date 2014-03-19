-- Table ${tableName} : remove primary key ${previousConstraintId}, add new primary key ${nextConstraintId} : ${nextPrimaryKey}
declare @username varchar(200);
set @username = (select user_name())
declare @userid int;
set @userid = (select user_id(@username))

declare @nm varchar(128)
select @nm = name from sysobjects where xtype='PK' and object_name(parent_obj) = '${tableName}' and uid=@userid
if (@nm is not null)
begin
 execute ('alter table ${tableName} drop constraint ' + @nm)
end
go
alter table ${tableName} add ${nextPrimaryKey}
go