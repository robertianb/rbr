-- Version management - Check
-- IF THE VERSION IS NOT THE EXPECTED, YOU SHOULD MANUALLY STOP THE SCRIPT (it loops indefinitely)


DROP PROCEDURE IF EXISTS upgrade_database $$
CREATE PROCEDURE upgrade_database()
proc_start:BEGIN
-- Update version from ${previous} to ${next}
if exists (select 1 from Version where component = '${component}')
then
	if not exists (select 1 from Version where databaseVersion like '${previous}' and component = '${component}')
	then
		SIGNAL SQLSTATE '45000'
		  SET MESSAGE_TEXT = 'Incorrect database version, please check your version before running this script!', MYSQL_ERRNO = 45000;
		  LEAVE proc_start;	
	end if;
	-- End of the Version management - Check (DO NOT REMOVE THIS COMMENT)
    update Version set databaseVersion='${next}' where databaseVersion like '${previous}' and component = '${component}';
else 
    insert into Version (databaseVersion,component) values ('${next}', '${component}');
end if;


END $$

CALL upgrade_database() $$


