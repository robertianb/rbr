-- Update database version
update Version set databaseVersion='${next}' where databaseVersion='${previous}' and component = '${component}';
