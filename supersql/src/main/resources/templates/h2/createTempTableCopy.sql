-- Create TEMPORARY table ${targetTableName} from ${sourceTableName}
CREATE GLOBAL TEMPORARY TABLE  ${targetTableName} ON COMMIT PRESERVE ROWS  AS SELECT * FROM ${sourceTableName};
