-- Table ${tableName} : drop column ${columnName}


DROP PROCEDURE IF EXISTS upgrade_database $$
CREATE PROCEDURE upgrade_database()
BEGIN
-- Table ${tableName} : drop column ${columnName}
IF EXISTS( (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
        AND COLUMN_NAME='${columnName}' AND TABLE_NAME='${tableName}') ) THEN
    alter table ${tableName} drop ${columnName} ;
END IF;

END $$

CALL upgrade_database() $$

