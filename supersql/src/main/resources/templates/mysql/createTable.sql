-- Create table ${tableName}
DELIMITER $$

DROP PROCEDURE IF EXISTS upgrade_database $$
CREATE PROCEDURE upgrade_database()
BEGIN
-- Create table ${tableName}
IF NOT EXISTS( (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
        AND TABLE_NAME='${tableName}') ) THEN
    ${createTableBody};
END IF;

END $$

CALL upgrade_database() $$

DELIMITER ;