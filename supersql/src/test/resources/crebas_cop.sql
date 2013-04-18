create table CopRule
(
   pk                         varchar2(128)          not null,
  ownerType                   varchar2(32)           not null,
  ownerId                     varchar2(32)           not null,
  targetType                  varchar2(32)           not null,
  targetId                   varchar2(128)          not null,
  exchangeId                 varchar2(32)           not null,
  currency                   varchar2(8)            not null,
  optionalCriteriaId          varchar2(4000)         null,
  ruleId                     varchar2(128)          not null,
  priority                   int                    default 0 null,
  lvl                        int                    default 0 not null,
  serializedRuleParams       varchar2(4000)        not null,
  equivalenceClass           varchar2(32)          null,
  rulesSet 				  varchar2(64) 			null,
  constraint PK_COPRULECONFIG primary key (pk)
)
/


create table CopAdminAudit
(
  pk                          varchar2(128)           not null,
  dateAndTime                 TIMESTAMP(3)            not null,
  adminId                     varchar2(32)            not null,
  actionType                  varchar2(12)            not null,
  rulePK                      varchar2(128)           not null,
  ruleId                      varchar2(128)           not null,
  ownerType                   varchar2(32)            not null,
  ownerId                     varchar2(32)            not null,
  targetType                  varchar2(32)            not null,
  targetId                    varchar2(128)           not null,
  exchangeId                  varchar2(32)            not null,
  currency                    varchar2(8)             not null,
  optionalCriteriaId          varchar2(4000)          null,
  priority                    int                     not null,
  lvl                         int                     not null,
  serializedRuleParams        varchar2(4000)          not null,
  equivalenceClass            varchar2(32)          null,
  rulesSet 				  	  varchar2(64) 			null,
  constraint PK_COPADMINLOG primary key (pk)
)
/


create table CopBypassAudit
(
 pk                               varchar2(128)           not null,
 dateAndTime                      TIMESTAMP(3)            not null,
 userId                           varchar2(32)            not null,
 orderDescription                 varchar2(4000)          not null,
 rulePK                           varchar2(128)           not null,
 ruleId                           varchar2(128)           not null,
 ownerType                        varchar2(32)            not null,
 ownerId                          varchar2(32)            not null,
 targetType                       varchar2(32)            not null,
 targetId                         varchar2(128)           not null,
 exchangeId                       varchar2(32)            not null,
 currency                         varchar2(8)             not null,
 optionalCriteriaId               varchar2(4000)          null,
 priority                         int                     not null,
 lvl                              int                     not null,
 serializedRuleParams             varchar2(4000)          not null,
 equivalenceClass                 varchar2(32)            null,
 rulesSet 				  	  	  varchar2(64) 			  null,
 constraint PK_COPBYPASSAUDIT primary key (pk)
) 
/


create table CopPreviousDayExecutedAmount
(
 imsId varchar2(64),
 side int,
 amount float,
 constraint PK_COPPREVDAYEXECAMNT  primary key (imsId, side)
) 
/

-- this table contains all rules sets used in Cop services connecting to this broker
create table CopRulesSet
(
	pk varchar(64),
	activated int,
	description varchar(32) null,
	constraint PK_COPRULESET primary key (pk)
)
/

create table SafetyRule
(
    id                            VARCHAR2(64)    not null,
    type                          VARCHAR2(128)   not null,
    ruleLevel                     VARCHAR2(16)    not null,
    priority                      INTEGER         not null,
    equivalenceClass              VARCHAR2(64)    null    ,
    rulesSet                      VARCHAR2(64)    null    ,
    criteria                      CLOB            null    ,
    parameters                    CLOB            null    ,
    description                   VARCHAR2(1024)  null    ,    
    constraint PK_SAFETYRULE primary key (id)
)
/

create table SafetyRuleChange
(
    changeId                       VARCHAR2(64)           not null,
    action                         VARCHAR2(16)           not null,
    changeTime                     TIMESTAMP(3)           not null,
    login                          VARCHAR2(64)           not null,
    id                             VARCHAR2(64)           not null,
    type                           VARCHAR2(128)          not null,
    ruleLevel                      VARCHAR2(16)           not null,
    priority                       INTEGER                not null,
    equivalenceClass               VARCHAR2(64)           null    ,
    rulesSet                       VARCHAR2(64)           null    ,
    criteria                       CLOB                   null    ,
    parameters                     CLOB                   null    ,
    description 				   VARCHAR2(1024) 		  null,
    constraint PK_SAFETYCHANGE primary key (changeId)
)
/

create table SafetyRulesSet
(
    id                             VARCHAR2(64)           not null,
    activated                      INTEGER                not null,
    description                    VARCHAR2(1024)         null    ,
    constraint PK_SAFETYRULESSET primary key (id)
)
/

create table SafetyRulesSetChange
(
    changeId                       VARCHAR2(64)           not null,
    action                         VARCHAR2(16)           not null,
    changeTime                     TIMESTAMP(3)           not null,
    login                          VARCHAR2(64)           not null,
    id                             VARCHAR2(64)           not null,
    activated                      INTEGER                not null,
    description                    VARCHAR2(1024)         null    ,
    constraint PK_SAFETYRULESETCHANGE primary key (changeId)
)
/