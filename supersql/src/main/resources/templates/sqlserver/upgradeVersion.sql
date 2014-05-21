-- Version management - Check
-- IF THE VERSION IS NOT THE EXPECTED, YOU SHOULD MANUALLY STOP THE SCRIPT (it loops indefinitely)
if exists (select 1 from Version where component = '${component}')
begin
	if not exists (select 1 from Version where databaseVersion like '${previous}' and component = '${component}')
	begin
		RAISERROR ('Incorrect database version, please check your version before running this script!', 17, 1);
		while 1 = 1
		begin
			WaitFor Delay '00:00:05'
		end
	end
	
	update Version set databaseVersion='${next}' where databaseVersion like '${previous}' and component = '${component}'
	end else 
begin
    insert into Version (databaseVersion,component) values ('${next}', '${component}')
end

go
-- End of the Version management - Check (DO NOT REMOVE THIS COMMENT)


