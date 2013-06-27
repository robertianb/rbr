-- Table ${tableName} : modify column ${columnName} 's type from ${columnPreviousType} to ${columnType}
DELIMITER $$

DROP PROCEDURE IF EXISTS upgrade_database $$
CREATE PROCEDURE upgrade_database()
BEGIN
-- Table ${tableName} : modify column ${columnName} 's type from ${columnPreviousType} to ${columnType}
IF EXISTS( (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
        AND COLUMN_NAME='${columnName}' AND TABLE_NAME='${tableName}') ) THEN
    alter table ${tableName} modify ${columnName} ${columnType};
END IF;

END $$

CALL upgrade_database() $$

DELIMITER ;