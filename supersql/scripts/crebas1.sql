-- ============================================================
--   Table : LiffeMktSpreadRuleUlGroup                         
-- ============================================================
create table LiffeMktSpreadRuleUlGroup
(
    ulId                           VARCHAR2(128)          not null,
    marketRole                     VARCHAR2(3)            not null
        constraint CKC_MARKETROLE_LIFFEMKT check (marketRole in ('PMM','RMM','PLP')),
    closePrice                     FLOAT                  null    ,
    ulLiquidity                    VARCHAR2(16)           not null,
    constraint PK_LIFFEMKTSPREADRULEULGROUP primary key (ulId)
)
/

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

-- ============================================================
--   Table : HKFEMktSpreadRuleUlGroup                          
-- ============================================================
create table HKFEMktSpreadRuleUlGroup
(
    ulId                           VARCHAR2(128)          not null,
    ulLiquidity                    VARCHAR2(16)           not null,
    constraint PK_HKFEMKTSPREADRULEULGROUP primary key (ulId)
)
/

-- ============================================================
--   Table : HKFEMktSpreadRuleMinLevel                         
-- ============================================================
create table HKFEMktSpreadRuleMinLevel
(
    ulPrice                        FLOAT                  not null,
    bestSpreadMultiplier           FLOAT                  not null,
    spreadOverhead                 FLOAT                  not null,
    constraint PK_HKFEMKTSPREADRULEMINLEVEL primary key (ulPrice)
)
/

-- ============================================================
--   Table : HKFEMktSpreadRuleLevel                            
-- ============================================================
create table HKFEMktSpreadRuleLevel
(
    ulLiquidity                    VARCHAR2(16)           not null,
    floatingMaturityValueToExpiry  INTEGER                not null,
    floatingMaturityUnitToExpiry   CHAR(1)                not null,
    spreadValue                    FLOAT                  not null,
    spreadUnit                     VARCHAR2(16)           not null,
    bestSpreadMultiplier           FLOAT                  not null,
    constraint PK_HKFEMKTSPREADRULELEVEL primary key (ulLiquidity, floatingMaturityValueToExpiry, floatingMaturityUnitToExpiry)
)
/

-- ============================================================
--   Table : Dividend                                          
-- ============================================================
create table Dividend
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_DIVIDEND check (priceType in ('TRADING','VALIDATION')),
    dividendDate                   TIMESTAMP(3)           not null,
    expressionMode                 VARCHAR2(64)           default 'F' not null,
    dividendValue                  FLOAT                  not null,
    isEstimated                    NUMBER(1)              not null,
    taxFactor                      FLOAT                  not null,
    constraint PK_DIVIDEND primary key (productId, context, priceType, dividendDate, expressionMode)
)
/

-- ============================================================
--   Table : Version                                           
-- ============================================================
create table Version
(
    component                      VARCHAR2(128)          not null,
    databaseVersion                VARCHAR2(32)           default '1.0.0' not null,
    version                        VARCHAR2(32)           null    ,
    environment                    VARCHAR2(32)           null    ,
    constraint PK_VERSION primary key (component)
)
/

-- ============================================================
--   Table : MarketUnderlyingAddress                           
-- ============================================================
create table MarketUnderlyingAddress
(
    imsId                          VARCHAR2(32)           not null,
    marketUlId                     VARCHAR2(128)          not null,
    ulId                           VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            null    ,
    importDerivatives              NUMBER(1)              default 0 null    ,
    importUnderlying               NUMBER(1)              default 0 null    ,
    importAsIndex                  NUMBER(1)              default 0 null    ,
    importAsDeltaOne               NUMBER(1)              default 0 null    ,
    deliveryType                   VARCHAR2(32)           null    ,
    strategyRegex                  VARCHAR2(128)          null    ,
    constraint PK_MARKETUNDERLYINGADDRESS primary key (imsId, marketUlId)
)

