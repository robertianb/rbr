package supersql.ast.actions;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 18/01/13
 * Time: 08:31
 * To change this template use File | Settings | File Templates.
 */
public class ActionCodes {
    public static final String UPGRADE_VERSION = "upgradeVersion";
    public static final String CREATE_TABLE = "createTable";
    public static final String DROP_TABLE = "dropTable";
    public static final String ADD_COLUMNS = "addColumns";
    public static final String ADD_COLUMN = "addColumn";
    public static final String DELETE_COLUMNS = "deleteColumns";
    public static final String DELETE_COLUMN = "deleteColumn";
    public static final String DELETE_COLUMN_WITH_DEFAULT_VALUE = "deleteColumnWithDefaultValue";
    public static final String ADD_COLUMN_WITHOUT_DEFAULT_VALUE = "addColumnWithoutDefaultValue";
    public static final String RENAME_COLUMN = "renameColumn";
    public static final String MODIFY_COLUMN = "modifyColumn";
    public static final String VOID_ACTION = "voidAction";
    public static final String COPY_TABLE_CONTENT = "copyTableContent";
    public static final String CREATE_TABLE_COPY = "createTableCopy";
    public static final String DELETE_ALL = "delete";
    public static final String CHANGE_PRIMARY_KEY = "changePrimaryKey";
    public static final String RENAME_COLUMN_WITHOUT_DEFAULT_VALUE = "renameColumnWithoutDefaultValue";
    public static final String MODIFY_COLUMN_WITH_DEFAULT_VALUE = "modifyColumnWithDefaultValue";
}
