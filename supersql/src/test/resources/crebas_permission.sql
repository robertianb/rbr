create table RightGroupDefinition
(
    groupId       VARCHAR2(80)           not null,
    caption       VARCHAR2(255)          null    ,
    priority      INTEGER                not null,
    enabled       INTEGER                default 1 null    
        constraint CKC_ENABLED_RightGroup check (enabled is null or (enabled in (0,1))),
    constraint PK_RightGroup primary key (groupId)
)
/

create table RightGroupRelationship
(
    sonId         VARCHAR2(80)           not null,
    parentId      VARCHAR2(80)           not null,
    constraint PK_RightRelationship primary key (sonId, parentId)
)
/

create table RightRule
(
    typeId        VARCHAR2(64)          not null,
    groupId       VARCHAR2(80)          not null,
    permission    VARCHAR2(64)          not null,
    entity        VARCHAR2(392)         not null,
    allow         INTEGER               default 0 null    
        constraint CKC_ALLOW_RightRule check (allow is null or (allow in (0,1))),
    constraint PK_RightRule primary key (typeId, groupId, permission, entity)
)
/

