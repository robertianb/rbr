-- ============================================================
--   Table : LiffeMktSpreadRuleUlGroup                         
-- ============================================================
create table LiffeMktSpreadRuleUlGroup
(
    ulId                           VARCHAR2(128)          not null,
    marketRole                     VARCHAR2(3)            not null 
        constraint CKC_MARKETROLE_LIFFEMKT check (marketRole in ('PMM','RMM','PLP')),

    closePrice                     FLOAT                  null    ,
    ulLiquidity                    VARCHAR2(16)           not null
    
)
/

