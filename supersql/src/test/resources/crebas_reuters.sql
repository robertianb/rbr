-- ============================================================ 
--   Table : ReutersExchange                                    
-- ============================================================ 
create table ReutersExchange
(
    exchangeId           varchar(10)            not null,
    description          varchar(80)            null    ,
    idsource             int                    null    ,
    destination          varchar(80)            null    ,
    constraint PK_REUTERSEXCHANGE primary key (exchangeId)
)
/

-- ============================================================ 
--   Table : ReutersStatus                                      
-- ============================================================ 
create table ReutersStatus
(
    statusLabel          varchar(32)            not null,
    statusId             int                    null    ,
    description          varchar(80)            null    ,
    constraint PK_REUTERSSTATUS primary key (statusLabel)
)
/

-- ============================================================ 
--   Table : ReutersGroup                                       
-- ============================================================ 
create table ReutersGroup
(
    groupId              varchar(32)            not null,
    lastFieldName        varchar(132)           null    ,
    openFieldName        varchar(132)           null    ,
    closeFieldName       varchar(132)           null    ,
    highFieldName        varchar(132)           null    ,
    lowFieldName         varchar(132)           null    ,
    bidFieldName         varchar(132)           null    ,
    askFieldName         varchar(132)           null    ,
    retrieveDepth        int                    null    ,
    lastQFieldName       varchar(132)           null    ,
    openQFieldName       varchar(132)           null    ,
    closeQFieldName      varchar(132)           null    ,
    highQFieldName       varchar(132)           null    ,
    lowQFieldName        varchar(132)           null    ,
    bidQFieldName        varchar(132)           null    ,
    askQFieldName        varchar(132)           null    ,
    chainingType         char(1)                null    
        constraint CKC_CHAININGTYPE_REUTERSG check (chainingType in ('R','M','C','D')),
    prefix               varchar(132)           null    ,
    totalQFieldName      varchar(132)           null    ,
    lastTimeFieldName    varchar(132)           null    ,
    vwapFieldName        varchar(132)           null    ,
    lotsizeFieldName     varchar(132)           null    ,
    tickFieldName        varchar(132)           null    ,
    statusFieldName      varchar(132)           null    ,
    strikeFieldName      varchar(132)           null    ,
    summaryLinkRank      int                    null    ,
    depthLinkRank        int                    null    ,
    suffix               varchar(132)           null    ,
    usedDynamicTemplate  int                    null    ,
    constraint PK_REUTERSGROUP primary key (groupId)
)
/

-- ============================================================ 
--   Table : ReutersParam                                       
-- ============================================================ 
create table ReutersParam
(
    productId            varchar(128)           not null,
    groupId              varchar(32)            null    ,
    ric                  varchar(80)            not null,
    service              varchar(80)            not null,
    imsId                varchar(32)            null    ,
    productType          varchar(32)            null    ,
    exchangeId           varchar(10)            null    ,
    tickRuleId           varchar(32)            null    ,
    customId             varchar(80)            null    ,
    destination          varchar(80)            null    ,
    parent               varchar(80)            null    ,
    constraint PK_REUTERSPARAM primary key (productId)
)
/

-- ============================================================ 
--   Table : ReutersUnderlyingFilter                            
-- ============================================================ 
create table ReutersUnderlyingFilter
(
    ric                  varchar(80)            not null,
    service              varchar(80)            not null,
    groupId              varchar(32)            not null,
    ulid                 varchar(32)            not null,
    exchangeId           varchar(10)            not null,
    shortName            varchar(32)            not null,
    productType          varchar(1)             not null,
    imsId                varchar(32)            not null,
    tickRuleId           varchar(32)            not null,
    destination          varchar(80)            null    ,
    constraint PK_REUTERSUNDERLYINGFILTER primary key (ric, service)
)
/
