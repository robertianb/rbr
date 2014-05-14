-- Table ${tableName} : remove primary key ${previousConstraintId}, add new primary key ${nextConstraintId} : ${nextPrimaryKey}
DECLARE 
l_name VARCHAR2(30);
l_count INT;
BEGIN
select count(*) into l_count from user_constraints WHERE table_name=upper('${tableName}') AND constraint_type = 'P';
if (l_count = 1)
then
    select CONSTRAINT_NAME into l_name from user_constraints WHERE table_name=upper('${tableName}') AND constraint_type = 'P';
    execute immediate 'alter table ${tableName} drop constraint ' || l_name || ' drop index';
end if;
END;
/


DECLARE
l_count INT;
BEGIN
select count(*) into l_count from user_constraints WHERE table_name=upper('${tableName}') AND constraint_type = 'P';
if (l_count = 0)
then
  execute immediate 'alter table ${tableName} add ${nextPrimaryKey}';
end if;
END;
/
