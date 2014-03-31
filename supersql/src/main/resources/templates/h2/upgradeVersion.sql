-- Update database version
update Version set databaseVersion='${next}' where databaseVersion like '${previous}' and component = '${component}';
