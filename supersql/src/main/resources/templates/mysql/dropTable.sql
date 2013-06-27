-- Drop table ${tableName}
DELIMITER $$

DROP PROCEDURE IF EXISTS upgrade_database $$
CREATE PROCEDURE upgrade_database()
BEGIN
-- Drop table ${tableName}
IF EXISTS( (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
        AND TABLE_NAME='${tableName}') ) THEN
    DROP TABLE ${tableName};
END IF;

END $$

CALL upgrade_database() $$

DELIMITER ;