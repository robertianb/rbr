-- Table ${tableName} : rename column ${previous} to ${next}


DROP PROCEDURE IF EXISTS upgrade_database $$
CREATE PROCEDURE upgrade_database()
BEGIN
-- Table ${tableName} : rename column ${previous} to ${next}
IF EXISTS( (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
        AND COLUMN_NAME='${previous}' AND TABLE_NAME='${tableName}') ) THEN
    alter table ${tableName} modify ${previous} ${next};
END IF;

END $$

CALL upgrade_database() $$

