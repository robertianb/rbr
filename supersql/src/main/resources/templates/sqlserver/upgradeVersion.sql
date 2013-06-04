-- Version management - Check
-- IF THE VERSION IS NOT THE EXPECTED, YOU SHOULD MANUALLY STOP THE SCRIPT (it loops indefinitely)
if not exists (select 1 from Version where databaseVersion like '${previous}' and component = '${component}')
begin
	RAISERROR ('Incorrect database version, please check your version before running this script!', 17, 1);
	while 1 = 1
	begin
		WaitFor Delay '00:00:05'
	end
end
go
-- End of the Version management - Check (DO NOT REMOVE THIS COMMENT)

update Version set databaseVersion='${next}' where databaseVersion='${previous}' and component = '${component}'
go