-- ============================================================
--   Table : ConfigServerAudit                                 
-- ============================================================
create table ConfigServerAudit
(
    auditTrailId         VARCHAR2(512)          not null,
    tableName            VARCHAR2(65)           not null,
    primaryKey           VARCHAR2(255)          not null,
    action               CHAR(1)                not null
        constraint CKC_ACTION_CONFIGSE check (action in ('I','U','D')),
    timestamp            TIMESTAMP(3)           not null,
    field                VARCHAR2(64)           not null,
    userId               VARCHAR2(80)           not null,
    previousValue        VARCHAR2(1024)         null    ,
    newValue             VARCHAR2(1024)         null    ,
    constraint PK_CONFIGSERVERAUDIT primary key (auditTrailId)
)
/

-- ============================================================
--   Table : DataChangeEventConfigServer                       
-- ============================================================
create table DataChangeEventConfigServer
(
    tableName            VARCHAR2(32)           not null,
    primaryKey           VARCHAR2(255)          not null,
    dataChangeType       CHAR(1)                not null
        constraint CKC_DATACHANGETYPE_CONFDATA check (dataChangeType in ('U','I','D')),
    timestamp            TIMESTAMP(3)                not null,
    isHandled            INTEGER                not null,
    constraint PK_DATACHANGEEVENTCONFIGSERVER primary key (tableName, primaryKey, dataChangeType)
)
/

-- ============================================================
--   Table : ProcessAlias                                      
-- ============================================================
create table ProcessAlias
(
    fromDependency       VARCHAR2(20)           not null,
    toDependency         VARCHAR2(20)           null    ,
    constraint PK_PROCESSALIAS primary key (fromDependency)
)
/

-- ============================================================
--   Table : ProcessInstance                                   
-- ============================================================
create table ProcessInstance
(
    processId            VARCHAR2(128)          not null,
    processType          VARCHAR2(128)          null    ,
    parent               VARCHAR2(128)          null    ,
    groupId              VARCHAR2(128)          null    ,
    constraint PK_PROCESSINSTANCE primary key (processId)
)
/

-- ============================================================
--   Table : ProcessType                                       
-- ============================================================
create table ProcessType
(
    processTypeName      VARCHAR2(128)          not null,
    constraint PK_PROCESSTYPE primary key (processTypeName)
)
/

-- ============================================================
--   Table : ProcessMode                                       
-- ============================================================
create table ProcessMode
(
    processMode          VARCHAR2(128)          not null,
    constraint PK_PROCESSMODE primary key (processMode)
)
/

-- ============================================================
--   Table : ProcessRegion                                     
-- ============================================================
create table ProcessRegion
(
    processLocalization  VARCHAR2(128)          not null,
    constraint PK_PROCESSREGION primary key (processLocalization)
)
/

-- ============================================================
--   Table : ProcessCurrentMode                                
-- ============================================================
create table ProcessCurrentMode
(
    processLocalization  VARCHAR2(128)          not null,
    processId            VARCHAR2(128)          not null,
    processMode          VARCHAR2(128)          not null,
    constraint PK_PROCESSCURRENTMODE primary key (processLocalization, processId)
)
/

-- ============================================================
--   Table : ProcessLocalization                               
-- ============================================================
create table ProcessLocalization
(
    processMode          VARCHAR2(128)          not null,
    processLocalization  VARCHAR2(128)          not null,
    processId            VARCHAR2(128)          not null,
    failoverMode         VARCHAR2(128)          null    ,
    isRunning            INTEGER                null    ,
    prefix               VARCHAR2(128)          null    ,
    autoFailover         INTEGER                null    ,
    isDisabled			 NUMBER(1)				null	,
    constraint PK_PROCESSLOCALIZATION primary key (processMode, processLocalization, processId)
)
/

-- ============================================================
--   Table : ProcessProperty                                   
-- ============================================================
create table ProcessProperty
(
    processTypeName      VARCHAR2(128)          not null,
    processPropertyId    VARCHAR2(128)          not null,
    isDependency         INTEGER                null    ,
    constraint PK_PROCESSPROPERTY primary key (processTypeName, processPropertyId)
)
/
-- ============================================================
--   Table : ProcessPropertyValue                              
-- ============================================================
create table ProcessPropertyValue
(
    processId            VARCHAR2(128)          not null,
    processLocalization  VARCHAR2(128)          not null,
    processMode          VARCHAR2(128)          not null,
    processPropertyId    VARCHAR2(128)          not null,
    propertyValue        VARCHAR2(2000)         null    ,
    constraint PK_PROCESSPROPERTYVALUE primary key (processId, processLocalization, processMode, processPropertyId)
)
/

-- ============================================================
--   Table : ProcessPropertyMapping                            
-- ============================================================
create table ProcessPropertyMapping
(
    origProcessTypeName  VARCHAR2(128)          not null,
    destProcessTypeName  VARCHAR2(128)          not null,
    origPropertyId       VARCHAR2(128)          not null,
    destPropertyId       VARCHAR2(128)          null    ,
    constraint PK_PROCESSPROPERTYMAPPING primary key (origProcessTypeName, destProcessTypeName, origPropertyId)
)
/

-- ============================================================
--   Table : ProcessCron                                       
-- ============================================================
create table ProcessCron
(
    processTypeName      VARCHAR2(128)          not null,
    processMode          VARCHAR2(128)          not null,
    processLocalization  VARCHAR2(128)          not null,
    action               CHAR(1)                not null,
    monday               TIMESTAMP(3)                null    ,
    tuesday              TIMESTAMP(3)                null    ,
    wednesday            TIMESTAMP(3)                null    ,
    thursday             TIMESTAMP(3)                null    ,
    friday               TIMESTAMP(3)                null    ,
    saturday             TIMESTAMP(3)                null    ,
    sunday               TIMESTAMP(3)                null    ,
    disabled             INTEGER                null    ,
    constraint PK_PROCESSCRON primary key (processTypeName, processMode, processLocalization, action)
)
/

-- ============================================================
--   Table : ProcessActionCommand                              
-- ============================================================
create table ProcessActionCommand
(
    processTypeName      VARCHAR2(128)          not null,
    action               CHAR(1)                not null,
    cmd                  VARCHAR2(255)          null    ,
    constraint PK_PROCESSACTIONCOMMAND primary key (processTypeName, action)
)
/

-- ============================================================
--   Table : ProcessGroup                                       
-- ============================================================
create table ProcessGroup
(
    groupId              VARCHAR2(128)          not null,
    constraint PK_PROCESSGROUP primary key (groupId)
)
/