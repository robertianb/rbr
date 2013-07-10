-- Version management - Check
-- IF THE VERSION IS NOT THE EXPECTED, YOU SHOULD MANUALLY STOP THE SCRIPT (it loops indefinitely)
declare l_version VARCHAR2(100);
begin
select databaseVersion into l_version from Version where databaseVersion like '${previous}' and component = '${component}';
exception
     when no_data_found then
     begin
         DBMS_OUTPUT.PUT_LINE('Incorrect database version, please check your version before running this script!');
         LOOP
        dbms_lock.sleep(5);
        END LOOP;
     end;
end;
/
-- End of the Version management - Check (DO NOT REMOVE THIS comments)

update Version set databaseVersion='${next}' where databaseVersion = '${previous}' and component = '${component}'
/

