
create table AuditTrail
(
    auditTrailId   VARCHAR2(512)          not null,
    tableName      VARCHAR2(65)           not null,
    primaryKey     VARCHAR2(255)          not null,
    action         CHAR(1)                not null,
    timestamp      TIMESTAMP(3)           not null,
    field          VARCHAR2(64)           not null,
    userId         VARCHAR2(80)           not null,
    previousValue  VARCHAR2(1024)          null    ,
    newValue       VARCHAR2(1024)          null    ,
    constraint PK_AUDITTRAIL primary key (auditTrailId)
)
/

create table AuditConfig
(
    tableName                      VARCHAR2(65)           not null,
    isAuditable                   NUMBER(1)              null    ,
    constraint PK_AUDITCONFIG primary key (tableName)
)
/
