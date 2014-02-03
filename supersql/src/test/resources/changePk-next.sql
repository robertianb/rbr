-- ============================================================
--   Table : LevelledMktSpreadRule                             
-- ============================================================
create table LevelledMktSpreadRule
(
    ulId                           VARCHAR2(128)          not null,
    bidPrice                       FLOAT                  not null,
    maturityValue                  INTEGER                not null,
    maturityUnit                   VARCHAR2(1)            not null
        constraint CKC_MATURITYUNIT_LEVELLED check (maturityUnit in ('d','w','m','y','D','W','M','Y')),
    spreadUnit                     VARCHAR2(16)           not null,
    spreadValue                    FLOAT                  not null,
    constraint PK_LEVELLEDMKTSPREADRULE primary key (ulId, bidPrice, maturityValue, maturityUnit)
)
/
