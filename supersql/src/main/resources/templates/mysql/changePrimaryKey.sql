-- Table ${tableName} : remove primary key ${previousConstraintId}, add new primary key ${nextConstraintId} : ${nextPrimaryKey}
DELIMITER $$

DROP PROCEDURE IF EXISTS upgrade_database $$
CREATE PROCEDURE upgrade_database()
BEGIN

IF EXISTS( (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
        AND TABLE_NAME='${tableName}') ) THEN
    ALTER TABLE ${tableName} DROP PRIMARY KEY;
	ALTER TABLE ${tableName} ADD PRIMARY KEY  ${nextPrimaryKey};
END IF;

END $$

CALL upgrade_database() $$

DELIMITER ;