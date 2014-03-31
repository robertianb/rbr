-- Table ${tableName} : drop column ${columnName} and constraints
DECLARE @colname VARCHAR(100), @tblname VARCHAR(100), @cmd VARCHAR(1000)
SET @colname = '${columnName}'
SET @tblname = '${tableName}'
declare @username varchar(200);
set @username = (select user_name())
declare @userid int;
set @userid = (select user_id(@username))

SET @cmd = 'ALTER TABLE ' + @tblname + ' DROP CONSTRAINT ' +
(SELECT so.name
FROM sysobjects so
JOIN sysconstraints con ON so.id = con.constid
JOIN syscolumns col ON col.colid = con.colid AND col.id = so.parent_obj
WHERE object_name(so.parent_obj) = @tblname
AND so.xtype = 'D'
AND col.name = @colname
AND so.uid = @userid)

declare @details_count int;
set @details_count = (select count(*) from syscolumns where upper(name)=upper(@colname) and id in (select id from sysobjects where upper(name) = upper(@tblname) and uid=@userid))
if @details_count > 0
begin
  print @cmd
  execute (@cmd)
  execute ('alter table '+@tblname+' drop column '+@colname)
end
go
