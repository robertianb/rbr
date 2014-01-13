-- Copy table contents from ${sourceTableName} to ${targetTableName}
insert into ${targetTableName} (select * from ${sourceTableName});
