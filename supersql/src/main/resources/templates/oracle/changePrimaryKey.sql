-- Table ${tableName} : remove primary key ${previousConstraintId}, add new primary key ${nextConstraintId} : ${nextPrimaryKey}
DECLARE l_name VARCHAR2(30);
BEGIN
select ${previousConstraintId} into l_name from user_constraints WHERE table_name=upper('${tableName}') AND constraint_type = 'P';
if (l_name is not null) then
  execute immediate 'alter table ${tableName} drop constraint ' || l_name || ' drop index';
end if;
END;
/
alter table ${tableName} add constraint ${nextConstraintId} primary key ${nextPrimaryKey}
/