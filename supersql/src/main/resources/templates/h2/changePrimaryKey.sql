-- Table ${tableName} : remove primary key ${previousConstraintId}, add new primary key ${nextConstraintId} : ${nextPrimaryKey}
alter table ${tableName} add constraint ${nextConstraintId} primary key ${nextPrimaryKey};
