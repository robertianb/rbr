-- Table ${tableName} : drop column ${columnName} and constraints
declare @username varchar(200)
DECLARE @colname VARCHAR(100), @tblname VARCHAR(100), @cmd VARCHAR(1000)
declare @username varchar(200)
set @username = (select user_name())
declare @userid INT
set @userid = (select user_id(@username))

SET @colname = '${columnName}'
SET @tblname = '${tableName}'
SET @cmd = 'ALTER TABLE ' + @tblname + ' DROP CONSTRAINT ' +
(SELECT so.name
FROM sysobjects so
JOIN sysconstraints con ON so.id = con.constrid
JOIN syscolumns col ON col.colid = con.colid
WHERE object_name(con.tableid) = @tblname 
AND uid=@userid
AND so.type = 'R'
AND col.name = @colname)

declare @details_count INT
set @details_count = (select count(*) from syscolumns where upper(name)=upper(@colname) and id in (select id from sysobjects where upper(name) = upper(@tblname) and uid=@userid))
if @details_count > 0
begin
  print @cmd
  execute (@cmd)
  execute ('alter table '+@tblname+' drop '+@colname)
end
go