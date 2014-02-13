-- ============================================================
--   Table : LiffeMktSpreadRuleUlGroup                         
-- ============================================================
create table LiffeMktSpreadRuleUlGroup
(
    ulId                           VARCHAR2(128)          not null,
    marketRole                     VARCHAR2(3)            not null
        constraint CKC_MARKETROLE_LIFFEMKT check (
            marketRole in ('PMM','RMM','PLP')),
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
    spreadOverhead                 INTEGER                not null,
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
    maturityTime                   VARCHAR2(6)            null    ,
    maturityTimezone               VARCHAR2(125)          null    ,
    marketSpreadClass              VARCHAR2(128)          null    ,
    constraint PK_MARKETUNDERLYINGADDRESS primary key (imsId, marketUlId)
)
/

-- ============================================================
--   Table : DataChangeEvent                                   
-- ============================================================
create table DataChangeEvent
(
    tableName                      VARCHAR2(32)           not null,
    primaryKey                     VARCHAR2(255)          not null,
    dataChangeType                 VARCHAR2(1)            not null
        constraint CKC_DATACHANGETYPE_DATACHAN check (dataChangeType in ('I','U','D')),
    isHandled                      NUMBER(1)              not null,
    eventTime                      TIMESTAMP(3)           null    ,
    constraint PK_DATACHANGEEVENT primary key (tableName, primaryKey, dataChangeType)
)
/

-- ============================================================
--   Table : AutoHedge                                         
-- ============================================================
create table AutoHedge
(
    ulId                           VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    isActive                       NUMBER(1)              null    ,
    imsId                          VARCHAR2(32)           null    ,
    hedgeInstrumentId              VARCHAR2(128)          null    ,
    hedgeFactor                    FLOAT                  default 1 null    ,
    roundingThreshold              FLOAT                  default 0.51 null    
        constraint CKC_ROUNDINGTHRESHOLD_AUTOHEDG check (roundingThreshold is null or (roundingThreshold between 0.51 and 1)),
    roundingStepInLots             INTEGER                default 1 null    ,
    hedgePriceMode                 VARCHAR2(16)           null    
        constraint CKC_HEDGEPRICEMODE_AUTOHEDG check (hedgePriceMode is null or (hedgePriceMode in ('RELATIVE','ABSOLUTE'))),
    maxDaySell                     NUMBER(15)             null    ,
    maxDayBuy                      NUMBER(15)             null    ,
    maxOrderSize                   INTEGER                null    ,
    sendThreshold                  INTEGER                default 1 null    ,
    maxShiftFromLast               FLOAT                  null    ,
    maxShiftType                   VARCHAR2(16)           null    ,
    isAutoStart                    NUMBER(1)              default 0 null    ,
    useMultipleInstruments         NUMBER(1)              default 0 null    ,
    ulSpotId                       VARCHAR2(32)           null    ,
    hedgeContext                   VARCHAR2(32)           null    ,
    capOrderSize                   INTEGER                null    ,
    autoStartUserId                VARCHAR2(64)           null    ,
    minVisible                     FLOAT                  null    ,
    halfCorridor                   FLOAT                  null    ,
    hedgeUnitType                  VARCHAR2(16)           null    ,
    sendThresholdCorrectionType    VARCHAR2(32)           null    ,
    sendThresholdCorrection        FLOAT                  null    ,
    sendThresholdSpotFieldId       VARCHAR2(32)           null    ,
    allowanceType                  VARCHAR2(32)           null    ,
    allowance                      FLOAT                  null    ,
    properties                     VARCHAR2(512)          null    ,
    lowTriggerAmount               FLOAT                  null    ,
    highTriggerAmount              FLOAT                  null    ,
    minimumOrderAmount             FLOAT                  null    ,
    amountCurrency                 VARCHAR2(3)            null    ,
    priceType                      VARCHAR2(32)           null    ,
    hedgeBuyStrategy               VARCHAR2(16)           null    ,
    hedgeSellStrategy              VARCHAR2(16)           null    ,
    aggregationStyle               VARCHAR2(16)           null    ,
    constraint PK_AUTOHEDGE primary key (ulId, portfolioId)
)
/

-- ============================================================
--   Table : Memorandum                                        
-- ============================================================
create table Memorandum
(
    noteId                         VARCHAR2(128)          not null,
    note                           VARCHAR2(1204)         null    ,
    constraint PK_MEMORANDUM primary key (noteId)
)
/

-- ============================================================
--   Table : CopFxRate                                         
-- ============================================================
create table CopFxRate
(
    rateId                         VARCHAR2(7)            not null,
    rate                           FLOAT                  null    ,
    constraint PK_COPFXRATE primary key (rateId)
)
/

-- ============================================================
--   Table : SpotProcessorFunction                             
-- ============================================================
create table SpotProcessorFunction
(
    functionId                     VARCHAR2(128)          not null,
    functionDescription            NVARCHAR2(256)         null    ,
    functionClass                  VARCHAR2(255)          null    ,
    constraint PK_SPOTPROCESSORFUNCTION primary key (functionId)
)
/

-- ============================================================
--   Table : AutoBiasRule                                      
-- ============================================================
create table AutoBiasRule
(
    autoBiasRuleId                 VARCHAR2(64)           not null,
    nbDays                         INTEGER                not null,
    interpolationMode              VARCHAR2(16)           not null
        constraint CKC_INTERPOLATIONMODE_AUTOBIAS check (interpolationMode in ('LINEAR','HYPERBOLIC')),
    targetType                     VARCHAR2(16)           null    ,
    targetValue                    FLOAT                  not null,
    constraint PK_AUTOBIASRULE primary key (autoBiasRuleId, nbDays)
)
/

-- ============================================================
--   Table : ExecutionLegSnapshot                              
-- ============================================================
create table ExecutionLegSnapshot
(
    tradeId                        VARCHAR2(128)          not null,
    productId                      VARCHAR2(128)          not null,
    legProductId                   VARCHAR2(128)          not null,
    referenceSpot                  FLOAT                  null    ,
    spot                           FLOAT                  null    ,
    theoriticalPrice               FLOAT                  null    ,
    delta                          FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    productToUlForex               FLOAT                  null    ,
    constraint PK_EXECUTIONLEGSNAPSHOT primary key (tradeId, productId, legProductId)
)
/

-- ============================================================
--   Table : VolatilityHistoric                                
-- ============================================================
create table VolatilityHistoric
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_VOLATILI check (priceType in ('TRADING','VALIDATION')),
    maturity                       TIMESTAMP(3)           not null,
    strike                         FLOAT                  not null,
    snapshotDate                   TIMESTAMP(3)           not null,
    volatility                     FLOAT                  null    ,
    spotRef                        FLOAT                  null    ,
    maturityValue                  INTEGER                null    ,
    maturityUnit                   VARCHAR2(1)            null    
        constraint CKC_MATURITYUNIT_VOLATILI check (maturityUnit is null or (maturityUnit in ('d','w','m','y','D','W','M','Y'))),
    constraint PK_VOLATILITYHISTORIC primary key (productId, context, priceType, maturity, strike, snapshotDate)
)
/

-- ============================================================
--   Table : Properties                                        
-- ============================================================
create table Properties
(
    propertyId                     VARCHAR2(128)          not null,
    propertyValue                  VARCHAR2(256)          null    ,
    constraint PK_PROPERTIES primary key (propertyId)
)
/

-- ============================================================
--   Table : MarketSchedule                                    
-- ============================================================
create table MarketSchedule
(
    imsId                          VARCHAR2(125)          not null,
    openTime                       VARCHAR2(125)          not null,
    closeTime                      VARCHAR2(125)          not null,
    timeZone                       VARCHAR2(125)          null    ,
    constraint PK_MARKETSCHEDULE primary key (imsId, openTime)
)
/

-- ============================================================
--   Table : SnapConfig                                        
-- ============================================================
create table SnapConfig
(
    productId                      VARCHAR2(125)          not null,
    imsId                          VARCHAR2(125)          not null,
    context                        VARCHAR2(125)          not null,
    spreadName                     VARCHAR2(125)          not null,
    period                         INTEGER                null    ,
    isActivate                     NUMBER(1)              null    ,
    constraint PK_SNAPCONFIG primary key (productId, imsId, context, spreadName)
)
/

-- ============================================================
--   Table : SnapPrice                                         
-- ============================================================
create table SnapPrice
(
    productId                      VARCHAR2(125)          not null,
    imsId                          VARCHAR2(125)          not null,
    context                        VARCHAR2(64)           not null,
    snapDate                       TIMESTAMP(3)           not null,
    scheduledOpenTime              VARCHAR2(125)          not null,
    spreadName                     VARCHAR2(125)          not null,
    snapOpenTime                   TIMESTAMP(3)           null    ,
    openSpot                       FLOAT                  null    ,
    openPrice                      FLOAT                  null    ,
    scheduledCloseTime             VARCHAR2(125)          null    ,
    snapCloseTime                  TIMESTAMP(3)           null    ,
    closeSpot                      FLOAT                  null    ,
    closePrice                     FLOAT                  null    ,
    constraint PK_SNAPPRICE primary key (productId, imsId, context, snapDate, scheduledOpenTime, spreadName)
)
/

-- ============================================================
--   Table : AutoHedgeProxy                                    
-- ============================================================
create table AutoHedgeProxy
(
    proxyId                        VARCHAR2(128)          not null,
    ulId                           VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    hedgeInstrumentId              VARCHAR2(128)          null    ,
    imsId                          VARCHAR2(32)           null    ,
    beta                           FLOAT                  default 1 null    ,
    proxySpotId                    VARCHAR2(32)           null    ,
    proxyUlSpotId                  VARCHAR2(32)           null    ,
    constraint PK_AUTOHEDGEPROXY primary key (proxyId, ulId, portfolioId)
)
/

-- ============================================================
--   Table : DealingTicket                                     
-- ============================================================
create table DealingTicket
(
    ticketId                       VARCHAR2(64)           not null,
    timestamp                      TIMESTAMP(3)           null    ,
    clientName                     VARCHAR2(64)           null    ,
    productId                      VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_DEALINGT check (way is null or (way in ('2','B','S'))),
    price                          FLOAT                  null    ,
    currencyId                     VARCHAR2(3)            null    ,
    quantity                       INTEGER                null    ,
    consideration                  VARCHAR2(64)           null    ,
    assigneeName                   VARCHAR2(64)           null    ,
    isBlocked                      NUMBER(1)              null    ,
    isLocked                       NUMBER(1)              null    ,
    validity                       VARCHAR2(32)           null    ,
    executedQuantity               FLOAT                  null    ,
    status                         VARCHAR2(32)           null    ,
    label1                         VARCHAR2(64)           null    ,
    label2                         VARCHAR2(64)           null    ,
    label3                         VARCHAR2(64)           null    ,
    label4                         VARCHAR2(64)           null    ,
    constraint PK_DEALINGTICKET primary key (ticketId)
)
/

-- ============================================================
--   Table : PerfMonitoringEvent                               
-- ============================================================
create table PerfMonitoringEvent
(
    eventId                        VARCHAR2(32)           not null,
    imsId                          VARCHAR2(32)           null    ,
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    ruleId                         VARCHAR2(128)          null    ,
    startTime                      TIMESTAMP(3)           null    ,
    endTime                        TIMESTAMP(3)           null    ,
    description                    VARCHAR2(1024)         null    ,
    contactedByEmail               NUMBER(1)              null    ,
    contactedByPhone               NUMBER(1)              null    ,
    contactName                    VARCHAR2(128)          null    ,
    constraint PK_PERFMONITORINGEVENT primary key (eventId)
)
/

-- ============================================================
--   Table : MarketMakingPerformance                           
-- ============================================================
create table MarketMakingPerformance
(
    imsId                          VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    onMarket                       NUMBER(1)              null    ,
    performance                    FLOAT                  null    ,
    constraint PK_MARKETMAKINGPERFORMANCE primary key (imsId, productId, context)
)
/

-- ============================================================
--   Table : PricingDataVersion                                
-- ============================================================
create table PricingDataVersion
(
    context                        VARCHAR2(32)           not null,
    dataType                       VARCHAR2(16)           not null
        constraint CKC_DATATYPE_PRICINGD check (dataType in ('VOLATILITY','DIVIDEND','RATE','REPO','YIELD','TAX_CREDIT')),
    ulId                           VARCHAR2(128)          not null,
    latestVersion                  VARCHAR2(32)           null    ,
    latestTimestamp                TIMESTAMP(3)           null    ,
    validationVersion              VARCHAR2(32)           default 'VALIDATION' null    ,
    validationTimestamp            TIMESTAMP(3)           null    ,
    tradingVersion                 VARCHAR2(32)           default 'TRADING' null    ,
    tradingTimestamp               TIMESTAMP(3)           null    ,
    constraint PK_PRICINGDATAVERSION primary key (context, dataType, ulId)
)
/

-- ============================================================
--   Table : FeesRule                                          
-- ============================================================
create table FeesRule
(
    ruleId                         VARCHAR2(16)           not null,
    feesSource                     VARCHAR2(16)           null    ,
    mic                            VARCHAR2(32)           null    ,
    productType                    VARCHAR2(16)           default '*' null    ,
    ulId                           VARCHAR2(128)          null    ,
    productId                      VARCHAR2(128)          null    ,
    userId                         VARCHAR2(64)           default '*' null    ,
    imsId                          VARCHAR2(32)           null    ,
    occurrence                     VARCHAR2(16)           null    ,
    orderLiquidity                 VARCHAR2(16)           null    ,
    isEnabled                      NUMBER(1)              null    ,
    description                    VARCHAR2(256)          null    ,
    feesType                       VARCHAR2(16)           null    ,
    feesValue                      FLOAT                  null    ,
    currencyId                     VARCHAR2(3)            null    ,
    manualRate                     FLOAT                  null    ,
    useManualRate                  NUMBER(1)              null    ,
    cap                            FLOAT                  null    ,
    floor                          FLOAT                  null    ,
    constraint PK_FEESRULE primary key (ruleId)
)
/

-- ============================================================
--   Table : PerformanceBiasRule                               
-- ============================================================
create table PerformanceBiasRule
(
    performanceBiasRuleId          VARCHAR2(100)          not null,
    nbDays                         INTEGER                not null,
    slope                          FLOAT                  null    ,
    constraint PK_PERFORMANCEBIASRULE primary key (performanceBiasRuleId, nbDays)
)
/

-- ============================================================
--   Table : PerformanceBiasHistory                            
-- ============================================================
create table PerformanceBiasHistory
(
    productId                      VARCHAR2(135)          not null,
    context                        VARCHAR2(32)           not null,
    owner                          VARCHAR2(255)          not null,
    biasDate                       TIMESTAMP(3)           not null,
    biasValue                      FLOAT                  null    ,
    constraint PK_PERFORMANCEBIASHISTORY primary key (productId, context, owner, biasDate)
)
/

-- ============================================================
--   Table : TradeReportingCustomFields                        
-- ============================================================
create table TradeReportingCustomFields
(
    mic                            VARCHAR2(32)           not null,
    className                      VARCHAR2(512)          null    ,
    constraint PK_TRADEREPORTINGCUSTOMFIELDS primary key (mic)
)
/

-- ============================================================
--   Table : PoserConfiguration                                
-- ============================================================
create table PoserConfiguration
(
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    productId                      VARCHAR2(128)          not null,
    spotId                         VARCHAR2(128)          null    ,
    spotFieldName                  VARCHAR2(64)           null    ,
    referenceInstrument            VARCHAR2(128)          null    ,
    constraint PK_POSERCONFIGURATION primary key (context, productId)
)
/

-- ============================================================
--   Table : PendingExecution                                  
-- ============================================================
create table PendingExecution
(
    generatedId                    VARCHAR2(128)          not null,
    execType                       VARCHAR2(1)            null    ,
    tradeId                        VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           null    ,
    brokerExchangeId               VARCHAR2(64)           null    ,
    mic                            VARCHAR2(32)           null    ,
    userId                         VARCHAR2(64)           null    ,
    imsId                          VARCHAR2(32)           null    ,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_PENDINGE check (way is null or (way in ('B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            not null
        constraint CKC_EXECUTIONSTATE_PENDINGE check (executionState in ('n','c','u')),
    productId                      VARCHAR2(128)          default '*' null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    hedgeRatio                     FLOAT                  default 1 null    ,
    hedgeUlSpot                    FLOAT                  null    ,
    hedgeProxyUlSpot               FLOAT                  null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    crossTradeId                   VARCHAR2(128)          null    ,
    feesData                       VARCHAR2(128)          null    ,
    isComplete                     NUMBER(1)              null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    productToHedgeForex            FLOAT                  null    ,
    ulId                           VARCHAR2(128)          null    ,
    productToUlForex               FLOAT                  null    ,
    errorMessage                   VARCHAR2(1024)         null    ,
    shortSellType                  VARCHAR2(32)           null    ,
    tradeType                      VARCHAR2(64)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    boughtAmount                   FLOAT                  null    ,
    boughtCurrency                 VARCHAR2(3)            null    ,
    soldAmount                     FLOAT                  null    ,
    soldCurrency                   VARCHAR2(3)            null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    positionEffect                 INTEGER                null    ,
    strategyId                     VARCHAR2(128)          null    ,
    strategyTradeId                VARCHAR2(128)          null    ,
    constraint PK_PENDINGEXECUTION primary key (generatedId)
)
/

-- ============================================================
--   Table : DepthSnapshot                                     
-- ============================================================
create table DepthSnapshot
(
    timestamp                      TIMESTAMP(3)           not null,
    productId                      VARCHAR2(128)          not null,
    way                            VARCHAR2(1)            not null
        constraint CKC_WAY_DEPTHSNA check (way in ('2','B','S')),
    rank                           INTEGER                not null,
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    owner                          VARCHAR2(64)           null    ,
    nbOrders                       INTEGER                null    ,
    constraint PK_DEPTHSNAPSHOT primary key (timestamp, productId, way, rank)
)
/

-- ============================================================
--   Table : SummarySnapshot                                   
-- ============================================================
create table SummarySnapshot
(
    timestamp                      TIMESTAMP(3)           not null,
    productId                      VARCHAR2(128)          not null,
    fieldId                        INTEGER                not null,
    fieldValue                     VARCHAR2(64)           null    ,
    constraint PK_SUMMARYSNAPSHOT primary key (timestamp, productId, fieldId)
)
/

-- ============================================================
--   Table : IMSSecurityGroup                                  
-- ============================================================
create table IMSSecurityGroup
(
    imsGroup                       VARCHAR2(32)           not null,
    imsSecurityGroupId             VARCHAR2(32)           not null,
    hasStream                      NUMBER(1)              null    ,
    canTrade                       NUMBER(1)              null    ,
    canReap                        NUMBER(1)              null    ,
    canUseFunction                 NUMBER(1)              null    ,
    constraint PK_IMSSECURITYGROUP primary key (imsGroup, imsSecurityGroupId)
)
/

-- ============================================================
--   Table : GraphServerConfig                                 
-- ============================================================
create table GraphServerConfig
(
    productId                      VARCHAR2(128)          not null,
    imsId                          VARCHAR2(32)           not null,
    inGraphServer                  NUMBER(1)              null    ,
    inHistoServer                  NUMBER(1)              null    ,
    channels                       VARCHAR2(64)           null    ,
    fields                         VARCHAR2(64)           null    ,
    actionType                     VARCHAR2(32)           null    ,
    fileType                       VARCHAR2(32)           null    ,
    constraint PK_GRAPHSERVERCONFIG primary key (productId, imsId)
)
/

-- ============================================================
--   Table : BasketIndexCalculationRule                        
-- ============================================================
create table BasketIndexCalculationRule
(
    imsId                          VARCHAR2(32)           not null,
    mic                            VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    marketPhase                    VARCHAR2(32)           not null,
    priority                       INTEGER                not null,
    usedField                      VARCHAR2(32)           null    ,
    constraint PK_BASKETINDEXCALCULATIONRULE primary key (imsId, mic, productId, marketPhase, priority)
)
/

-- ============================================================
--   Table : BasketOrderSendingModality                        
-- ============================================================
create table BasketOrderSendingModality
(
    modalityName                   VARCHAR2(32)           not null,
    imsId                          VARCHAR2(32)           not null,
    mic                            VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    marketPhase                    VARCHAR2(32)           not null,
    priority                       INTEGER                null    ,
    validity                       VARCHAR2(32)           null    ,
    buyOrderPriceType              VARCHAR2(32)           null    ,
    buyOrderPctOffset              FLOAT                  null    ,
    buyOrderTickOffset             INTEGER                null    ,
    sellOrderPriceType             VARCHAR2(32)           null    ,
    sellOrderPctOffset             FLOAT                  null    ,
    sellOrderTickOffset            INTEGER                null    ,
    constraint PK_BASKETORDERSENDINGMODALITY primary key (modalityName, imsId, mic, productId, marketPhase)
)
/

-- ============================================================
--   Table : SSMUser                                           
-- ============================================================
create table SSMUser
(
    userId                         VARCHAR2(64)           not null,
    mandatory                      INTEGER                null    ,
    constraint PK_SSMUSER primary key (userId)
)
/

-- ============================================================
--   Table : SpotFieldSnapshot                                 
-- ============================================================
create table SpotFieldSnapshot
(
    spotId                         VARCHAR2(128)          not null,
    fieldId                        VARCHAR2(128)          not null,
    timestamp                      TIMESTAMP(3)           not null,
    fieldValue                     FLOAT                  null    ,
    details                        VARCHAR2(512)          null    ,
    constraint PK_SPOTFIELDSNAPSHOT primary key (spotId, fieldId, timestamp)
)
/

-- ============================================================
--   Table : ContribIMSConfiguration                           
-- ============================================================
create table ContribIMSConfiguration
(
    imsId                          VARCHAR2(32)           not null,
    publisherClassName             VARCHAR2(255)          null    ,
    constraint PK_CONTRIBIMSCONFIGURATION primary key (imsId)
)
/

-- ============================================================
--   Table : ContribFieldMapping                               
-- ============================================================
create table ContribFieldMapping
(
    imsId                          VARCHAR2(32)           not null,
    mappingName                    VARCHAR2(255)          not null,
    hmmReference                   VARCHAR2(255)          null    ,
    fixedValue                     VARCHAR2(255)          null    ,
    typeClass                      VARCHAR2(255)          null    ,
    typeClassParameterValues       VARCHAR2(255)          null    ,
    hmmReferenceParameterValues    VARCHAR2(255)          null    ,
    constraint PK_CONTRIBFIELDMAPPING primary key (imsId, mappingName)
)
/

-- ============================================================
--   Table : MarketStateInterlock                              
-- ============================================================
create table MarketStateInterlock
(
    sourceImsId                    VARCHAR2(32)           not null,
    sourceScope                    VARCHAR2(16)           not null,
    sourceId                       VARCHAR2(128)          not null,
    sourceState                    VARCHAR2(32)           not null,
    targetImsId                    VARCHAR2(32)           not null,
    targetScope                    VARCHAR2(16)           not null,
    targetId                       VARCHAR2(128)          not null,
    targetState                    VARCHAR2(32)           not null,
    constraint PK_MARKETSTATEINTERLOCK primary key (sourceImsId, sourceScope, sourceId, sourceState, targetImsId, targetScope, targetId)
)
/

-- ============================================================
--   Table : StaticDataImportQueue                             
-- ============================================================
create table StaticDataImportQueue
(
    source                         VARCHAR2(16)           not null,
    eventId                        VARCHAR2(64)           not null,
    timeCreated                    TIMESTAMP(3)           null    ,
    timeAdded                      TIMESTAMP(3)           null    ,
    eventType                      VARCHAR2(64)           null    ,
    eventData                      CLOB                   null    ,
    status                         INTEGER                null    ,
    statusMessage                  CLOB                   null    ,
    constraint PK_STATICDATAIMPORTQUEUE primary key (source, eventId)
)
/

-- ============================================================
--   Table : SyntheticSpotProxy                                
-- ============================================================
create table SyntheticSpotProxy
(
    syntheticSpotId                VARCHAR2(128)          not null,
    proxySpotId                    VARCHAR2(128)          not null,
    priority                       INTEGER                null    ,
    spotPolicy                     VARCHAR2(64)           null    ,
    fieldToSnap                    VARCHAR2(128)          null    ,
    description                    VARCHAR2(256)          null    ,
    validationFormula              VARCHAR2(512)          null    ,
    multiplier                     FLOAT                  null    ,
    constraint PK_SYNTHETICSPOTPROXY primary key (syntheticSpotId, proxySpotId)
)
/

-- ============================================================
--   Table : SyntheticSpotHistory                              
-- ============================================================
create table SyntheticSpotHistory
(
    syntheticSpotId                VARCHAR2(128)          not null,
    timestamp                      TIMESTAMP(3)           not null,
    newProxySpotId                 VARCHAR2(128)          not null,
    oldProxySpotId                 VARCHAR2(128)          null    ,
    oldState                       VARCHAR2(32)           null    ,
    oldValidationFormula           VARCHAR2(512)          null    ,
    switchReason                   VARCHAR2(512)          not null,
    newSyntheticFormula            VARCHAR2(512)          not null,
    constraint PK_SYNTHETICSPOTHISTORY primary key (syntheticSpotId, timestamp, newProxySpotId)
)
/

-- ============================================================
--   Table : SyntheticSpotState                                
-- ============================================================
create table SyntheticSpotState
(
    syntheticSpotId                VARCHAR2(128)          not null,
    currentProxySpotId             VARCHAR2(128)          not null,
    timestamp                      TIMESTAMP(3)           not null,
    currentFormula                 VARCHAR2(512)          null    ,
    constraint PK_SYNTHETICSPOTSTATE primary key (syntheticSpotId)
)
/

-- ============================================================
--   Table : StaticDataOutputQueue                             
-- ============================================================
create table StaticDataOutputQueue
(
    eventId                        VARCHAR2(64)           not null,
    timeCreated                    TIMESTAMP(3)           null    ,
    timeExported                   TIMESTAMP(3)           null    ,
    eventType                      VARCHAR2(64)           null    ,
    eventData                      CLOB                   null    ,
    status                         INTEGER                null    ,
    statusMessage                  CLOB                   null    ,
    constraint PK_STATICDATAOUTPUTQUEUE primary key (eventId)
)
/

-- ============================================================
--   Table : StaticDataImportQueueMetadata                     
-- ============================================================
create table StaticDataImportQueueMetadata
(
    eventType                      VARCHAR2(64)           not null,
    fieldName                      VARCHAR2(64)           not null,
    fieldType                      VARCHAR2(128)          null    ,
    format                         VARCHAR2(64)           null    ,
    constraint PK_STATICDATAIMPQMETA primary key (eventType, fieldName)
)
/

-- ============================================================
--   Table : ForexProduct                                      
-- ============================================================
create table ForexProduct
(
    productId                      VARCHAR2(128)          not null,
    forexProductType               VARCHAR2(32)           null    ,
    assetClass                     VARCHAR2(32)           null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    tickRuleId                     VARCHAR2(128)          null    ,
    currency1                      VARCHAR2(3)            null    ,
    currency2                      VARCHAR2(3)            null    ,
    tenor                          VARCHAR2(8)            null    ,
    quantityCurrency               VARCHAR2(3)            null    ,
    currency2Multiplier            FLOAT                  null    ,
    pipsUnit                       FLOAT                  null    ,
    mic                            VARCHAR2(32)           null    ,
    constraint PK_FOREXPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : AntiArbitrageRule                                 
-- ============================================================
create table AntiArbitrageRule
(
    ruleType                       VARCHAR2(32)           not null,
    context                        VARCHAR2(32)           not null,
    ulId                           VARCHAR2(128)          not null,
    maturity                       TIMESTAMP(3)           not null,
    strike                         FLOAT                  not null,
    optionType                     CHAR(1)                not null,
    productType                    VARCHAR2(32)           not null,
    imsId                          VARCHAR2(32)           not null,
    counterparty                   VARCHAR2(256)          not null,
    limitNotional                  FLOAT                  null    ,
    period                         INTEGER                null    ,
    periodUnit                     VARCHAR2(8)            null    ,
    intradayMaxNotional            FLOAT                  null    ,
    shiftClass                     VARCHAR2(128)          null    ,
    shiftParams                    VARCHAR2(512)          null    ,
    lastResetTime                  TIMESTAMP(3)           null    ,
    isActive                       NUMBER(1)              default 1 not null,
    alertLimitNotional             FLOAT                  null    ,
    startTime                      TIMESTAMP(3)           null    ,
    endTime                        TIMESTAMP(3)           null    ,
    timezone                       VARCHAR2(32)           null    ,
    limitUnit                      VARCHAR2(32)           not null,
    constraint PK_ANTIARBITRAGERULE primary key (ruleType, context, ulId, maturity, strike, optionType, productType, imsId, counterparty)
)
/

-- ============================================================
--   Table : ManualPriceOverride                               
-- ============================================================
create table ManualPriceOverride
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    bidPrice                       FLOAT                  null    ,
    askPrice                       FLOAT                  null    ,
    bidMode                        VARCHAR2(16)           null    ,
    askMode                        VARCHAR2(16)           null    ,
    maxShiftFromTheo               FLOAT                  null    ,
    maxShiftFromTheoType           VARCHAR2(16)           null    ,
    resetOnContextChange           NUMBER(1)              null    ,
    constraint PK_MANUALPRICEOVERRIDE primary key (productId, context)
)
/

-- ============================================================
--   Table : PairTradingFastMarketCondition                    
-- ============================================================
create table PairTradingFastMarketCondition
(
    strategyId                     VARCHAR2(128)          not null,
    instrumentType                 VARCHAR2(32)           not null,
    field                          VARCHAR2(32)           not null,
    maxVariationInPct              FLOAT                  null    ,
    constraint PK_PTFASTMARKETCONDITION primary key (strategyId, instrumentType, field)
)
/

-- ============================================================
--   Table : SalesClientConfig                                 
-- ============================================================
create table SalesClientConfig
(
    salesGroupId                   VARCHAR2(64)           not null,
    clientId                       VARCHAR2(64)           not null,
    defaultPortfolio               VARCHAR2(64)           null    ,
    constraint PK_SALESCLIENTCONFIG primary key (salesGroupId, clientId)
)
/

-- ============================================================
--   Table : SalesGroupConfig                                  
-- ============================================================
create table SalesGroupConfig
(
    userId                         VARCHAR2(64)           not null,
    salesGroupId                   VARCHAR2(64)           not null,
    constraint PK_SALESGROUPCONFIG primary key (userId, salesGroupId)
)
/

-- ============================================================
--   Table : LinkedPricing                                     
-- ============================================================
create table LinkedPricing
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           not null,
    linkedProductId                VARCHAR2(128)          not null,
    linkedProductIdImsId           VARCHAR2(32)           not null,
    linkedProductShift             FLOAT                  null    ,
    isActive                       NUMBER(1)              null    ,
    constraint PK_LINKEDPRICING primary key (productId, context)
)
/

-- ============================================================
--   Table : MarketStreamConfiguration                         
-- ============================================================
create table MarketStreamConfiguration
(
    userId                         VARCHAR2(64)           not null,
    mic                            VARCHAR2(32)           not null,
    productType                    VARCHAR2(16)           default '*' not null,
    productId                      VARCHAR2(128)          not null,
    streamImsId                    VARCHAR2(32)           null    ,
    transactionImsId               VARCHAR2(32)           null    ,
    constraint PK_MARKETSTREAMCONFIGURATION primary key (userId, mic, productType, productId)
)
/

-- ============================================================
--   Table : PairTradingSchedule                               
-- ============================================================
create table PairTradingSchedule
(
    rank                           INTEGER                not null,
    pairTradingAutomatonId         VARCHAR2(168)          not null,
    startTime                      TIMESTAMP(3)           null    ,
    stopTime                       TIMESTAMP(3)           null    ,
    constraint PK_PAIRTRADINGSCHEDULE primary key (rank, pairTradingAutomatonId)
)
/

-- ============================================================
--   Table : Vwap                                              
-- ============================================================
create table Vwap
(
    vwapId                         VARCHAR2(256)          not null,
    productId                      VARCHAR2(128)          not null,
    strategyId                     VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            not null
        constraint CKC_WAY_VWAP check (way in ('B','S')),
    initialQty                     INTEGER                not null,
    bucketNumber                   INTEGER                null    ,
    startTime                      TIMESTAMP(3)           not null,
    endTime                        TIMESTAMP(3)           not null,
    stopTime                       TIMESTAMP(3)           null    ,
    status                         VARCHAR2(16)           null    ,
    spotRef                        FLOAT                  null    ,
    last                           FLOAT                  null    ,
    qty                            INTEGER                null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    vwapTrader                     FLOAT                  null    ,
    vwapMarket                     FLOAT                  null    ,
    pnl                            FLOAT                  null    ,
    leftQty                        FLOAT                  null    ,
    execQty                        FLOAT                  null    ,
    execPrice                      FLOAT                  null    ,
    execTimestamp                  TIMESTAMP(3)           null    ,
    constraint PK_VWAP primary key (vwapId)
)
/

-- ============================================================
--   Table : SyntheticMakerProductRule                         
-- ============================================================
create table SyntheticMakerProductRule
(
    productId                      VARCHAR2(128)          not null,
    proxyProductId                 VARCHAR2(128)          not null,
    priority                       INTEGER                null    ,
    multiplier                     FLOAT                  null    ,
    constraint PK_SYNTHETICMAKERPRODUCTRULE primary key (productId, proxyProductId)
)
/

-- ============================================================
--   Table : SyntheticMakerMarketRule                          
-- ============================================================
create table SyntheticMakerMarketRule
(
    mic                            VARCHAR2(32)           not null,
    proxySpotId                    VARCHAR2(128)          not null,
    priority                       INTEGER                null    ,
    spotPolicy                     VARCHAR2(64)           null    ,
    fieldToSnap                    VARCHAR2(128)          null    ,
    description                    VARCHAR2(256)          null    ,
    validationFormula              VARCHAR2(512)          null    ,
    constraint PK_SYNTHETICMAKERMARKETRULE primary key (mic, proxySpotId)
)
/

-- ============================================================
--   Table : PricingDataVersionHistory                         
-- ============================================================
create table PricingDataVersionHistory
(
    pricingBatchId                 VARCHAR2(64)           not null,
    dataType                       VARCHAR2(16)           not null
        constraint CKC_DATATYPE_PDVHISTO check (dataType in ('VOLATILITY','DIVIDEND','RATE','REPO','YIELD','TAX_CREDIT')),
    version                        VARCHAR2(32)           null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    constraint PK_PDVHISTORY primary key (pricingBatchId, dataType)
)
/

-- ============================================================
--   Table : PricingAdditionalSpread                           
-- ============================================================
create table PricingAdditionalSpread
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           not null,
    spreadRuleGroupId              VARCHAR2(64)           not null,
    cautiousLevel                  INTEGER                default 0 not null,
    forceMarketSpread              NUMBER(1)              null    ,
    constraint PK_PRICINGADDITIONALSPREAD primary key (productId, context, spreadRuleGroupId)
)
/

-- ============================================================
--   Table : Correlation                                       
-- ============================================================
create table Correlation
(
    context                        VARCHAR2(32)           not null,
    priceType                      VARCHAR2(16)           not null,
    productId                      VARCHAR2(128)          not null,
    currencyPairId                 VARCHAR2(8)            not null,
    correlation                    FLOAT                  null    ,
    constraint PK_CORRELATION primary key (context, priceType, productId, currencyPairId)
)
/

-- ============================================================
--   Table : ImmediateHedge                                    
-- ============================================================
create table ImmediateHedge
(
    userId                         VARCHAR2(64)           not null,
    productId                      VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    constraint PK_IMMEDIATEHEDGE primary key (userId, productId, portfolioId)
)
/

-- ============================================================
--   Table : ManualFxRate                                      
-- ============================================================
create table ManualFxRate
(
    rateId                         VARCHAR2(7)            not null,
    rate                           FLOAT                  null    ,
    constraint PK_MANUALFXRATE primary key (rateId)
)
/

-- ============================================================
--   Table : EconomicZone                                      
-- ============================================================
create table EconomicZone
(
    zoneName                       VARCHAR2(16)           not null,
    parentZoneName                 VARCHAR2(16)           null    ,
    description                    VARCHAR2(255)          default '*' null    ,
    constraint PK_ECONOMICZONE primary key (zoneName)
)
/

-- ============================================================
--   Table : SpotUnitAddress                                   
-- ============================================================
create table SpotUnitAddress
(
    spotId                         VARCHAR2(128)          not null,
    unitId                         VARCHAR2(32)           null    ,
    constraint PK_SPOTUNITADDRESS primary key (spotId)
)
/

-- ============================================================
--   Table : CopReferencePrice                                 
-- ============================================================
create table CopReferencePrice
(
    productId                      VARCHAR2(128)          not null,
    referencePrice                 FLOAT                  not null,
    constraint PK_COPREFERENCEPRICE primary key (productId)
)
/

-- ============================================================
--   Table : TickerSubscriptionProfile                         
-- ============================================================
create table TickerSubscriptionProfile
(
    profileId                      VARCHAR2(256)          not null,
    rank                           INTEGER                not null,
    ulId                           VARCHAR2(128)          null    ,
    productTypes                   VARCHAR2(256)          null    ,
    expiries                       VARCHAR2(256)          null    ,
    minQty                         NUMBER(15)             null    ,
    trxTypesToExclude              VARCHAR2(128)          null    ,
    constraint PK_TICKERSUBSCRIPTIONPROFILE primary key (profileId, rank)
)
/

-- ============================================================
--   Table : AutomatonUnitAddress                              
-- ============================================================
create table AutomatonUnitAddress
(
    imsId                          VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           not null,
    automatonUnitId                VARCHAR2(128)          not null,
    constraint PK_AUTOMATONUNITADDRESS primary key (imsId, productId, context)
)
/

-- ============================================================
--   Table : License                                           
-- ============================================================
create table License
(
    licenseId                      VARCHAR2(16)           not null,
    licenseValue                   CLOB                   not null,
    signature                      CLOB                   not null,
    constraint PK_LICENSE primary key (licenseId)
)
/

-- ============================================================
--   Table : UlMaturityParam                                   
-- ============================================================
create table UlMaturityParam
(
    ulId                           VARCHAR2(128)          not null,
    maturity                       TIMESTAMP(3)           not null,
    paramName                      VARCHAR2(64)           not null,
    paramValue                     VARCHAR2(1024)         null    ,
    constraint PK_ULMATURITYPARAM primary key (ulId, maturity, paramName)
)
/

-- ============================================================
--   Table : ExternalPricingConfiguration                      
-- ============================================================
create table ExternalPricingConfiguration
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           not null,
    ulId                           VARCHAR2(128)          not null,
    staleWarningDelayMs            INTEGER                null    ,
    staleErrorDelayMs              INTEGER                null    ,
    refreshLimiterPeriodMs         INTEGER                null    ,
    constraint PK_EXTERNALPRICINGCONF primary key (productId, context, ulId)
)
/

-- ============================================================
--   Table : DerivativeClass                                   
-- ============================================================
create table DerivativeClass
(
    underlyingClass                VARCHAR2(128)          not null,
    settlementDateOffset           INTEGER                null    ,
    constraint PK_DERIVATIVECLASS primary key (underlyingClass)
)
/

-- ============================================================
--   Table : ReutersFieldMapping                               
-- ============================================================
create table ReutersFieldMapping
(
    groupId                        VARCHAR2(32)           not null,
    driverFieldId                  VARCHAR2(64)           not null,
    reutersField                   VARCHAR2(64)           null    ,
    type                           VARCHAR2(128)          null    ,
    additionalInfo                 VARCHAR2(2000)         null    ,
    constraint PK_REUTERSFIELDMAPPING primary key (groupId, driverFieldId)
)
/

-- ============================================================
--   Table : UlSnapAtMaturityConfig                            
-- ============================================================
create table UlSnapAtMaturityConfig
(
    ulId                           VARCHAR2(128)          not null,
    spotId                         VARCHAR2(128)          null    ,
    spotField                      VARCHAR2(128)          null    ,
    constraint PK_ULSNAPATMATURITYCONFIG primary key (ulId)
)
/

-- ============================================================
--   Table : UlSnapAtMaturity                                  
-- ============================================================
create table UlSnapAtMaturity
(
    ulId                           VARCHAR2(128)          not null,
    maturity                       TIMESTAMP(3)           not null,
    spotValue                      FLOAT                  null    ,
    constraint PK_ULSNAPATMATURITY primary key (ulId, maturity)
)
/

-- ============================================================
--   Table : DeltaOneUnitAddress                               
-- ============================================================
create table DeltaOneUnitAddress
(
    groupId                        VARCHAR2(128)          not null,
    unitId                         VARCHAR2(128)          not null,
    constraint PK_DELTAONEUNITADDRESS primary key (groupId)
)
/

-- ============================================================
--   Table : BloombergDictionarySeed                           
-- ============================================================
create table BloombergDictionarySeed
(
    seedId                         VARCHAR2(16)           not null,
    cusip                          VARCHAR2(16)           null    ,
    sedol                          VARCHAR2(16)           null    ,
    isin                           VARCHAR2(16)           null    ,
    bsid                           VARCHAR2(32)           null    ,
    bsym                           VARCHAR2(256)          null    ,
    buid                           VARCHAR2(64)           null    ,
    source                         VARCHAR2(16)           null    ,
    type                           VARCHAR2(128)          null    ,
    getList                        NUMBER(1)              null    ,
    secclass                       VARCHAR2(128)          null    ,
    minStrike                      FLOAT                  null    ,
    maxStrike                      FLOAT                  null    ,
    minExpiryUnit                  VARCHAR2(1)            null    ,
    minExpiryValue                 INTEGER                null    ,
    maxExpiryUnit                  VARCHAR2(1)            null    ,
    maxExpiryValue                 INTEGER                null    ,
    constraint PK_BLOOMBERGDICTIONARYSEED primary key (seedId)
)
/

-- ============================================================
--   Table : PricingModelConfig                                
-- ============================================================
create table PricingModelConfig
(
    mic                            VARCHAR2(32)           not null,
    modelName                      VARCHAR2(256)          not null,
    parameterName                  VARCHAR2(256)          not null,
    value                          FLOAT                  null    ,
    constraint PK_PRICINGMODELCONFIG primary key (mic, modelName, parameterName)
)
/

-- ============================================================
--   Table : AlgoInstanceConfiguration                         
-- ============================================================
create table AlgoInstanceConfiguration
(
    instance                       VARCHAR2(64)           not null,
    serverId                       VARCHAR2(64)           null    ,
    modelFile                      VARCHAR2(64)           null    ,
    isAutoStart                    NUMBER(1)              null    ,
    ownerId                        VARCHAR2(64)           null    ,
    constraint PK_ALGOINSTANCECONFIGURATION primary key (instance)
)
/

-- ============================================================
--   Table : AlgoInstanceParameter                             
-- ============================================================
create table AlgoInstanceParameter
(
    instance                       VARCHAR2(64)           not null,
    paramKey                       VARCHAR2(128)          not null,
    paramValue                     VARCHAR2(256)          null    ,
    constraint PK_ALGOINSTANCEPARAMETER primary key (instance, paramKey)
)
/

-- ============================================================
--   Table : StrategyMarketMakingRule                          
-- ============================================================
create table StrategyMarketMakingRule
(
    ulId                           VARCHAR2(128)          not null,
    strategyType                   VARCHAR2(32)           not null,
    maturityUnit                   VARCHAR2(1)            not null
        constraint CKC_MATURITYUNIT_STRATEGY check (maturityUnit in ('d','w','m','y','D','W','M','Y')),
    maturityValue                  INTEGER                not null,
    spreadMultiplier               FLOAT                  default 1 null    ,
    quantityMultiplier             FLOAT                  default 1 null    ,
    constraint PK_STRATEGYMARKETMAKINGRULE primary key (ulId, strategyType, maturityUnit, maturityValue)
)
/

-- ============================================================
--   Table : MarketMakingQuantityRule                          
-- ============================================================
create table MarketMakingQuantityRule
(
    productId                      VARCHAR2(128)          not null,
    minimumQuantity                INTEGER                null    ,
    constraint PK_MARKETMAKINGQUANTITYRULE primary key (productId)
)
/

-- ============================================================
--   Table : FORTSObligations                                  
-- ============================================================
create table FORTSObligations
(
    productId                      VARCHAR2(128)          not null,
    maxSpread                      FLOAT                  null    ,
    minQty                         INTEGER                null    ,
    constraint PK_FORTSOBLIGATIONS primary key (productId)
)
/

-- ============================================================
--   Table : FORTSObligationsHistoric                          
-- ============================================================
create table FORTSObligationsHistoric
(
    productId                      VARCHAR2(128)          not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    maxSpread                      FLOAT                  null    ,
    minQty                         INTEGER                null    ,
    constraint PK_FORTSOBLIGATIONSHISTORIC primary key (productId, archiveTimestamp)
)
/

-- ============================================================
--   Table : SpotRule                                          
-- ============================================================
create table SpotRule
(
    spotRuleId                     VARCHAR2(32)           not null,
    className                      VARCHAR2(128)          not null,
    description                    VARCHAR2(256)          null    ,
    properties                     VARCHAR2(512)          null    ,
    constraint PK_SPOTRULE primary key (spotRuleId)
)
/

-- ============================================================
--   Table : PriceVector                                       
-- ============================================================
create table PriceVector
(
    priceVectorId                  VARCHAR2(32)           not null,
    stepCount                      INTEGER                not null,
    radiusInPercent                FLOAT                  not null,
    stableRadiusInPercent          FLOAT                  not null,
    constraint PK_PRICEVECTOR primary key (priceVectorId)
)
/

-- ============================================================
--   Table : SpreadRuleGroup                                   
-- ============================================================
create table SpreadRuleGroup
(
    spreadRuleGroupId              VARCHAR2(32)           not null,
    cautiousLevel                  INTEGER                not null,
    distribution                   VARCHAR2(4)            not null,
    spreadDistribution             FLOAT                  null    ,
    spreadInPriceAbs               FLOAT                  null    ,
    spreadInPricePct               FLOAT                  null    ,
    spreadInVolAbs                 FLOAT                  null    ,
    spreadInVolPct                 FLOAT                  null    ,
    spreadInTick                   INTEGER                null    ,
    associationMode                VARCHAR2(4)            null    ,
    wideDistribution               VARCHAR2(4)            null    ,
    wideSpreadDistribution         FLOAT                  null    ,
    wideSpreadInPriceAbs           FLOAT                  null    ,
    wideSpreadInPricePct           FLOAT                  null    ,
    wideSpreadInVolAbs             FLOAT                  null    ,
    wideSpreadInVolPct             FLOAT                  null    ,
    wideSpreadInTick               INTEGER                null    ,
    wideAssociationMode            VARCHAR2(4)            null    ,
    constraint PK_SPREADRULEGROUP primary key (spreadRuleGroupId, cautiousLevel)
)
/

-- ============================================================
--   Table : TickRule                                          
-- ============================================================
create table TickRule
(
    tickRuleId                     VARCHAR2(128)          not null,
    className                      VARCHAR2(128)          not null,
    properties                     VARCHAR2(1024)         null    ,
    constraint PK_TICKRULE primary key (tickRuleId)
)
/

-- ============================================================
--   Table : LiffeMktSpreadRule                                
-- ============================================================
create table LiffeMktSpreadRule
(
    marketSpreadRuleId             VARCHAR2(32)           not null,
    contractPivotSize              INTEGER                null    ,
    constraint PK_LIFFEMKTSPREADRULE primary key (marketSpreadRuleId)
)
/

-- ============================================================
--   Table : UserInfo                                          
-- ============================================================
create table UserInfo
(
    userId                         VARCHAR2(64)           not null,
    name                           VARCHAR2(64)           null    ,
    password                       VARCHAR2(255)          null    ,
    isSystem                       NUMBER(1)              null    ,
    isImsSuperUser                 NUMBER(1)              default 0 null    ,
    maxImsSession                  INTEGER                default 0 null    ,
    imsShortId                     VARCHAR2(4)            null    ,
    cancelOnDisconnect             NUMBER(1)              default 0 null    ,
    lostExecPortfolio              VARCHAR2(256)          null    ,
    defaultContext                 VARCHAR2(32)           null    ,
    dacsLogin                      VARCHAR2(64)           null    ,
    useDacs                        NUMBER(1)              null    ,
    isActive                       NUMBER(1)              null    ,
    isIms                          NUMBER(1)              null    ,
    isBlocked                      NUMBER(1)              null    ,
    nbLoginAttempts                INTEGER                null    ,
    passwordExpiry                 TIMESTAMP(3)           null    ,
    useMbpipe                      NUMBER(1)              null    ,
    mbpipeLogin                    VARCHAR2(64)           null    ,
    constraint PK_USERINFO primary key (userId)
)
/

-- ============================================================
--   Table : Currency                                          
-- ============================================================
create table Currency
(
    currencyId                     VARCHAR2(3)            not null,
    quotity                        FLOAT                  null    ,
    constraint PK_CURRENCY primary key (currencyId)
)
/

-- ============================================================
--   Table : Market                                            
-- ============================================================
create table Market
(
    mic                            VARCHAR2(32)           not null,
    description                    VARCHAR2(256)          null    ,
    bloombergCode                  VARCHAR2(32)           null    ,
    reutersCode                    VARCHAR2(32)           null    ,
    openingTime                    VARCHAR2(5)            null    ,
    closingTime                    VARCHAR2(5)            null    ,
    breakTime                      VARCHAR2(5)            null    ,
    resumeTime                     VARCHAR2(5)            null    ,
    constraint PK_MARKET primary key (mic)
)
/

-- ============================================================
--   Table : IMSConnection                                     
-- ============================================================
create table IMSConnection
(
    imsId                          VARCHAR2(32)           not null,
    host                           VARCHAR2(64)           null    ,
    port                           INTEGER                not null,
    type                           VARCHAR2(32)           null    ,
    constraint PK_IMSCONNECTION primary key (imsId)
)
/

-- ============================================================
--   Table : Portfolio                                         
-- ============================================================
create table Portfolio
(
    portfolioId                    VARCHAR2(64)           not null,
    parentId                       VARCHAR2(256)          null    ,
    description                    NVARCHAR2(256)         null    ,
    legalEntity                    VARCHAR2(64)           null    ,
    bookingSystem                  VARCHAR2(64)           null    ,
    isTradable                     NUMBER(1)              default 1 null    ,
    constraint PK_PORTFOLIO primary key (portfolioId)
)
/

-- ============================================================
--   Table : Context                                           
-- ============================================================
create table Context
(
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    description                    NVARCHAR2(256)         null    ,
    enableMarketSpreadRule         NUMBER(1)              default 1 null    ,
    constraint PK_CONTEXT primary key (context)
)
/

-- ============================================================
--   Table : PricingUnit                                       
-- ============================================================
create table PricingUnit
(
    pricingUnitId                  VARCHAR2(16)           not null,
    loadBalancerHost               VARCHAR2(64)           not null,
    loadBalancerPort               INTEGER                not null,
    loadBalancerWrapperPort        INTEGER                not null,
    priceStreamerHost              VARCHAR2(64)           not null,
    priceStreamerPort              INTEGER                not null,
    constraint PK_PRICINGUNIT primary key (pricingUnitId)
)
/

-- ============================================================
--   Table : CopGroup                                          
-- ============================================================
create table CopGroup
(
    groupId                        VARCHAR2(64)           not null,
    description                    VARCHAR2(256)          null    ,
    constraint PK_COPGROUP primary key (groupId)
)
/

-- ============================================================
--   Table : SpotProcessor                                     
-- ============================================================
create table SpotProcessor
(
    spotId                         VARCHAR2(128)          not null,
    description                    VARCHAR2(256)          null    ,
    limiterPeriodMs                INTEGER                null    ,
    thresholdValue                 FLOAT                  null    ,
    thresholdUnit                  VARCHAR2(16)           null    ,
    isExcel                        NUMBER(1)              default 0 null    ,
    isTemplate                     NUMBER(1)              default 0 null    ,
    useTemplate                    VARCHAR2(64)           null    ,
    publishDepth                   NUMBER(1)              default 0 null    ,
    type                           VARCHAR2(16)           default 'NORMAL' null    
        constraint CKC_TYPE_SPOTPROC check (type is null or (type in ('NORMAL','SYNTHETIC'))),
    marketProductId                VARCHAR2(128)          null    ,
    isActive                       NUMBER(1)              default 0 null    ,
    constraint PK_SPOTPROCESSOR primary key (spotId)
)
/

-- ============================================================
--   Table : SpotProcessorComputation                          
-- ============================================================
create table SpotProcessorComputation
(
    formulaId                      VARCHAR2(128)          not null,
    formulaDescription             VARCHAR2(255)          null    ,
    formulaValue                   VARCHAR2(512)          null    ,
    constraint PK_SPOTPROCESSORCOMPUTATION primary key (formulaId)
)
/

-- ============================================================
--   Table : Broker                                            
-- ============================================================
create table Broker
(
    brokerId                       VARCHAR2(64)           not null,
    brokerName                     VARCHAR2(256)          null    ,
    country                        VARCHAR2(256)          null    ,
    location                       VARCHAR2(256)          null    ,
    bookingId                      VARCHAR2(256)          null    ,
    type                           VARCHAR2(32)           null    
        constraint CKC_TYPE_BROKER check (type is null or (type in ('BROKER','COUNTERPARTY','INTERNAL_COUNTERPARTY'))),
    constraint PK_BROKER primary key (brokerId)
)
/

-- ============================================================
--   Table : MarketMakingRule                                  
-- ============================================================
create table MarketMakingRule
(
    ruleId                         VARCHAR2(128)          not null,
    ruleType                       VARCHAR2(64)           not null
        constraint CKC_RULETYPE_MARKETMA check (ruleType in ('FULL_MM','QUOTE_RESPONSE')),
    ruleParameters                 VARCHAR2(1024)         null    ,
    underPerfThreshold             FLOAT                  not null
        constraint CKC_UNDERPERFTHRESHOL_MARKETMA check (underPerfThreshold between 0 and 1),
    constraint PK_MARKETMAKINGRULE primary key (ruleId)
)
/

-- ============================================================
--   Table : TradeBooking                                      
-- ============================================================
create table TradeBooking
(
    tradeId                        VARCHAR2(128)          not null,
    tradeLegId                     VARCHAR2(64)           not null,
    execType                       VARCHAR2(1)            null    
        constraint CKC_EXECTYPE_TRADEBOO check (execType is null or (execType in ('C','D'))),
    tradeType                      VARCHAR2(32)           null    ,
    bookId                         VARCHAR2(128)          null    ,
    counterpartyId                 VARCHAR2(64)           null    ,
    status                         VARCHAR2(16)           null    ,
    bookingSystem                  VARCHAR2(32)           null    ,
    bookingError                   VARCHAR2(512)          null    ,
    bookingId                      VARCHAR2(128)          null    ,
    tradeTimestamp                 TIMESTAMP(3)           null    ,
    statusTimestamp                TIMESTAMP(3)           null    ,
    messageFormat                  VARCHAR2(64)           null    ,
    messageFormatVersion           VARCHAR2(16)           null    ,
    messageHeaderVersion           VARCHAR2(16)           null    ,
    applicationId                  VARCHAR2(32)           null    ,
    replyDownStream                VARCHAR2(512)          null    ,
    source                         VARCHAR2(512)          null    ,
    messageTimestamp               TIMESTAMP(3)           null    ,
    messageType                    VARCHAR2(32)           null    ,
    targetDownStream               VARCHAR2(512)          null    ,
    messageId                      VARCHAR2(256)          null    ,
    applicationSource              VARCHAR2(32)           null    ,
    eventType                      VARCHAR2(32)           null    ,
    subEventType                   VARCHAR2(32)           null    ,
    location                       VARCHAR2(32)           null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_TRADEBOO check (way is null or (way in ('2','B','S'))),
    tradeRevision                  INTEGER                null    ,
    inputBy                        VARCHAR2(64)           null    ,
    legalEntity                    VARCHAR2(32)           null    ,
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    principal                      FLOAT                  null    ,
    securityId                     VARCHAR2(32)           null    ,
    securityType                   VARCHAR2(32)           null    ,
    settlementCurrency             VARCHAR2(3)            null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    downStreamStrategy             VARCHAR2(32)           null    ,
    tradeCurrency                  VARCHAR2(3)            null    ,
    localTradeTimeStamp            TIMESTAMP(3)           null    ,
    traderId                       VARCHAR2(64)           null    ,
    tradingArea                    VARCHAR2(64)           null    ,
    dealCondition                  VARCHAR2(64)           null    ,
    accruedInterest                FLOAT                  null    ,
    constraint PK_TRADEBOOKING primary key (tradeId, tradeLegId)
)
/

-- ============================================================
--   Table : AutomatonDynamicRuleGroup                         
-- ============================================================
create table AutomatonDynamicRuleGroup
(
    cautiousLevel                  INTEGER                not null,
    dynamicRuleGroupId             VARCHAR2(32)           not null,
    bidSizeMultiplier              FLOAT                  null    ,
    askSizeMultiplier              FLOAT                  null    ,
    refillStepMultiplier           INTEGER                null    ,
    refillPeriodMsMultiplier       INTEGER                null    ,
    refillOnFullExec               NUMBER(1)              null    ,
    staleDelayMs                   INTEGER                null    ,
    maxShiftType                   VARCHAR2(16)           null    ,
    maxShiftValue                  FLOAT                  null    ,
    smoothingDelay                 INTEGER                null    ,
    rfqPriceToleranceMultiplier    FLOAT                  null    ,
    firstBidSizeMultiplier         FLOAT                  null    ,
    firstAskSizeMultiplier         FLOAT                  null    ,
    constraint PK_AUTODYNAMICRULEGROUP primary key (cautiousLevel, dynamicRuleGroupId)
)
/

-- ============================================================
--   Table : SchedulerCommand                                  
-- ============================================================
create table SchedulerCommand
(
    scheduleCommand                VARCHAR2(64)           not null,
    scheduleCommandClass           VARCHAR2(128)          not null,
    constraint PK_SCHEDULERCOMMAND primary key (scheduleCommand)
)
/

-- ============================================================
--   Table : PairTradingSafetyNet                              
-- ============================================================
create table PairTradingSafetyNet
(
    pairTradingSafetyNetId         VARCHAR2(32)           not null,
    fastMarketVariationPercent     FLOAT                  null    ,
    spotPrimaryDs1                 VARCHAR2(128)          null    ,
    spotPrimaryDs2                 VARCHAR2(128)          null    ,
    spotPrimaryDiffPercent         FLOAT                  null    ,
    spotHedgeDs1                   VARCHAR2(128)          null    ,
    spotHedgeDs2                   VARCHAR2(128)          null    ,
    spotHedgeDiffPercent           FLOAT                  null    ,
    maxQtyPrimaryBuy               INTEGER                null    ,
    maxQtyPrimarySell              INTEGER                null    ,
    maxQtyHedgeBuy                 INTEGER                null    ,
    maxQtyHedgeSell                INTEGER                null    ,
    disableOnFixing                NUMBER(1)              null    ,
    disableOnSuspended             NUMBER(1)              null    ,
    properties                     VARCHAR2(1024)         null    ,
    constraint PK_PAIRTRADINGSAFETYNET primary key (pairTradingSafetyNetId)
)
/

-- ============================================================
--   Table : ScheduledSpot                                     
-- ============================================================
create table ScheduledSpot
(
    scheduledSpotId                VARCHAR2(128)          not null,
    description                    VARCHAR2(256)          null    ,
    isActive                       NUMBER(1)              null    ,
    constraint PK_SCHEDULEDSPOT primary key (scheduledSpotId)
)
/

-- ============================================================
--   Table : BasketDefinition                                  
-- ============================================================
create table BasketDefinition
(
    basketId                       VARCHAR2(64)           not null,
    isIndex                        NUMBER(1)              null    ,
    basketCurrency                 VARCHAR2(3)            null    ,
    pointValue                     FLOAT                  default 1.0 null    ,
    description                    NVARCHAR2(256)         null    ,
    lastUpdate                     TIMESTAMP(3)           null    ,
    indexId                        VARCHAR2(128)          null    ,
    streamImsId                    VARCHAR2(32)           null    ,
    transactionImsId               VARCHAR2(32)           null    ,
    indexStreamImsId               VARCHAR2(32)           null    ,
    depthTemplate                  VARCHAR2(256)          null    ,
    depthType                      VARCHAR2(32)           null    ,
    generateSyntheticBasket        NUMBER(1)              null    ,
    useAmount                      NUMBER(1)              null    ,
    legPriceStrategy               VARCHAR2(32)           null    ,
    cashComponent                  FLOAT                  null    ,
    creationUnit                   INTEGER                null    ,
    constraint PK_BASKETDEFINITION primary key (basketId)
)
/

-- ============================================================
--   Table : SSMPortfolio                                      
-- ============================================================
create table SSMPortfolio
(
    ssmPortfolioId                 VARCHAR2(64)           not null,
    portfolioType                  VARCHAR2(32)           null    ,
    constraint PK_SSMPORTFOLIO primary key (ssmPortfolioId)
)
/

-- ============================================================
--   Table : SSMSecurity                                       
-- ============================================================
create table SSMSecurity
(
    productId                      VARCHAR2(128)          not null,
    type                           VARCHAR2(1)            null    ,
    constraint PK_SSMSECURITY primary key (productId)
)
/

-- ============================================================
--   Table : PricingScenarioType                               
-- ============================================================
create table PricingScenarioType
(
    pricingScenarioId              VARCHAR2(64)           not null,
    shortId                        VARCHAR2(8)            null    ,
    isDefaultScenario              NUMBER(1)              null    ,
    description                    VARCHAR2(256)          null    ,
    constraint PK_PRICINGSCENARIOTYPE primary key (pricingScenarioId)
)
/

-- ============================================================
--   Table : PricingPriority                                   
-- ============================================================
create table PricingPriority
(
    pricingPriority                VARCHAR2(16)           not null,
    description                    VARCHAR2(256)          null    ,
    constraint PK_PRICINGPRIORITY primary key (pricingPriority)
)
/

-- ============================================================
--   Table : ForexExecution                                    
-- ============================================================
create table ForexExecution
(
    tradeId                        VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           null    ,
    brokerExchangeId               VARCHAR2(64)           null    ,
    userId                         VARCHAR2(64)           null    ,
    imsId                          VARCHAR2(32)           null    ,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_FOREXEXE check (way is null or (way in ('2','B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            null    
        constraint CKC_EXECUTIONSTATE_FOREXEXE check (executionState is null or (executionState in ('n','c','u'))),
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    tradeType                      VARCHAR2(64)           null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    boughtCurrency                 VARCHAR2(3)            null    ,
    boughtAmount                   FLOAT                  null    ,
    soldCurrency                   VARCHAR2(3)            null    ,
    soldAmount                     FLOAT                  null    ,
    mic                            VARCHAR2(32)           null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    hedgeSnapStatus                VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    hedgeForexSnapStatus           VARCHAR2(64)           null    ,
    constraint PK_FOREXEXECUTION primary key (tradeId)
)
/

-- ============================================================
--   Table : CopOptionalCriterion                              
-- ============================================================
create table CopOptionalCriterion
(
    name                           VARCHAR2(128)          not null,
    source                         VARCHAR2(128)          null    ,
    selector                       VARCHAR2(128)          null    ,
    constraint PK_COPOPTIONALCRITERION primary key (name)
)
/

-- ============================================================
--   Table : BiasOwner                                         
-- ============================================================
create table BiasOwner
(
    owner                          VARCHAR2(255)          not null,
    constraint PK_BIASOWNER primary key (owner)
)
/

-- ============================================================
--   Table : MarketStreamSnap                                  
-- ============================================================
create table MarketStreamSnap
(
    snapId                         VARCHAR2(32)           not null,
    timestamp                      TIMESTAMP(3)           null    ,
    productId                      VARCHAR2(128)          null    ,
    tradeId                        VARCHAR2(128)          null    ,
    fieldId                        VARCHAR2(128)          null    ,
    value                          FLOAT                  null    ,
    constraint PK_MARKETSTREAMSNAP primary key (snapId)
)
/

-- ============================================================
--   Table : ExecutionInformation                              
-- ============================================================
create table ExecutionInformation
(
    tradeId                        VARCHAR2(128)          not null,
    propertyId                     VARCHAR2(256)          not null,
    propertyValue                  VARCHAR2(512)          null    ,
    constraint PK_EXECINFO primary key (tradeId, propertyId)
)
/

-- ============================================================
--   Table : YieldCurve                                        
-- ============================================================
create table YieldCurve
(
    currencyId                     VARCHAR2(3)            not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_YIELDCUR check (priceType in ('TRADING','VALIDATION')),
    interpolationModel             VARCHAR2(16)           null    
        constraint CKC_INTERPOLATIONMODE_YIELDCUR check (interpolationModel is null or (interpolationModel in ('LINEAR','SPLINE'))),
    compounding                    VARCHAR2(32)           null    ,
    dayCount                       VARCHAR2(32)           null    ,
    weekEndRule                    VARCHAR2(32)           null    ,
    endOfMonthRule                 VARCHAR2(32)           null    ,
    businessDayConvention          VARCHAR2(32)           null    ,
    constraint PK_YIELDCURVE primary key (currencyId, context, priceType)
)
/

-- ============================================================
--   Table : CombinationProduct                                
-- ============================================================
create table CombinationProduct
(
    productId                      VARCHAR2(128)          not null,
    tickRuleId                     VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            not null,
    mic                            VARCHAR2(32)           null    ,
    lotSize                        INTEGER                null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    contractSizeNumerator          FLOAT                  null    ,
    contractSizeDenominator        FLOAT                  null    ,
    pointValue                     FLOAT                  null    ,
    label1                         NVARCHAR2(64)          null    ,
    label2                         NVARCHAR2(64)          null    ,
    label3                         NVARCHAR2(64)          null    ,
    label4                         NVARCHAR2(64)          null    ,
    strike                         FLOAT                  null    ,
    maturity                       TIMESTAMP(3)           null    ,
    ulId                           VARCHAR2(128)          null    ,
    isDecomposed                   NUMBER(1)              null    ,
    model                          VARCHAR2(16)           default 'VANILLA' not null,
    priceType                      VARCHAR2(32)           default 'AMOUNT' not null
        constraint CKC_PRICETYPE_COMBIPRO check (priceType in ('AMOUNT','PERCENT_DIRTY','PERCENT_CLEAN','YIELD')),
    allowNegativePrice             NUMBER(1)              null    ,
    constraint PK_COMBIPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : PrimaryProduct                                    
-- ============================================================
create table PrimaryProduct
(
    productId                      VARCHAR2(128)          not null,
    tickRuleId                     VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            not null,
    mic                            VARCHAR2(32)           null    ,
    lotSize                        INTEGER                null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    contractSizeNumerator          FLOAT                  null    ,
    contractSizeDenominator        FLOAT                  null    ,
    pointValue                     FLOAT                  null    ,
    frontMonthDate                 TIMESTAMP(3)           null    ,
    productType                    VARCHAR2(32)           not null,
    assetClass                     VARCHAR2(32)           null    ,
    economicZoneName               VARCHAR2(16)           null    ,
    futurePointValue               FLOAT                  null    ,
    constraint PK_PRIMARYPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : StrategyProduct                                   
-- ============================================================
create table StrategyProduct
(
    productId                      VARCHAR2(128)          not null,
    tickRuleId                     VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            not null,
    mic                            VARCHAR2(32)           null    ,
    lotSize                        INTEGER                null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    contractSizeNumerator          FLOAT                  null    ,
    contractSizeDenominator        FLOAT                  null    ,
    pointValue                     FLOAT                  null    ,
    strategyType                   VARCHAR2(32)           null    ,
    label1                         NVARCHAR2(64)          null    ,
    label2                         NVARCHAR2(64)          null    ,
    label3                         NVARCHAR2(64)          null    ,
    label4                         NVARCHAR2(64)          null    ,
    ulId                           VARCHAR2(128)          null    ,
    maturity                       TIMESTAMP(3)           null    ,
    strike                         FLOAT                  null    ,
    constraint PK_STRATEGYPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : DeltaOneProduct                                   
-- ============================================================
create table DeltaOneProduct
(
    productId                      VARCHAR2(128)          not null,
    tickRuleId                     VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            not null,
    mic                            VARCHAR2(32)           null    ,
    lotSize                        INTEGER                null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    contractSizeNumerator          FLOAT                  null    ,
    contractSizeDenominator        FLOAT                  null    ,
    pointValue                     FLOAT                  null    ,
    ulId                           VARCHAR2(128)          not null,
    marketUlId                     VARCHAR2(128)          null    ,
    isQuanto                       NUMBER(1)              null    ,
    quantoRate                     FLOAT                  null    ,
    decomposePosition              NUMBER(1)              null    ,
    expiry                         TIMESTAMP(3)           null    ,
    model                          VARCHAR2(16)           default 'VANILLA' not null,
    label1                         NVARCHAR2(64)          null    ,
    label2                         NVARCHAR2(64)          null    ,
    label3                         NVARCHAR2(64)          null    ,
    label4                         NVARCHAR2(64)          null    ,
    allowNegativePrice             NUMBER(1)              null    ,
    constraint PK_DELTAONEPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : OptionProduct                                     
-- ============================================================
create table OptionProduct
(
    productId                      VARCHAR2(128)          not null,
    tickRuleId                     VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            not null,
    mic                            VARCHAR2(32)           null    ,
    lotSize                        INTEGER                null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    contractSizeNumerator          FLOAT                  null    ,
    contractSizeDenominator        FLOAT                  null    ,
    pointValue                     FLOAT                  null    ,
    ulId                           VARCHAR2(128)          not null,
    marketUlId                     VARCHAR2(128)          null    ,
    isQuanto                       NUMBER(1)              null    ,
    quantoRate                     FLOAT                  null    ,
    decomposePosition              NUMBER(1)              null    ,
    maturity                       TIMESTAMP(3)           not null,
    strike                         FLOAT                  not null,
    exerciseStyle                  CHAR(1)                null    
        constraint CKC_EXERCICESTYLE_OPTIONPR check (exerciseStyle is null or (exerciseStyle in ('A','E'))),
    optionType                     CHAR(1)                not null
        constraint CKC_OPTIONTYPE_OPTIONPR check (optionType in ('C','P')),
    optionModel                    VARCHAR2(16)           not null,
    payoffDate                     TIMESTAMP(3)           null    ,
    isOtc                          NUMBER(1)              default 0 null    ,
    deliveryType                   VARCHAR2(32)           null    ,
    constraint PK_OPTIONPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : FutureProduct                                     
-- ============================================================
create table FutureProduct
(
    productId                      VARCHAR2(128)          not null,
    tickRuleId                     VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            not null,
    mic                            VARCHAR2(32)           null    ,
    lotSize                        INTEGER                null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    contractSizeNumerator          FLOAT                  null    ,
    contractSizeDenominator        FLOAT                  null    ,
    pointValue                     FLOAT                  null    ,
    ulId                           VARCHAR2(128)          not null,
    marketUlId                     VARCHAR2(128)          null    ,
    isQuanto                       NUMBER(1)              null    ,
    quantoRate                     FLOAT                  null    ,
    decomposePosition              NUMBER(1)              null    ,
    maturity                       TIMESTAMP(3)           not null,
    constraint PK_FUTUREPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : CombinationLeg                                    
-- ============================================================
create table CombinationLeg
(
    productId                      VARCHAR2(128)          not null,
    legProductId                   VARCHAR2(128)          not null,
    weight                         FLOAT                  null    ,
    name                           VARCHAR2(128)          null    ,
    isReference                    NUMBER(1)              null    ,
    constraint PK_COMBILEG primary key (productId, legProductId)
)
/

-- ============================================================
--   Table : Barrier                                           
-- ============================================================
create table Barrier
(
    productId                      VARCHAR2(128)          not null,
    rank                           INTEGER                not null,
    ulId                           VARCHAR2(128)          null    ,
    barrierType                    VARCHAR2(16)           not null
        constraint CKC_BARRIERTYPE_BARRIER check (barrierType in ('downAndIn','downAndOut','upAndOut','upAndIn')),
    barrierLevel                   FLOAT                  not null,
    isTouched                      NUMBER(1)              null    ,
    touchTimestamp                 TIMESTAMP(3)           null    ,
    payDateValue                   INTEGER                null    ,
    payDateUnit                    VARCHAR2(1)            null    
        constraint CKC_PAYDATEUNIT_BARRIER check (payDateUnit is null or (payDateUnit in ('D','W','M','Y','d','w','m','y'))),
    observationStartDate           TIMESTAMP(3)           null    ,
    constraint PK_BARRIER primary key (productId, rank)
)
/

-- ============================================================
--   Table : WarrantProduct                                    
-- ============================================================
create table WarrantProduct
(
    productId                      VARCHAR2(128)          not null,
    tickRuleId                     VARCHAR2(128)          null    ,
    currencyId                     VARCHAR2(3)            not null,
    mic                            VARCHAR2(32)           null    ,
    lotSize                        INTEGER                null    ,
    description                    NVARCHAR2(256)         null    ,
    name                           VARCHAR2(128)          null    ,
    contractSizeNumerator          FLOAT                  null    ,
    contractSizeDenominator        FLOAT                  null    ,
    pointValue                     FLOAT                  null    ,
    ulId                           VARCHAR2(128)          not null,
    marketUlId                     VARCHAR2(128)          null    ,
    isQuanto                       NUMBER(1)              null    ,
    quantoRate                     FLOAT                  null    ,
    decomposePosition              NUMBER(1)              null    ,
    maturity                       TIMESTAMP(3)           not null,
    strike                         FLOAT                  not null,
    exerciseStyle                  CHAR(1)                null    
        constraint CKC_EXERCICESTYLE_WARRANTP check (exerciseStyle is null or (exerciseStyle in ('A','E'))),
    optionType                     CHAR(1)                not null
        constraint CKC_OPTIONTYPE_WARRANTP check (optionType in ('C','P')),
    optionModel                    VARCHAR2(16)           not null,
    payoffDate                     TIMESTAMP(3)           null    ,
    isOtc                          NUMBER(1)              default 0 null    ,
    deliveryType                   VARCHAR2(32)           null    ,
    ulParity                       INTEGER                default 1 not null,
    warrantParity                  INTEGER                default 1 not null,
    constraint PK_WARRANTPRODUCT primary key (productId)
)
/

-- ============================================================
--   Table : YesterdayPosition                                 
-- ============================================================
create table YesterdayPosition
(
    productId                      VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    way                            VARCHAR2(1)            not null
        constraint CKC_WAY_YESTERDA check (way in ('2','B','S')),
    quantity                       INTEGER                not null,
    price                          FLOAT                  not null,
    delta                          FLOAT                  null    ,
    gamma                          FLOAT                  null    ,
    vega                           FLOAT                  null    ,
    theta                          FLOAT                  null    ,
    rho                            FLOAT                  null    ,
    mu                             FLOAT                  null    ,
    importTimestamp                TIMESTAMP(3)           null    ,
    generationTimestamp            TIMESTAMP(3)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    toHedge                        NUMBER(1)              null    ,
    spot                           FLOAT                  null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    productToPositionForex         FLOAT                  null    ,
    constraint PK_YESTERDAYPOSITION primary key (productId, portfolioId, context)
)
/

-- ============================================================
--   Table : RepoRate                                          
-- ============================================================
create table RepoRate
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_REPORATE check (priceType in ('TRADING','VALIDATION')),
    dayCount                       VARCHAR2(32)           null    ,
    interpolationModel             VARCHAR2(16)           null    
        constraint CKC_INTERPOLATIONMODE_REPORATE check (interpolationModel is null or (interpolationModel in ('LINEAR','SPLINE'))),
    weekEndRule                    VARCHAR2(32)           null    ,
    constraint PK_REPORATE primary key (productId, context, priceType)
)
/

-- ============================================================
--   Table : VolatilityMatrix                                  
-- ============================================================
create table VolatilityMatrix
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_VM check (priceType in ('TRADING','VALIDATION')),
    volatilityType                 VARCHAR2(16)           default 'FIXED' not null
        constraint CKC_VOLATILITYTYPE_VM check (volatilityType in ('FIXED','DEVFWD')),
    spotRef                        FLOAT                  null    ,
    snapshotDate                   TIMESTAMP(3)           null    ,
    updateTimestamp                TIMESTAMP(3)           null    ,
    interpolationModel             VARCHAR2(32)           null    ,
    maturityInterpolation          NUMBER(1)              null    ,
    constraint PK_VM primary key (productId, context, priceType, volatilityType)
)
/

-- ============================================================
--   Table : DynamicSkewVolatility                             
-- ============================================================
create table DynamicSkewVolatility
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_DSV check (priceType in ('TRADING','VALIDATION')),
    calcDate                       TIMESTAMP(3)           null    ,
    calendarDayShift               INTEGER                null    ,
    v0Offset                       FLOAT                  null    ,
    v0Cr                           FLOAT                  null    ,
    vInf                           FLOAT                  null    ,
    vInfCr                         FLOAT                  null    ,
    volConvexity                   FLOAT                  null    ,
    spotDate                       TIMESTAMP(3)           null    ,
    spot                           FLOAT                  null    ,
    spotRef                        FLOAT                  null    ,
    minStrike                      FLOAT                  null    ,
    maxStrike                      FLOAT                  null    ,
    step                           FLOAT                  null    ,
    updateTimestamp                TIMESTAMP(3)           null    ,
    constraint PK_DSV primary key (productId, context, priceType)
)
/

-- ============================================================
--   Table : StaticSkewVolatility                              
-- ============================================================
create table StaticSkewVolatility
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_SSV check (priceType in ('TRADING','VALIDATION')),
    calcDate                       TIMESTAMP(3)           null    ,
    spotRef                        FLOAT                  null    ,
    volAtmShift                    FLOAT                  null    ,
    skewShift                      FLOAT                  null    ,
    minStrike                      FLOAT                  null    ,
    maxStrike                      FLOAT                  null    ,
    step                           FLOAT                  null    ,
    optimizeVolatility             NUMBER(1)              null    ,
    updateTimestamp                TIMESTAMP(3)           null    ,
    constraint PK_SSV primary key (productId, context, priceType)
)
/

-- ============================================================
--   Table : AutofitDynamicSkew                                
-- ============================================================
create table AutofitDynamicSkew
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_ADS check (priceType in ('TRADING','VALIDATION')),
    calcDate                       TIMESTAMP(3)           null    ,
    updateTimestamp                TIMESTAMP(3)           null    ,
    constraint PK_ADS primary key (productId, context, priceType)
)
/

-- ============================================================
--   Table : CombinationPricing                                
-- ============================================================
create table CombinationPricing
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    spreadRuleGroupId              VARCHAR2(32)           null    ,
    cautiousLevel                  INTEGER                null    ,
    isActive                       NUMBER(1)              null    ,
    maxShiftValue                  FLOAT                  null    ,
    maxShiftType                   VARCHAR2(16)           null    ,
    isSpreadRuleGroupOverriden     NUMBER(1)              default 0 null    ,
    tradingVersion                 VARCHAR2(32)           default 'TRADING' null    ,
    validationVersion              VARCHAR2(32)           default 'VALIDATION' null    ,
    volMaxShiftValue               FLOAT                  null    ,
    volMaxShiftType                VARCHAR2(16)           null    ,
    spreadCurrency                 VARCHAR2(3)            null    ,
    roundingTickThreshold          FLOAT                  null    ,
    minTheo                        FLOAT                  null    ,
    minBidPrice                    FLOAT                  null    ,
    minAskPrice                    FLOAT                  null    ,
    pricingSource                  VARCHAR2(32)           null    ,
    constraint PK_COMBINATIONPRICING primary key (productId, context)
)
/

-- ============================================================
--   Table : PricingStream                                     
-- ============================================================
create table PricingStream
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    spotId                         VARCHAR2(128)          not null,
    priceVectorId                  VARCHAR2(32)           null    ,
    spreadRuleGroupId              VARCHAR2(32)           null    ,
    cautiousLevel                  INTEGER                null    ,
    isActive                       NUMBER(1)              null    ,
    maxShiftValue                  FLOAT                  null    ,
    maxShiftType                   VARCHAR2(16)           null    ,
    isSpreadRuleGroupOverriden     NUMBER(1)              default 0 null    ,
    tradingVersion                 VARCHAR2(32)           default 'TRADING' null    ,
    validationVersion              VARCHAR2(32)           default 'VALIDATION' null    ,
    volMaxShiftValue               FLOAT                  null    ,
    volMaxShiftType                VARCHAR2(16)           null    ,
    spreadCurrency                 VARCHAR2(3)            null    ,
    roundingTickThreshold          FLOAT                  null    ,
    minTheo                        FLOAT                  null    ,
    minBidPrice                    FLOAT                  null    ,
    minAskPrice                    FLOAT                  null    ,
    pricingSource                  VARCHAR2(32)           null    ,
    usedFixedVol                   NUMBER(1)              null    ,
    fixedVolValue                  FLOAT                  null    
        constraint CKC_FIXEDVOLVALUE_PRICINGS check (fixedVolValue is null or (fixedVolValue >= 0)),
    priceFieldName                 VARCHAR2(32)           default 'price' null    ,
    askFieldName                   VARCHAR2(32)           default 'ask' null    ,
    bidFieldName                   VARCHAR2(32)           default 'bid' null    ,
    initialPvSpot                  FLOAT                  null    ,
    isExternal                     NUMBER(1)              null    ,
    externalPublicationType        VARCHAR2(32)           null    ,
    spreadType                     VARCHAR2(32)           null    ,
    allowSingleSide                NUMBER(1)              null    ,
    forceMarketSpread              NUMBER(1)              null    ,
    constraint PK_PRICINGSTREAM primary key (productId, context)
)
/

-- ============================================================
--   Table : CombinationPricingLeg                             
-- ============================================================
create table CombinationPricingLeg
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    legProductId                   VARCHAR2(128)          not null,
    spotId                         VARCHAR2(128)          not null,
    priceFieldName                 VARCHAR2(32)           default 'price' null    ,
    interpolationType              VARCHAR2(16)           not null,
    applyRate                      NUMBER(1)              null    ,
    constraint PK_COMBINATIONPRICINGLEG primary key (productId, context, legProductId)
)
/

-- ============================================================
--   Table : ManualPriceVector                                 
-- ============================================================
create table ManualPriceVector
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    version                        VARCHAR2(32)           default 'TRADING' not null,
    stableRadiusInPercent          FLOAT                  null    ,
    radiusInPercent                FLOAT                  null    ,
    generationTimestamp            TIMESTAMP(3)           null    ,
    importTimestamp                TIMESTAMP(3)           null    ,
    manualRate                     FLOAT                  null    ,
    constraint PK_MANUALPRICEVECTOR primary key (productId, context, version)
)
/

-- ============================================================
--   Table : Spot                                              
-- ============================================================
create table Spot
(
    spotId                         VARCHAR2(128)          not null,
    imsId                          VARCHAR2(32)           not null,
    staleDelayMs                   INTEGER                null    ,
    limiterPeriodMs                INTEGER                null    ,
    priceFieldName                 VARCHAR2(32)           null    ,
    maxShiftValue                  FLOAT                  null    ,
    maxShiftType                   VARCHAR2(16)           null    ,
    manualValue                    FLOAT                  null    ,
    isManual                       NUMBER(1)              null    ,
    thresholdValue                 FLOAT                  null    ,
    thresholdUnit                  VARCHAR2(16)           null    
        constraint CKC_THRESHOLDUNIT_SPOT check (thresholdUnit is null or (thresholdUnit in ('IN_ABSOLUTE','IN_PERCENT'))),
    productId                      VARCHAR2(128)          null    ,
    publishDepth                   NUMBER(1)              default 0 null    ,
    fieldTemplateId                VARCHAR2(64)           null    ,
    isExcel                        NUMBER(1)              default 0 null    ,
    autoConfirmCheckPeriodMs       INTEGER                null    ,
    constraint PK_SPOT primary key (spotId)
)
/

-- ============================================================
--   Table : BrokerAddress                                     
-- ============================================================
create table BrokerAddress
(
    brokerExchangeId               VARCHAR2(64)           not null,
    mic                            VARCHAR2(32)           not null,
    brokerId                       VARCHAR2(64)           not null,
    constraint PK_BROKERADDRESS primary key (brokerExchangeId, mic)
)
/

-- ============================================================
--   Table : RFQBlotter                                        
-- ============================================================
create table RFQBlotter
(
    quoteRequestId                 VARCHAR2(128)          not null,
    userId                         VARCHAR2(64)           not null,
    brokerId                       VARCHAR2(64)           null    ,
    productId                      VARCHAR2(128)          not null,
    exchangeMic                    VARCHAR2(32)           null    ,
    requestTimestamp               TIMESTAMP(3)           not null,
    replyTimestamp                 TIMESTAMP(3)           null    ,
    tradeTimestamp                 TIMESTAMP(3)           null    ,
    responseType                   VARCHAR2(16)           null    ,
    responseTypeSource             VARCHAR2(16)           null    ,
    status                         VARCHAR2(16)           null    ,
    quoteReplyId                   VARCHAR2(128)          null    ,
    buyQty                         INTEGER                null    ,
    buyPrice                       FLOAT                  null    ,
    sellQty                        INTEGER                null    ,
    sellPrice                      FLOAT                  null    ,
    errorMessage                   VARCHAR2(256)          null    ,
    constraint PK_RFQBLOTTER primary key (quoteRequestId)
)
/

-- ============================================================
--   Table : CashExecution                                     
-- ============================================================
create table CashExecution
(
    tradeId                        VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    brokerExchangeId               VARCHAR2(64)           null    ,
    mic                            VARCHAR2(32)           null    ,
    userId                         VARCHAR2(64)           not null,
    imsId                          VARCHAR2(32)           not null,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_CASHEXEC check (way is null or (way in ('2','B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            not null
        constraint CKC_EXECUTIONSTATE_CASHEXEC check (executionState in ('n','c','u')),
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    hedgeRatio                     FLOAT                  default 1 null    ,
    hedgeUlSpot                    FLOAT                  null    ,
    hedgeProxyUlSpot               FLOAT                  null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    crossTradeId                   VARCHAR2(128)          null    ,
    feesData                       VARCHAR2(128)          null    ,
    isComplete                     NUMBER(1)              null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    productToHedgeForex            FLOAT                  null    ,
    shortSellType                  VARCHAR2(32)           null    ,
    tradeType                      VARCHAR2(64)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    hedgeSnapStatus                VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    hedgeForexSnapStatus           VARCHAR2(64)           null    ,
    strategyId                     VARCHAR2(128)          null    ,
    strategyTradeId                VARCHAR2(128)          null    ,
    constraint PK_CASHEXEC primary key (tradeId)
)
/

-- ============================================================
--   Table : DerivativeExecution                               
-- ============================================================
create table DerivativeExecution
(
    tradeId                        VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    brokerExchangeId               VARCHAR2(64)           null    ,
    mic                            VARCHAR2(32)           null    ,
    userId                         VARCHAR2(64)           not null,
    imsId                          VARCHAR2(32)           not null,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_DERIVATI check (way is null or (way in ('2','B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            not null
        constraint CKC_EXECUTIONSTATE_DERIVATI check (executionState in ('n','c','u')),
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    hedgeRatio                     FLOAT                  default 1 null    ,
    hedgeUlSpot                    FLOAT                  null    ,
    hedgeProxyUlSpot               FLOAT                  null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    crossTradeId                   VARCHAR2(128)          null    ,
    feesData                       VARCHAR2(128)          null    ,
    isComplete                     NUMBER(1)              null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    productToHedgeForex            FLOAT                  null    ,
    shortSellType                  VARCHAR2(32)           null    ,
    tradeType                      VARCHAR2(64)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    hedgeSnapStatus                VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    hedgeForexSnapStatus           VARCHAR2(64)           null    ,
    strategyId                     VARCHAR2(128)          null    ,
    strategyTradeId                VARCHAR2(128)          null    ,
    ulId                           VARCHAR2(128)          null    ,
    productToUlForex               FLOAT                  null    ,
    spot                           FLOAT                  null    ,
    theoriticalPrice               FLOAT                  null    ,
    delta                          FLOAT                  null    ,
    gamma                          FLOAT                  null    ,
    volatility                     FLOAT                  null    ,
    vega                           FLOAT                  null    ,
    theta                          FLOAT                  null    ,
    rho                            FLOAT                  null    ,
    mu                             FLOAT                  null    ,
    deltaUnderlying                FLOAT                  null    ,
    gammaUnderlying                FLOAT                  null    ,
    vegaUnderlying                 FLOAT                  null    ,
    thetaUnderlying                FLOAT                  null    ,
    rhoUnderlying                  FLOAT                  null    ,
    muUnderlying                   FLOAT                  null    ,
    spreadAmount                   FLOAT                  null    ,
    spreadDistribution             FLOAT                  null    ,
    askShiftUnit                   VARCHAR2(16)           null    ,
    askShiftValue                  FLOAT                  null    ,
    bidShiftUnit                   VARCHAR2(16)           null    ,
    bidShiftValue                  FLOAT                  null    ,
    priceShiftUnit                 VARCHAR2(16)           null    ,
    priceShiftValue                FLOAT                  null    ,
    askTickShift                   INTEGER                null    ,
    bidTickShift                   INTEGER                null    ,
    rawPrice                       FLOAT                  null    ,
    marketToPricingForex           FLOAT                  null    ,
    biases                         CLOB                   null    ,
    positionEffect                 INTEGER                null    ,
    constraint PK_DERIVATIVEEXEC primary key (tradeId)
)
/

-- ============================================================
--   Table : Scheduler                                         
-- ============================================================
create table Scheduler
(
    taskId                         INTEGER                not null,
    scheduleCommand                VARCHAR2(64)           not null,
    taskName                       VARCHAR2(64)           null    ,
    month                          INTEGER                null    ,
    dayOfMonth                     INTEGER                null    ,
    daysOfWeek                     INTEGER                null    ,
    weekNumber                     INTEGER                null    ,
    dayOfWeekNumber                INTEGER                null    ,
    scheduleTime                   VARCHAR2(5)            null    ,
    scheduleTimezone               VARCHAR2(128)          null    ,
    checkBeforeExecutionDelay      INTEGER                null    ,
    scheduleCommandParameters      VARCHAR2(2000)         null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    description                    VARCHAR2(512)          null    ,
    isEnabled                      NUMBER(1)              null    ,
    onSuccessTaskId                INTEGER                null    ,
    onWarningTaskId                INTEGER                null    ,
    onErrorTaskId                  INTEGER                null    ,
    owner                          VARCHAR2(64)           null    ,
    isTemplate                     NUMBER(1)              null    ,
    useTemplate                    NUMBER(1)              null    ,
    templateTaskId                 INTEGER                null    ,
    constraint PK_SCHEDULER primary key (taskId)
)
/

-- ============================================================
--   Table : PricingBatch                                      
-- ============================================================
create table PricingBatch
(
    pricingBatchId                 VARCHAR2(64)           not null,
    pricingPriority                VARCHAR2(16)           not null,
    pricingScenarioId              VARCHAR2(64)           not null,
    status                         VARCHAR2(32)           null    ,
    progress                       VARCHAR2(32)           null    ,
    requestedTimestamp             TIMESTAMP(3)           null    ,
    completedTimestamp             TIMESTAMP(3)           null    ,
    lastUpdate                     TIMESTAMP(3)           null    ,
    message                        VARCHAR2(256)          null    ,
    userComment                    VARCHAR2(256)          null    ,
    snap                           VARCHAR2(128)          null    ,
    requestType                    VARCHAR2(64)           null    ,
    isTemplate                     NUMBER(1)              null    ,
    userId                         VARCHAR2(64)           null    ,
    hasElements                    NUMBER(1)              null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    valuationDateType              VARCHAR2(16)           null    ,
    valuationDate                  TIMESTAMP(3)           null    ,
    constraint PK_PRICINGBATCH primary key (pricingBatchId)
)
/

-- ============================================================
--   Table : SpotField                                         
-- ============================================================
create table SpotField
(
    fieldTemplateId                VARCHAR2(64)           not null,
    fieldName                      VARCHAR2(32)           not null,
    spotRuleId                     VARCHAR2(32)           not null,
    constraint PK_SPOTFIELD primary key (fieldTemplateId, fieldName)
)
/

-- ============================================================
--   Table : LiffeMktSpreadRuleLevel                           
-- ============================================================
create table LiffeMktSpreadRuleLevel
(
    marketSpreadRuleId             VARCHAR2(32)           not null,
    bidPrice                       FLOAT                  not null,
    spreadUnit                     VARCHAR2(16)           not null,
    spreadValue                    FLOAT                  not null,
    spreadMax                      FLOAT                  null    ,
    constraint PK_LIFFEMKTSPREADRULELEVEL primary key (marketSpreadRuleId, bidPrice)
)
/

-- ============================================================
--   Table : LiffeMktSpreadRuleOptGroup                        
-- ============================================================
create table LiffeMktSpreadRuleOptGroup
(
    marketSpreadRuleId             VARCHAR2(32)           not null,
    floatingMaturityValue          INTEGER                not null,
    floatingMaturityUnit           CHAR(1)                not null
        constraint CKC_FLOATINGMATURITYU_LIFFEMKT check (floatingMaturityUnit in ('D','M','Y','d','m','y')),
    spotRangeMax                   FLOAT                  not null,
    exerciseStyle                  CHAR(1)                not null
        constraint CKC_EXERCICESTYLE_LIFFEMKT check (exerciseStyle in ('A','E')),
    ulLiquidity                    VARCHAR2(16)           null    ,
    constraint PK_LIFFEMKTSPREADRULEOPTGROUP primary key (marketSpreadRuleId, floatingMaturityValue, floatingMaturityUnit, spotRangeMax, exerciseStyle)
)
/

-- ============================================================
--   Table : Automaton                                         
-- ============================================================
create table Automaton
(
    imsId                          VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    spreadName                     VARCHAR2(64)           not null,
    cautiousLevel                  INTEGER                null    ,
    dynamicRuleGroupId             VARCHAR2(32)           null    ,
    askLegs                        INTEGER                default 1 not null,
    bidLegs                        INTEGER                default 1 not null,
    activateCapByMarket            NUMBER(1)              default 0 not null,
    activateVolumeSpread           NUMBER(1)              default 0 not null,
    scenarioType                   VARCHAR2(32)           not null,
    maxDayBuy                      NUMBER(15)             null    ,
    maxDaySell                     NUMBER(15)             null    ,
    properties                     VARCHAR2(2000)         null    ,
    isActive                       NUMBER(1)              null    ,
    ignoreBidInventory             NUMBER(1)              default 0 null    ,
    nakMaxRetries                  INTEGER                default 0 null    
        constraint CKC_NAKMAXRETRIES_AUTOMATO check (nakMaxRetries is null or (nakMaxRetries >= 0)),
    toHedge                        NUMBER(1)              default 0 null    ,
    useQuotes                      NUMBER(1)              default 0 null    ,
    quotingCurrency                VARCHAR2(3)            null    ,
    isDynamicRuleGroupOverriden    NUMBER(1)              default 0 null    ,
    marketProductId                VARCHAR2(128)          null    ,
    baseBidSize                    INTEGER                null    ,
    baseAskSize                    INTEGER                null    ,
    baseRefillStep                 INTEGER                null    ,
    baseRefillPeriodMs             INTEGER                null    ,
    allowSingleSideQuotes          NUMBER(1)              null    ,
    baseRfqPriceTolerance          FLOAT                  null    ,
    baseRfqPriceToleranceType      VARCHAR2(16)           default 'IN_ABSOLUTE' null    
        constraint CKC_BASERFQPRICETOLER_AUTOMATO check (baseRfqPriceToleranceType is null or (baseRfqPriceToleranceType in ('IN_ABSOLUTE','IN_PERCENT'))),
    baseFirstBidSize               INTEGER                null    ,
    baseFirstAskSize               INTEGER                null    ,
    quantityMode                   VARCHAR2(16)           null    ,
    minBaseBidSize                 FLOAT                  null    ,
    minBaseAskSize                 FLOAT                  null    ,
    qtyRandomizeDelayMs            INTEGER                null    ,
    choicePriceField               VARCHAR2(16)           null    ,
    constraint PK_AUTOMATON primary key (imsId, productId, context, spreadName)
)
/

-- ============================================================
--   Table : PricingStreamSpotConf                             
-- ============================================================
create table PricingStreamSpotConf
(
    spotId                         VARCHAR2(128)          not null,
    thresholdUnit                  VARCHAR2(16)           null    
        constraint CKC_THRESHOLDUNIT_PRICINGS check (thresholdUnit is null or (thresholdUnit in ('IN_ABSOLUTE','IN_PERCENT'))),
    thresholdValue                 FLOAT                  null    ,
    calcPeriodMs                   INTEGER                null    ,
    staleDelayMs                   INTEGER                null    ,
    constraint PK_PRICINGSTREAMSPOTCONF primary key (spotId)
)
/

-- ============================================================
--   Table : Shift                                             
-- ============================================================
create table Shift
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    spreadName                     VARCHAR2(64)           not null,
    version                        VARCHAR2(32)           default 'TRADING' not null,
    askShiftUnit                   VARCHAR2(16)           null    
        constraint CKC_ASKSHIFTUNIT_SHIFT check (askShiftUnit is null or (askShiftUnit in ('PRICE_ABS','PRICE_PCT','VOL_ABS','VOL_PCT','TICK'))),
    askShiftValue                  FLOAT                  null    ,
    bidShiftUnit                   VARCHAR2(16)           null    
        constraint CKC_BIDSHIFTUNIT_SHIFT check (bidShiftUnit is null or (bidShiftUnit in ('PRICE_ABS','PRICE_PCT','VOL_ABS','VOL_PCT','TICK'))),
    bidShiftValue                  FLOAT                  null    ,
    bidTickShift                   INTEGER                null    ,
    askTickShift                   INTEGER                null    ,
    shiftClassName                 VARCHAR2(128)          null    ,
    classProperties                VARCHAR2(512)          null    ,
    priceShiftValue                FLOAT                  null    ,
    priceShiftUnit                 VARCHAR2(16)           null    
        constraint CKC_PRICESHIFTUNIT_SHIFT check (priceShiftUnit is null or (priceShiftUnit in ('PRICE_ABS','PRICE_PCT','VOL_ABS','VOL_PCT','TICK'))),
    isNotProduct                   NUMBER(1)              null    ,
    bidShiftStep                   FLOAT                  default 1 null    ,
    priceShiftStep                 FLOAT                  default 1 null    ,
    askShiftStep                   FLOAT                  default 1 null    ,
    constraint PK_SHIFT primary key (productId, context, spreadName, version)
)
/

-- ============================================================
--   Table : Fixing                                            
-- ============================================================
create table Fixing
(
    productId                      VARCHAR2(128)          not null,
    fixingDate                     TIMESTAMP(3)           not null,
    fixingValue                    FLOAT                  null    ,
    fixingMinValue                 FLOAT                  null    ,
    fixingMaxValue                 FLOAT                  null    ,
    constraint PK_FIXING primary key (productId, fixingDate)
)
/

-- ============================================================
--   Table : DiscountFactor                                    
-- ============================================================
create table DiscountFactor
(
    currencyId                     VARCHAR2(3)            not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_DISCOUNT check (priceType in ('TRADING','VALIDATION')),
    discountDate                   TIMESTAMP(3)           not null,
    discountFactor                 FLOAT                  not null,
    rate                           FLOAT                  not null,
    constraint PK_DISCOUNTFACTOR primary key (currencyId, context, priceType, discountDate)
)
/

-- ============================================================
--   Table : BankHoliday                                       
-- ============================================================
create table BankHoliday
(
    mic                            VARCHAR2(32)           not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_BANKHOLI check (priceType in ('TRADING','VALIDATION')),
    holidayDate                    TIMESTAMP(3)           not null,
    constraint PK_BANKHOLIDAY primary key (mic, context, priceType, holidayDate)
)
/

-- ============================================================
--   Table : FutureBasis                                       
-- ============================================================
create table FutureBasis
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_FUTUREBA check (priceType in ('TRADING','VALIDATION')),
    basis                          FLOAT                  null    ,
    isBasisManual                  NUMBER(1)              null    ,
    constraint PK_FUTUREBASIS primary key (productId, context, priceType)
)
/

-- ============================================================
--   Table : IssueParameters                                   
-- ============================================================
create table IssueParameters
(
    productId                      VARCHAR2(128)          not null,
    issueSize                      INTEGER                not null,
    issueDate                      TIMESTAMP(3)           null    ,
    isSoldOut                      NUMBER(1)              null    ,
    buyQuantityThreshold           INTEGER                null    ,
    reservedQuantity               INTEGER                null    ,
    listingDate                    TIMESTAMP(3)           null    ,
    lastTradingDate                TIMESTAMP(3)           null    ,
    sellQuantityThreshold          INTEGER                null    ,
    axeWay                         CHAR(1)                null    
        constraint CKC_AXEWAY_ISSUEPAR check (axeWay is null or (axeWay in ('B','S'))),
    issuerId                       VARCHAR2(64)           null    ,
    constraint PK_ISSUEPARAMETERS primary key (productId)
)
/

-- ============================================================
--   Table : CashExecutionHistoric                             
-- ============================================================
create table CashExecutionHistoric
(
    tradeId                        VARCHAR2(128)          not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    portfolioId                    VARCHAR2(64)           not null,
    brokerExchangeId               VARCHAR2(64)           null    ,
    mic                            VARCHAR2(32)           null    ,
    userId                         VARCHAR2(64)           not null,
    imsId                          VARCHAR2(32)           not null,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_CEXECH check (way is null or (way in ('2','B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            not null
        constraint CKC_EXECUTIONSTATE_CEXECH check (executionState in ('n','c','u')),
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    hedgeRatio                     FLOAT                  default 1 null    ,
    hedgeUlSpot                    FLOAT                  null    ,
    hedgeProxyUlSpot               FLOAT                  null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    crossTradeId                   VARCHAR2(128)          null    ,
    feesData                       VARCHAR2(128)          null    ,
    isComplete                     NUMBER(1)              null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    productToHedgeForex            FLOAT                  null    ,
    shortSellType                  VARCHAR2(32)           null    ,
    tradeType                      VARCHAR2(64)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    hedgeSnapStatus                VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    hedgeForexSnapStatus           VARCHAR2(64)           null    ,
    strategyId                     VARCHAR2(128)          null    ,
    strategyTradeId                VARCHAR2(128)          null    ,
    constraint PK_CEXECH primary key (tradeId, archiveTimestamp)
)
/

-- ============================================================
--   Table : MarketRouting                                     
-- ============================================================
create table MarketRouting
(
    imsId                          VARCHAR2(32)           not null,
    userId                         VARCHAR2(64)           not null,
    ulId                           VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    productId                      VARCHAR2(128)          not null,
    sessionId                      VARCHAR2(64)           not null,
    constraint PK_MARKETROUTING primary key (imsId, userId, ulId, portfolioId, productId)
)
/

-- ============================================================
--   Table : DefaultPortfolio                                  
-- ============================================================
create table DefaultPortfolio
(
    userId                         VARCHAR2(64)           not null,
    ulId                           VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    productId                      VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    constraint PK_DEFAULTPORTFOLIO primary key (userId, ulId, context, productId)
)
/

-- ============================================================
--   Table : DerivativeExecutionHistoric                       
-- ============================================================
create table DerivativeExecutionHistoric
(
    tradeId                        VARCHAR2(128)          not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    portfolioId                    VARCHAR2(64)           not null,
    brokerExchangeId               VARCHAR2(64)           null    ,
    mic                            VARCHAR2(32)           null    ,
    userId                         VARCHAR2(64)           not null,
    imsId                          VARCHAR2(32)           not null,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_DEXECH check (way is null or (way in ('2','B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            not null
        constraint CKC_EXECUTIONSTATE_DEXECH check (executionState in ('n','c','u')),
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    hedgeRatio                     FLOAT                  default 1 null    ,
    hedgeUlSpot                    FLOAT                  null    ,
    hedgeProxyUlSpot               FLOAT                  null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    crossTradeId                   VARCHAR2(128)          null    ,
    feesData                       VARCHAR2(128)          null    ,
    isComplete                     NUMBER(1)              null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    productToHedgeForex            FLOAT                  null    ,
    shortSellType                  VARCHAR2(32)           null    ,
    tradeType                      VARCHAR2(64)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    hedgeSnapStatus                VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    hedgeForexSnapStatus           VARCHAR2(64)           null    ,
    strategyId                     VARCHAR2(128)          null    ,
    strategyTradeId                VARCHAR2(128)          null    ,
    ulId                           VARCHAR2(128)          null    ,
    productToUlForex               FLOAT                  null    ,
    spot                           FLOAT                  null    ,
    theoriticalPrice               FLOAT                  null    ,
    delta                          FLOAT                  null    ,
    gamma                          FLOAT                  null    ,
    volatility                     FLOAT                  null    ,
    vega                           FLOAT                  null    ,
    theta                          FLOAT                  null    ,
    rho                            FLOAT                  null    ,
    mu                             FLOAT                  null    ,
    deltaUnderlying                FLOAT                  null    ,
    gammaUnderlying                FLOAT                  null    ,
    vegaUnderlying                 FLOAT                  null    ,
    thetaUnderlying                FLOAT                  null    ,
    rhoUnderlying                  FLOAT                  null    ,
    muUnderlying                   FLOAT                  null    ,
    spreadAmount                   FLOAT                  null    ,
    spreadDistribution             FLOAT                  null    ,
    askShiftUnit                   VARCHAR2(16)           null    ,
    askShiftValue                  FLOAT                  null    ,
    bidShiftUnit                   VARCHAR2(16)           null    ,
    bidShiftValue                  FLOAT                  null    ,
    priceShiftUnit                 VARCHAR2(16)           null    ,
    priceShiftValue                FLOAT                  null    ,
    askTickShift                   INTEGER                null    ,
    bidTickShift                   INTEGER                null    ,
    rawPrice                       FLOAT                  null    ,
    marketToPricingForex           FLOAT                  null    ,
    biases                         CLOB                   null    ,
    positionEffect                 INTEGER                null    ,
    constraint PK_DEXECH primary key (tradeId, archiveTimestamp)
)
/

-- ============================================================
--   Table : UserContext                                       
-- ============================================================
create table UserContext
(
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    userId                         VARCHAR2(64)           not null,
    canModify                      NUMBER(1)              not null,
    constraint PK_USERCONTEXT primary key (context, userId)
)
/

-- ============================================================
--   Table : GLAddress                                         
-- ============================================================
create table GLAddress
(
    productId                      VARCHAR2(128)          not null,
    mnemo                          VARCHAR2(64)           null    ,
    glCode                         VARCHAR2(64)           null    ,
    constraint PK_GLADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : SophisAddress                                     
-- ============================================================
create table SophisAddress
(
    productId                      VARCHAR2(128)          not null,
    reference                      VARCHAR2(128)          null    ,
    externRef                      VARCHAR2(128)          null    ,
    sicovam                        VARCHAR2(16)           null    ,
    constraint PK_SOPHISADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : BloombergAddress                                  
-- ============================================================
create table BloombergAddress
(
    productId                      VARCHAR2(128)          not null,
    cusip                          VARCHAR2(16)           null    ,
    recordType                     VARCHAR2(32)           null    ,
    GDCO                           VARCHAR2(32)           null    ,
    monitorNb                      VARCHAR2(32)           null    ,
    pageNb                         VARCHAR2(32)           null    ,
    rowNb                          VARCHAR2(32)           null    ,
    entitlementId                  VARCHAR2(32)           null    ,
    constraint PK_BLOOMBERGADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : PricingUnitAddress                                
-- ============================================================
create table PricingUnitAddress
(
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    productType                    VARCHAR2(16)           not null,
    productId                      VARCHAR2(128)          not null,
    pricingUnitId                  VARCHAR2(16)           not null,
    constraint PK_PRICINGUNITADDRESS primary key (context, productType, productId)
)
/

-- ============================================================
--   Table : DeltaOnePricing                                   
-- ============================================================
create table DeltaOnePricing
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    version                        VARCHAR2(32)           default 'TRADING' not null,
    slope                          FLOAT                  null    ,
    offset                         FLOAT                  null    ,
    delta                          FLOAT                  null    ,
    pricingMethod                  VARCHAR2(16)           default 'ax+b' not null
        constraint CKC_PRICINGMETHOD_DELTAONE check (pricingMethod in ('ax+b','(x-b)/a','(b-x)/a')),
    generationTimestamp            TIMESTAMP(3)           null    ,
    importTimestamp                TIMESTAMP(3)           null    ,
    referenceSpot                  FLOAT                  null    ,
    radiusValue                    FLOAT                  null    ,
    radiusType                     VARCHAR2(64)           null    ,
    stableRadius                   FLOAT                  null    ,
    includeMargin                  NUMBER(1)              null    ,
    margin                         FLOAT                  null    ,
    manualRate                     FLOAT                  null    ,
    constraint PK_DELTAONEPRICING primary key (productId, context, version)
)
/

-- ============================================================
--   Table : ContextUnderlyingMember                           
-- ============================================================
create table ContextUnderlyingMember
(
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    ulId                           VARCHAR2(128)          not null,
    defaultSpotId                  VARCHAR2(32)           null    ,
    modelId                        VARCHAR2(128)          null    ,
    compactUnitId                  VARCHAR2(128)          null    ,
    constraint PK_CONTEXTUNDERLYINGMEMBER primary key (context, ulId)
)
/

-- ============================================================
--   Table : IMSParam                                          
-- ============================================================
create table IMSParam
(
    imsId                          VARCHAR2(32)           not null,
    paramName                      VARCHAR2(32)           not null,
    paramValue                     VARCHAR2(1024)         null    ,
    constraint PK_IMSPARAM primary key (imsId, paramName)
)
/

-- ============================================================
--   Table : UnresolvedExecution                               
-- ============================================================
create table UnresolvedExecution
(
    tradeId                        VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    brokerExchangeId               VARCHAR2(64)           null    ,
    mic                            VARCHAR2(32)           null    ,
    userId                         VARCHAR2(64)           not null,
    imsId                          VARCHAR2(32)           not null,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_UNRESOLV check (way is null or (way in ('2','B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            not null
        constraint CKC_EXECUTIONSTATE_UNRESOLV check (executionState in ('n','c','u')),
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    hedgeRatio                     FLOAT                  default 1 null    ,
    hedgeUlSpot                    FLOAT                  null    ,
    hedgeProxyUlSpot               FLOAT                  null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    crossTradeId                   VARCHAR2(128)          null    ,
    feesData                       VARCHAR2(128)          null    ,
    isComplete                     NUMBER(1)              null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    productToHedgeForex            FLOAT                  null    ,
    shortSellType                  VARCHAR2(32)           null    ,
    tradeType                      VARCHAR2(64)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    hedgeSnapStatus                VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    hedgeForexSnapStatus           VARCHAR2(64)           null    ,
    strategyId                     VARCHAR2(128)          null    ,
    strategyTradeId                VARCHAR2(128)          null    ,
    boughtCurrency                 VARCHAR2(3)            null    ,
    boughtAmount                   FLOAT                  null    ,
    soldCurrency                   VARCHAR2(3)            null    ,
    soldAmount                     FLOAT                  null    ,
    positionEffect                 INTEGER                null    ,
    constraint PK_UNRESOLVEDEXECUTION primary key (tradeId)
)
/

-- ============================================================
--   Table : SpotProcessorParameter                            
-- ============================================================
create table SpotProcessorParameter
(
    spotId                         VARCHAR2(128)          not null,
    parameterId                    VARCHAR2(128)          not null,
    parameterValue                 VARCHAR2(255)          null    ,
    isProductIdentifier            NUMBER(1)              null    ,
    isComputedValue                NUMBER(1)              null    ,
    constraint PK_SPOTPROCESSORPARAMETER primary key (spotId, parameterId)
)
/

-- ============================================================
--   Table : SpotProcessorValidation                           
-- ============================================================
create table SpotProcessorValidation
(
    formulaId                      VARCHAR2(128)          not null,
    formulaDescription             VARCHAR2(255)          null    ,
    formulaValue                   VARCHAR2(512)          null    ,
    statusFormulaValue             VARCHAR2(512)          null    ,
    statusValue                    INTEGER                null    ,
    constraint PK_SPOTPROCESSORVALIDATION primary key (formulaId)
)
/

-- ============================================================
--   Table : SpotProcessorField                                
-- ============================================================
create table SpotProcessorField
(
    spotId                         VARCHAR2(128)          not null,
    fieldId                        VARCHAR2(128)          not null,
    description                    VARCHAR2(256)          null    ,
    computationFormula             VARCHAR2(128)          not null,
    validationFormula              VARCHAR2(128)          null    ,
    toForward                      NUMBER(1)              null    ,
    skipThresholdCheck             NUMBER(1)              null    ,
    constraint PK_SPOTPROCESSORFIELD primary key (spotId, fieldId)
)
/

-- ============================================================
--   Table : BarrierMonitor                                    
-- ============================================================
create table BarrierMonitor
(
    productId                      VARCHAR2(128)          not null,
    rank                           INTEGER                not null,
    thresholdAlert                 FLOAT                  null    ,
    spotId                         VARCHAR2(128)          not null,
    spotFieldName                  VARCHAR2(32)           not null,
    isManual                       NUMBER(1)              null    ,
    isAcknowledged                 NUMBER(1)              null    ,
    triggerClassName               VARCHAR2(128)          null    ,
    classProperties                VARCHAR2(512)          null    ,
    monitorClassName               VARCHAR2(255)          null    ,
    redemptionPrice                FLOAT                  null    ,
    manualRedemptionPrice          FLOAT                  null    ,
    redemptionFx                   FLOAT                  null    ,
    manualRedemptionFx             FLOAT                  null    ,
    constraint PK_BARRIERMONITOR primary key (productId, rank)
)
/

-- ============================================================
--   Table : AutoBias                                          
-- ============================================================
create table AutoBias
(
    owner                          VARCHAR2(255)          not null,
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           not null,
    startDate                      TIMESTAMP(3)           null    ,
    autoBiasRuleId                 VARCHAR2(64)           null    ,
    biasType                       VARCHAR2(16)           null    ,
    bias                           FLOAT                  null    ,
    isManual                       NUMBER(1)              null    ,
    manualBias                     FLOAT                  null    ,
    constraint PK_AUTOBIAS primary key (owner, productId, context)
)
/

-- ============================================================
--   Table : ProductAutofitData                                
-- ============================================================
create table ProductAutofitData
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_PRODUCTA check (priceType in ('TRADING','VALIDATION')),
    dataTimestamp                  TIMESTAMP(3)           not null,
    isTradingTime                  NUMBER(1)              null    ,
    rate                           FLOAT                  null    ,
    discountFactor                 FLOAT                  null    ,
    yield                          FLOAT                  null    ,
    repo                           FLOAT                  null    ,
    spot                           FLOAT                  null    ,
    forward                        FLOAT                  null    ,
    marketBid                      FLOAT                  null    ,
    marketAsk                      FLOAT                  null    ,
    settlementPrice                FLOAT                  null    ,
    impliedVolBid                  FLOAT                  null    ,
    impliedVolAsk                  FLOAT                  null    ,
    volume                         NUMBER(15)             null    ,
    iterationCount                 INTEGER                null    ,
    constraint PK_PRODUCTAUTOFITDATA primary key (productId, context, priceType)
)
/

-- ============================================================
--   Table : ManualPriceVectorPoint                            
-- ============================================================
create table ManualPriceVectorPoint
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    version                        VARCHAR2(32)           default 'TRADING' not null,
    spot                           FLOAT                  not null,
    price                          FLOAT                  null    ,
    delta                          FLOAT                  null    ,
    gamma                          FLOAT                  null    ,
    vega                           FLOAT                  null    ,
    volatility                     FLOAT                  null    ,
    rho                            FLOAT                  null    ,
    theta                          FLOAT                  null    ,
    repoRho                        FLOAT                  null    ,
    epsilon                        FLOAT                  null    ,
    constraint PK_MANUALPRICEVECTORPOINT primary key (productId, context, version, spot)
)
/

-- ============================================================
--   Table : ProductInformation                                
-- ============================================================
create table ProductInformation
(
    productId                      VARCHAR2(128)          not null,
    labelKey                       VARCHAR2(32)           not null,
    labelValue                     VARCHAR2(64)           null    ,
    constraint PK_PRODUCTINFORMATION primary key (productId, labelKey)
)
/

-- ============================================================
--   Table : BorrowingCost                                     
-- ============================================================
create table BorrowingCost
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_BORROWIN check (priceType in ('TRADING','VALIDATION')),
    borrowingDate                  TIMESTAMP(3)           not null,
    borrowingValue                 FLOAT                  null    ,
    repoRate                       FLOAT                  default 0 not null,
    constraint PK_BORROWINGCOST primary key (productId, context, priceType, borrowingDate)
)
/

-- ============================================================
--   Table : YieldCurvePoint                                   
-- ============================================================
create table YieldCurvePoint
(
    currencyId                     VARCHAR2(3)            not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_YCPOINT check (priceType in ('TRADING','VALIDATION')),
    maturityValue                  INTEGER                not null,
    maturityUnit                   VARCHAR2(1)            not null
        constraint CKC_MATURITYUNIT_YCPOINT check (maturityUnit in ('d','w','m','y','D','W','M','Y')),
    rate                           FLOAT                  null    ,
    constraint PK_YCPOINT primary key (currencyId, context, priceType, maturityValue, maturityUnit)
)
/

-- ============================================================
--   Table : RepoRatePoint                                     
-- ============================================================
create table RepoRatePoint
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_RRPOINT check (priceType in ('TRADING','VALIDATION')),
    maturityValue                  INTEGER                not null,
    maturityUnit                   VARCHAR2(1)            not null
        constraint CKC_MATURITYUNIT_RRPOINT check (maturityUnit in ('d','w','m','y','D','W','M','Y')),
    rate                           FLOAT                  null    ,
    constraint PK_RRPOINT primary key (productId, context, priceType, maturityValue, maturityUnit)
)
/

-- ============================================================
--   Table : Contribution                                      
-- ============================================================
create table Contribution
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    imsId                          VARCHAR2(32)           not null,
    isActive                       NUMBER(1)              null    ,
    bidSize                        INTEGER                null    ,
    askSize                        INTEGER                null    ,
    quotingCurrency                VARCHAR2(3)            null    ,
    choicePriceField               VARCHAR2(16)           null    ,
    quotePublicationPeriod         INTEGER                null    ,
    constraint PK_CONTRIBUTION primary key (productId, context, imsId)
)
/

-- ============================================================
--   Table : VolatilityMatrixPoint                             
-- ============================================================
create table VolatilityMatrixPoint
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_VMP check (priceType in ('TRADING','VALIDATION')),
    volatilityType                 VARCHAR2(16)           default 'FIXED' not null
        constraint CKC_VOLATILITYTYPE_VMP check (volatilityType in ('FIXED','DEVFWD')),
    maturity                       TIMESTAMP(3)           not null,
    strike                         FLOAT                  not null,
    volatility                     FLOAT                  null    ,
    constraint PK_VMP primary key (productId, context, priceType, volatilityType, maturity, strike)
)
/

-- ============================================================
--   Table : ReutersAddress                                    
-- ============================================================
create table ReutersAddress
(
    productId                      VARCHAR2(128)          not null,
    ric                            VARCHAR2(32)           not null,
    service                        VARCHAR2(64)           not null,
    groupId                        VARCHAR2(64)           not null,
    depthLinkPath                  VARCHAR2(64)           null    ,
    linkedRicPath                  VARCHAR2(64)           null    ,
    rdnExchangeId                  VARCHAR2(64)           null    ,
    constraint PK_REUTERSADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : ForwardRolling                                    
-- ============================================================
create table ForwardRolling
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_FORWARDR check (priceType in ('TRADING','VALIDATION')),
    maturity                       TIMESTAMP(3)           not null,
    future                         FLOAT                  null    ,
    rolling                        FLOAT                  null    ,
    basis                          FLOAT                  null    ,
    dividend                       FLOAT                  null    ,
    rate                           FLOAT                  null    ,
    dayShift                       INTEGER                null    ,
    snapshotDate                   TIMESTAMP(3)           null    ,
    constraint PK_FORWARDROLLING primary key (productId, context, priceType, maturity)
)
/

-- ============================================================
--   Table : DefaultCustomFields                               
-- ============================================================
create table DefaultCustomFields
(
    userId                         VARCHAR2(64)           not null,
    ulId                           VARCHAR2(128)          not null,
    productType                    VARCHAR2(16)           default '*' not null,
    imsId                          VARCHAR2(64)           not null,
    transactionType                VARCHAR2(16)           default 'ORDER' not null,
    dictBranch                     VARCHAR2(256)          not null,
    customFields                   VARCHAR2(2000)         null    ,
    customFieldDependencies        VARCHAR2(2000)         null    ,
    constraint PK_DEFAULTCUSTOMFIELDS primary key (userId, ulId, productType, imsId, transactionType, dictBranch)
)
/

-- ============================================================
--   Table : PricingConfig                                     
-- ============================================================
create table PricingConfig
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_PRICINGC check (priceType in ('TRADING','VALIDATION')),
    productType                    VARCHAR2(16)           default '*' not null,
    maturityThresholdValue         INTEGER                not null,
    maturityThresholdUnit          VARCHAR2(16)           not null,
    forwardComputation             VARCHAR2(32)           default 'MANUAL' null    ,
    nbTradingDays                  FLOAT                  null    ,
    greekComputation               VARCHAR2(32)           null    ,
    atmVolAxis                     VARCHAR2(32)           default 'DATE' null    ,
    volModel                       VARCHAR2(32)           default 'ROLLING_SKEW' null    ,
    stepsNumber                    INTEGER                null    ,
    pricingModel                   VARCHAR2(16)           default 'DEFFAULT' not null,
    dayCount                       VARCHAR2(16)           null    ,
    maturityOffset                 INTEGER                null    ,
    timeManagement                 INTEGER                null    ,
    useCharm                       NUMBER(1)              null    ,
    pvExpirationDelayInMinute      INTEGER                null    ,
    constraint PK_PRICINGCONFIG primary key (productId, context, priceType, productType, maturityThresholdValue, maturityThresholdUnit)
)
/

-- ============================================================
--   Table : CashOperation                                     
-- ============================================================
create table CashOperation
(
    operationId                    VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    userId                         VARCHAR2(64)           not null,
    productId                      VARCHAR2(128)          null    ,
    amount                         FLOAT                  null    ,
    currencyId                     VARCHAR2(3)            null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    userComment                    NVARCHAR2(512)         null    ,
    operationType                  VARCHAR2(32)           not null
        constraint CKC_OPERATIONTYPE_CASHOPER check (operationType in ('ADJUSTMENT','FEE','SETTLEMENT','PNL')),
    constraint PK_CASHOPERATION primary key (operationId)
)
/

-- ============================================================
--   Table : SelfMatchingBypass                                
-- ============================================================
create table SelfMatchingBypass
(
    productId                      VARCHAR2(128)          not null,
    bypass                         NUMBER(1)              null    ,
    constraint PK_SELFMATCHINGBYPASS primary key (productId)
)
/

-- ============================================================
--   Table : ProductClientInfo                                 
-- ============================================================
create table ProductClientInfo
(
    productId                      VARCHAR2(128)          not null,
    assetClass                     VARCHAR2(256)          null    ,
    clientProductId                VARCHAR2(256)          null    ,
    pricingSystem                  VARCHAR2(256)          null    ,
    bookingSystem                  VARCHAR2(256)          null    ,
    settlementType                 VARCHAR2(256)          null    ,
    productType                    VARCHAR2(64)           null    ,
    location                       VARCHAR2(256)          null    ,
    constraint PK_PRODUCTCLIENTINFO primary key (productId)
)
/

-- ============================================================
--   Table : PerfMonitorConfig                                 
-- ============================================================
create table PerfMonitorConfig
(
    imsId                          VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    ruleId                         VARCHAR2(128)          not null,
    constraint PK_PERFMONITORCONFIG primary key (imsId, productId, context)
)
/

-- ============================================================
--   Table : ExchBookingParam                                  
-- ============================================================
create table ExchBookingParam
(
    portfolioId                    VARCHAR2(64)           not null,
    mic                            VARCHAR2(32)           not null,
    deltaLegalEntity               VARCHAR2(64)           null    ,
    deltaWashBook                  VARCHAR2(64)           null    ,
    deltaCounterpartyId            VARCHAR2(64)           null    ,
    isDeltaMarketSide              NUMBER(1)              null    ,
    productLegalEntity             VARCHAR2(64)           null    ,
    productWashBook                VARCHAR2(64)           null    ,
    productCounterpartyId          VARCHAR2(64)           null    ,
    isProductMarketSide            NUMBER(1)              null    ,
    constraint PK_EXCHBOOKINGPARAM primary key (portfolioId, mic)
)
/

-- ============================================================
--   Table : SpotProcessorInitialization                       
-- ============================================================
create table SpotProcessorInitialization
(
    spotId                         VARCHAR2(128)          not null,
    formulaId                      VARCHAR2(128)          not null,
    constraint PK_SPOTPROCESSORINITIALIZATION primary key (spotId, formulaId)
)
/

-- ============================================================
--   Table : UserPortfolio                                     
-- ============================================================
create table UserPortfolio
(
    portfolioId                    VARCHAR2(64)           not null,
    userId                         VARCHAR2(64)           not null,
    isFavourite                    NUMBER(1)              null    ,
    constraint PK_USERPORTFOLIO primary key (portfolioId, userId)
)
/

-- ============================================================
--   Table : TradeReportingAddress                             
-- ============================================================
create table TradeReportingAddress
(
    imsId                          VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    targetProductId                VARCHAR2(128)          not null,
    targetImsId                    VARCHAR2(32)           not null,
    constraint PK_TRADEREPORTINGADDRESS primary key (imsId, productId)
)
/

-- ============================================================
--   Table : TradeReportStatus                                 
-- ============================================================
create table TradeReportStatus
(
    generatedUserData              VARCHAR2(512)          not null,
    tradeId                        VARCHAR2(128)          not null,
    status                         VARCHAR2(16)           null    ,
    message                        VARCHAR2(256)          null    ,
    reportedTradeId                VARCHAR2(128)          null    ,
    constraint PK_TRADEREPORTSTATUS primary key (generatedUserData)
)
/

-- ============================================================
--   Table : SchedulerActivity                                 
-- ============================================================
create table SchedulerActivity
(
    eventId                        VARCHAR2(64)           not null,
    taskId                         INTEGER                not null,
    activityTimestamp              TIMESTAMP(3)           null    ,
    scheduleStatus                 INTEGER                null    ,
    scheduleStatusMessage          VARCHAR2(2000)         null    ,
    scheduleCommandParameters      VARCHAR2(2000)         null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    constraint PK_SCHEDULERACTIVITY primary key (eventId)
)
/

-- ============================================================
--   Table : PerformanceBiasConfig                             
-- ============================================================
create table PerformanceBiasConfig
(
    owner                          VARCHAR2(255)          not null,
    productId                      VARCHAR2(135)          not null,
    context                        VARCHAR2(32)           not null,
    startDate                      TIMESTAMP(3)           null    ,
    performanceBiasRuleId          VARCHAR2(100)          null    ,
    startValue                     FLOAT                  null    ,
    constraint PK_PERFORMANCEBIASCONFIG primary key (owner, productId, context)
)
/

-- ============================================================
--   Table : BrokerClientInfo                                  
-- ============================================================
create table BrokerClientInfo
(
    brokerId                       VARCHAR2(64)           not null,
    propertyId                     VARCHAR2(256)          not null,
    propertyValue                  VARCHAR2(256)          null    ,
    constraint PK_BROKERCLIENTINFO primary key (brokerId, propertyId)
)
/

-- ============================================================
--   Table : IMSUserParam                                      
-- ============================================================
create table IMSUserParam
(
    imsId                          VARCHAR2(32)           not null,
    userId                         VARCHAR2(64)           not null,
    paramName                      VARCHAR2(64)           not null,
    paramValue                     VARCHAR2(1024)         null    ,
    constraint PK_IMSUSERPARAM primary key (imsId, userId, paramName)
)
/

-- ============================================================
--   Table : PasswordHistory                                   
-- ============================================================
create table PasswordHistory
(
    userId                         VARCHAR2(64)           not null,
    changeDate                     TIMESTAMP(3)           not null,
    password                       VARCHAR2(255)          null    ,
    constraint PK_PASSWORDHISTORY primary key (userId, changeDate)
)
/

-- ============================================================
--   Table : PairTradingAutomaton                              
-- ============================================================
create table PairTradingAutomaton
(
    pairTradingAutomatonId         VARCHAR2(128)          not null,
    pairTradingSafetyNetId         VARCHAR2(32)           null    ,
    description                    VARCHAR2(256)          null    ,
    primaryPrice                   VARCHAR2(32)           null    ,
    primarySide                    VARCHAR2(32)           null    ,
    primaryStrategy                VARCHAR2(32)           null    ,
    primaryInstrument              VARCHAR2(128)          null    ,
    hedgePrice                     VARCHAR2(32)           null    ,
    hedgeBracket                   INTEGER                null    ,
    hedgeInstrument                VARCHAR2(128)          null    ,
    primaryDynRuleId               VARCHAR2(32)           null    ,
    hedgeDynRuleId                 VARCHAR2(32)           null    ,
    spreadId                       VARCHAR2(32)           null    ,
    primaryProperties              VARCHAR2(2000)         null    ,
    primaryNakMaxRetries           INTEGER                null    ,
    hedgeNakMaxRetries             INTEGER                null    ,
    hedgeBasketComponentsSession   VARCHAR2(32)           null    ,
    hedgeBasket                    NUMBER(1)              null    ,
    slPercentOffset                FLOAT                  null    ,
    slTickOffset                   INTEGER                null    ,
    slOppositeTickShift            INTEGER                null    ,
    slEnabled                      NUMBER(1)              null    ,
    primaryPriceFormula            VARCHAR2(2000)         null    ,
    hedgeQuantityFormula           VARCHAR2(2000)         null    ,
    hedgePriceTickOffset           INTEGER                null    ,
    hedgePricePercentOffset        FLOAT                  null    ,
    primaryStrategyDepthNum        INTEGER                null    ,
    primaryStrategyDepthTicks      INTEGER                null    ,
    isActive                       NUMBER(1)              null    ,
    ownerId                        VARCHAR2(32)           null    ,
    depthCheckPeriod               INTEGER                null    ,
    primaryBaseBidSize             INTEGER                null    ,
    primaryBaseAskSize             INTEGER                null    ,
    primaryBaseRefillStep          INTEGER                null    ,
    primaryBaseRefillPeriodMs      INTEGER                null    ,
    hedgeBaseBidSize               INTEGER                null    ,
    hedgeBaseAskSize               INTEGER                null    ,
    hedgeBaseRefillStep            INTEGER                null    ,
    hedgeBaseRefillPeriodMs        INTEGER                null    ,
    hedgeAllowBothSides            NUMBER(1)              null    ,
    automaticHedge                 NUMBER(1)              null    ,
    primaryMaxDayBuy               INTEGER                null    ,
    primaryMaxDaySell              INTEGER                null    ,
    slPrice                        VARCHAR2(32)           null    ,
    primaryCustomFields            VARCHAR2(2000)         null    ,
    hedgeCustomFields              VARCHAR2(2000)         null    ,
    orderSensitivity               FLOAT                  null    ,
    hedgeRoundingThreshold         FLOAT                  null    ,
    pairTradingUnitId              VARCHAR2(16)           default 'unit1' null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    positionCurrency               VARCHAR2(3)            null    ,
    positionUpdatePeriodMs         INTEGER                null    ,
    includeExecsOnRestart          NUMBER(1)              null    ,
    basketQuantityRounding         VARCHAR2(16)           null    ,
    forexProductId                 VARCHAR2(128)          null    ,
    fxTransactionImsId             VARCHAR2(32)           null    ,
    activateVolumeSpread           NUMBER(1)              null    ,
    levelQty                       INTEGER                null    ,
    coolDownDelay                  INTEGER                null    ,
    ignorePartExecs                NUMBER(1)              null    ,
    levelSizes                     VARCHAR2(32)           null    ,
    levels                         VARCHAR2(32)           null    ,
    spotStaleDelay                 INTEGER                null    ,
    backupHedgeInstrument          VARCHAR2(128)          null    ,
    currentHedgeInstrument         VARCHAR2(32)           null    ,
    useExternalPrice               NUMBER(1)              null    ,
    primaryMinBaseBidSize          INTEGER                null    ,
    primaryMinBaseAskSize          INTEGER                null    ,
    primaryPortfolio               VARCHAR2(64)           null    ,
    hedgePortfolio                 VARCHAR2(64)           null    ,
    spotterFieldTemplateId         VARCHAR2(64)           null    ,
    constraint PK_PAIRTRADINGAUTOMATON primary key (pairTradingAutomatonId)
)
/

-- ============================================================
--   Table : ScheduledSpotPeriod                               
-- ============================================================
create table ScheduledSpotPeriod
(
    scheduledSpotId                VARCHAR2(128)          not null,
    switchTime                     VARCHAR2(8)            not null,
    switchDate                     VARCHAR2(10)           not null,
    spotPolicy                     VARCHAR2(64)           null    ,
    proxySpotId                    VARCHAR2(128)          null    ,
    fieldToSnap                    VARCHAR2(128)          null    ,
    latestSnapshotId               VARCHAR2(128)          null    ,
    description                    VARCHAR2(256)          null    ,
    constraint PK_SCHEDULEDSPOTPERIOD primary key (scheduledSpotId, switchTime, switchDate)
)
/

-- ============================================================
--   Table : DefaultIMSAddress                                 
-- ============================================================
create table DefaultIMSAddress
(
    productId                      VARCHAR2(128)          not null,
    imsId                          VARCHAR2(32)           null    ,
    constraint PK_DEFAULTIMSADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : IsinAddress                                       
-- ============================================================
create table IsinAddress
(
    productId                      VARCHAR2(128)          not null,
    isin                           VARCHAR2(16)           null    ,
    constraint PK_ISINADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : WknAddress                                        
-- ============================================================
create table WknAddress
(
    productId                      VARCHAR2(128)          not null,
    wkn                            VARCHAR2(16)           null    ,
    constraint PK_WKNADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : ValorenAddress                                    
-- ============================================================
create table ValorenAddress
(
    productId                      VARCHAR2(128)          not null,
    valoren                        VARCHAR2(16)           null    ,
    constraint PK_VALORENADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : SedolAddress                                      
-- ============================================================
create table SedolAddress
(
    productId                      VARCHAR2(128)          not null,
    sedol                          VARCHAR2(16)           null    ,
    constraint PK_SEDOLADDRESS primary key (productId)
)
/

-- ============================================================
--   Table : IMSUserSecurity                                   
-- ============================================================
create table IMSUserSecurity
(
    userId                         VARCHAR2(64)           not null,
    imsGroup                       VARCHAR2(32)           not null,
    groups                         VARCHAR2(255)          null    ,
    monitors                       VARCHAR2(255)          null    ,
    reparents                      VARCHAR2(255)          null    ,
    onBehalfs                      VARCHAR2(255)          null    ,
    forces                         VARCHAR2(255)          null    ,
    hasStream                      NUMBER(1)              null    ,
    canTrade                       NUMBER(1)              null    ,
    canReap                        NUMBER(1)              null    ,
    canUseFunction                 NUMBER(1)              null    ,
    deleteOnBehalfs                VARCHAR2(255)          null    ,
    updateOnBehalfs                VARCHAR2(255)          null    ,
    insertOnBehalfs                VARCHAR2(255)          null    ,
    adminRights                    VARCHAR2(255)          null    ,
    constraint PK_IMSUSERSECURITY primary key (userId, imsGroup)
)
/

-- ============================================================
--   Table : StrategyLeg                                       
-- ============================================================
create table StrategyLeg
(
    productId                      VARCHAR2(128)          not null,
    legProductId                   VARCHAR2(128)          not null,
    weight                         FLOAT                  null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_STRATEGY check (way is null or (way in ('2','B','S'))),
    price                          FLOAT                  null    ,
    name                           VARCHAR2(128)          null    ,
    constraint PK_STRATEGYLEG primary key (productId, legProductId)
)
/

-- ============================================================
--   Table : PricingSensitivity                                
-- ============================================================
create table PricingSensitivity
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    legProductId                   VARCHAR2(128)          not null,
    version                        VARCHAR2(32)           default 'TRADING' not null,
    radiusValue                    FLOAT                  null    ,
    radiusType                     VARCHAR2(64)           null    ,
    delta                          FLOAT                  null    ,
    gamma                          FLOAT                  null    ,
    vega                           FLOAT                  null    ,
    theta                          FLOAT                  null    ,
    epsilon                        FLOAT                  null    ,
    rho                            FLOAT                  null    ,
    volatility                     FLOAT                  null    ,
    generationTimestamp            TIMESTAMP(3)           null    ,
    importTimestamp                TIMESTAMP(3)           null    ,
    stableRadius                   FLOAT                  null    ,
    reference                      FLOAT                  null    ,
    manualRate                     FLOAT                  null    ,
    constraint PK_PRICINGSENSITIVITY primary key (productId, context, legProductId, version)
)
/

-- ============================================================
--   Table : BasketComposition                                 
-- ============================================================
create table BasketComposition
(
    basketId                       VARCHAR2(64)           not null,
    componentId                    VARCHAR2(64)           not null,
    mic                            VARCHAR2(32)           null    ,
    weight                         FLOAT                  null    ,
    capitalization                 FLOAT                  null    ,
    customGrouping1                VARCHAR2(64)           null    ,
    customGrouping2                VARCHAR2(64)           null    ,
    streamImsId                    VARCHAR2(32)           null    ,
    transactionImsId               VARCHAR2(32)           null    ,
    referencePrice                 FLOAT                  null    ,
    forexRate                      FLOAT                  null    ,
    constraint PK_BASKETCOMPOSITION primary key (basketId, componentId)
)
/

-- ============================================================
--   Table : SSMPosition                                       
-- ============================================================
create table SSMPosition
(
    productId                      VARCHAR2(128)          not null,
    ssmPortfolioId                 VARCHAR2(64)           not null,
    position                       INTEGER                null    ,
    hedgePosition                  INTEGER                null    ,
    borrowing                      INTEGER                null    ,
    lending                        INTEGER                null    ,
    marginQuantity                 INTEGER                null    ,
    constraint PK_SSMPOSITION primary key (productId, ssmPortfolioId)
)
/

-- ============================================================
--   Table : PricingBatchElement                               
-- ============================================================
create table PricingBatchElement
(
    pricingBatchId                 VARCHAR2(64)           not null,
    pricingBatchElementId          VARCHAR2(128)          not null,
    pricingSystemProductId         VARCHAR2(128)          null    ,
    status                         VARCHAR2(32)           null    ,
    message                        VARCHAR2(256)          null    ,
    calculationDuration            INTEGER                null    ,
    queuedDuration                 INTEGER                null    ,
    batchElementType               VARCHAR2(16)           null    ,
    pricingSystem                  VARCHAR2(256)          null    ,
    constraint PK_PRICINGBATCHELEMENT primary key (pricingBatchId, pricingBatchElementId)
)
/

-- ============================================================
--   Table : DynamicSkewVolatilityPoint                        
-- ============================================================
create table DynamicSkewVolatilityPoint
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_DSVP check (priceType in ('TRADING','VALIDATION')),
    maturity                       TIMESTAMP(3)           not null,
    v0                             FLOAT                  null    ,
    forward                        FLOAT                  null    ,
    forwardRef                     FLOAT                  null    ,
    ssr                            FLOAT                  null    ,
    vr                             FLOAT                  null    ,
    vcr                            FLOAT                  null    ,
    sr                             FLOAT                  null    ,
    scr                            FLOAT                  null    ,
    pc                             FLOAT                  null    ,
    cc                             FLOAT                  null    ,
    dc                             FLOAT                  null    ,
    uc                             FLOAT                  null    ,
    dsm                            FLOAT                  null    ,
    usm                            FLOAT                  null    ,
    useInterpolation               NUMBER(1)              null    ,
    useSpline                      NUMBER(1)              null    ,
    constraint PK_DSVP primary key (productId, context, priceType, maturity)
)
/

-- ============================================================
--   Table : StaticSkewVolatilityPoint                         
-- ============================================================
create table StaticSkewVolatilityPoint
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_SSVP check (priceType in ('TRADING','VALIDATION')),
    maturity                       TIMESTAMP(3)           not null,
    forward                        FLOAT                  null    ,
    volAtmF                        FLOAT                  null    ,
    skew                           FLOAT                  null    ,
    putKurtosis                    FLOAT                  null    ,
    callKurtosis                   FLOAT                  null    ,
    maxVol                         FLOAT                  null    ,
    minVol                         FLOAT                  null    ,
    putCurlUp                      FLOAT                  null    ,
    callCurlUp                     FLOAT                  null    ,
    putCutOff                      FLOAT                  null    ,
    callCutOff                     FLOAT                  null    ,
    constraint PK_SSVP primary key (productId, context, priceType, maturity)
)
/

-- ============================================================
--   Table : IMSMicParam                                       
-- ============================================================
create table IMSMicParam
(
    imsId                          VARCHAR2(32)           not null,
    virtualMic                     VARCHAR2(32)           not null,
    exchangeMic                    VARCHAR2(32)           null    ,
    constraint PK_IMSMICPARAM primary key (imsId, virtualMic)
)
/

-- ============================================================
--   Table : BrokerRFQParam                                    
-- ============================================================
create table BrokerRFQParam
(
    brokerId                       VARCHAR2(64)           not null,
    imsId                          VARCHAR2(32)           not null,
    responseType                   VARCHAR2(16)           null    ,
    maxLiveRequests                INTEGER                null    ,
    quoteSizeScale                 FLOAT                  null    ,
    quoteReplyValidityMs           INTEGER                null    ,
    tradeEntry                     NUMBER(1)              null    ,
    tradeEntrySystem               VARCHAR2(32)           null    ,
    accountCodification            VARCHAR2(32)           null    ,
    accountCode                    VARCHAR2(32)           null    ,
    acceptRFEInPricingBounds       NUMBER(1)              null    ,
    constraint PK_BROKERRFQPARAM primary key (brokerId, imsId)
)
/

-- ============================================================
--   Table : ProductRFQParam                                   
-- ============================================================
create table ProductRFQParam
(
    imsId                          VARCHAR2(32)           not null,
    productId                      VARCHAR2(128)          not null,
    responseType                   VARCHAR2(64)           null    ,
    constraint PK_PRODUCTRFQPARAM primary key (imsId, productId)
)
/

-- ============================================================
--   Table : ProductVersionInformation                         
-- ============================================================
create table ProductVersionInformation
(
    productId                      VARCHAR2(128)          not null,
    version                        VARCHAR2(32)           default '1' not null,
    labelKey                       VARCHAR2(255)          default 'VANILLA' not null,
    labelValue                     VARCHAR2(255)          null    ,
    generationTimestamp            TIMESTAMP(3)           null    ,
    importTimestamp                TIMESTAMP(3)           null    ,
    constraint PK_PRODUCTVERSIONINFORMATION primary key (productId, version, labelKey)
)
/

-- ============================================================
--   Table : BarrierVersion                                    
-- ============================================================
create table BarrierVersion
(
    productId                      VARCHAR2(128)          not null,
    rank                           INTEGER                not null,
    version                        VARCHAR2(32)           not null,
    ulId                           VARCHAR2(128)          null    ,
    barrierType                    VARCHAR2(16)           not null
        constraint CKC_BARRIERTYPE_BARRIERV check (barrierType in ('downAndIn','downAndOut','upAndOut','upAndIn')),
    barrierLevel                   FLOAT                  not null,
    isTouched                      NUMBER(1)              null    ,
    touchTimestamp                 TIMESTAMP(3)           null    ,
    payDateValue                   INTEGER                null    ,
    payDateUnit                    VARCHAR2(1)            null    
        constraint CKC_PAYDATEUNIT_BARRIERV check (payDateUnit is null or (payDateUnit in ('D','W','M','Y','d','w','m','y'))),
    observationStartDate           TIMESTAMP(3)           null    ,
    generationTimestamp            TIMESTAMP(3)           null    ,
    importTimestamp                TIMESTAMP(3)           null    ,
    constraint PK_BARRIERVERSION primary key (productId, rank, version)
)
/

-- ============================================================
--   Table : ForexExecutionHistoric                            
-- ============================================================
create table ForexExecutionHistoric
(
    tradeId                        VARCHAR2(128)          not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    portfolioId                    VARCHAR2(64)           null    ,
    brokerExchangeId               VARCHAR2(64)           null    ,
    userId                         VARCHAR2(64)           null    ,
    imsId                          VARCHAR2(32)           null    ,
    orderId                        VARCHAR2(128)          null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_FEXECH check (way is null or (way in ('2','B','S'))),
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    timestamp                      TIMESTAMP(3)           null    ,
    imsUserId                      VARCHAR2(32)           null    ,
    userData                       VARCHAR2(256)          null    ,
    marketData                     CLOB                   null    ,
    executionState                 VARCHAR2(1)            null    
        constraint CKC_EXECUTIONSTATE_FEXECH check (executionState is null or (executionState in ('n','c','u'))),
    productId                      VARCHAR2(128)          null    ,
    context                        VARCHAR2(32)           default 'DEFAULT' null    ,
    toHedge                        NUMBER(1)              default 0 null    ,
    sessionId                      VARCHAR2(64)           null    ,
    exchangeFees                   FLOAT                  null    ,
    clearingFees                   FLOAT                  null    ,
    brokerageFees                  FLOAT                  null    ,
    ignoreInPosition               NUMBER(1)              null    ,
    marketTradeId                  VARCHAR2(128)          null    ,
    tradeType                      VARCHAR2(64)           null    ,
    eventTimestamp                 TIMESTAMP(3)           null    ,
    version                        INTEGER                null    ,
    brokerName                     VARCHAR2(256)          null    ,
    boughtCurrency                 VARCHAR2(3)            null    ,
    boughtAmount                   FLOAT                  null    ,
    soldCurrency                   VARCHAR2(3)            null    ,
    soldAmount                     FLOAT                  null    ,
    mic                            VARCHAR2(32)           null    ,
    immediateHedgePercent          FLOAT                  null    ,
    immediateHedgeStrategy         VARCHAR2(16)           null    ,
    originalOrderPrice             FLOAT                  null    ,
    originalOrderQuantity          INTEGER                null    ,
    orderTimestamp                 TIMESTAMP(3)           null    ,
    execMarketData                 CLOB                   null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    settlementFees                 FLOAT                  null    ,
    comments                       VARCHAR2(256)          null    ,
    imsChannelType                 INTEGER                default 100 null    ,
    productToPositionForex         FLOAT                  null    ,
    pricingSnapStatus              VARCHAR2(64)           null    ,
    hedgeSnapStatus                VARCHAR2(64)           null    ,
    forexSnapStatus                VARCHAR2(64)           null    ,
    hedgeForexSnapStatus           VARCHAR2(64)           null    ,
    constraint PK_FEXECH primary key (tradeId, archiveTimestamp)
)
/

-- ============================================================
--   Table : AntiArbitrageDeviation                            
-- ============================================================
create table AntiArbitrageDeviation
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    deviationUnit                  VARCHAR2(16)           null    ,
    deviationValue                 FLOAT                  null    ,
    isIntradayMaxReached           NUMBER(1)              null    ,
    isShiftExceeded                NUMBER(1)              null    ,
    constraint PK_ANTIARBITRAGEDEVIATION primary key (productId, context)
)
/

-- ============================================================
--   Table : CopOptionalCriterionValues                        
-- ============================================================
create table CopOptionalCriterionValues
(
    name                           VARCHAR2(128)          not null,
    value                          VARCHAR2(128)          not null,
    constraint PK_COPOPTIONALCRITERIONVALUES primary key (name, value)
)
/

-- ============================================================
--   Table : AsianConvention                                   
-- ============================================================
create table AsianConvention
(
    mic                            VARCHAR2(32)           not null,
    nbFixings                      INTEGER                null    ,
    lastFixingLag                  INTEGER                null    ,
    fixingTime                     VARCHAR2(16)           null    ,
    constraint PK_ASIANCONVENTION primary key (mic)
)
/

-- ============================================================
--   Table : UserProperty                                      
-- ============================================================
create table UserProperty
(
    userId                         VARCHAR2(64)           not null,
    propertyId                     VARCHAR2(32)           not null,
    propertyValue                  VARCHAR2(512)          null    ,
    constraint PK_USERPROPERTY primary key (userId, propertyId)
)
/

-- ============================================================
--   Table : RFQBlotterHistoric                                
-- ============================================================
create table RFQBlotterHistoric
(
    quoteRequestId                 VARCHAR2(128)          not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    userId                         VARCHAR2(64)           not null,
    brokerId                       VARCHAR2(64)           null    ,
    productId                      VARCHAR2(128)          not null,
    exchangeMic                    VARCHAR2(32)           null    ,
    requestTimestamp               TIMESTAMP(3)           not null,
    replyTimestamp                 TIMESTAMP(3)           null    ,
    tradeTimestamp                 TIMESTAMP(3)           null    ,
    responseType                   VARCHAR2(16)           null    ,
    responseTypeSource             VARCHAR2(16)           null    ,
    status                         VARCHAR2(16)           null    ,
    quoteReplyId                   VARCHAR2(128)          null    ,
    buyQty                         INTEGER                null    ,
    buyPrice                       FLOAT                  null    ,
    sellQty                        INTEGER                null    ,
    sellPrice                      FLOAT                  null    ,
    errorMessage                   VARCHAR2(256)          null    ,
    constraint PK_RFQBLOTH primary key (quoteRequestId, archiveTimestamp)
)
/

-- ============================================================
--   Table : TradeBookingHistoric                              
-- ============================================================
create table TradeBookingHistoric
(
    tradeId                        VARCHAR2(128)          not null,
    tradeLegId                     VARCHAR2(64)           not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    execType                       VARCHAR2(1)            null    
        constraint CKC_EXECTYPE_TRABOOKH check (execType is null or (execType in ('C','D'))),
    tradeType                      VARCHAR2(32)           null    ,
    bookId                         VARCHAR2(128)          null    ,
    counterpartyId                 VARCHAR2(64)           null    ,
    status                         VARCHAR2(16)           null    ,
    bookingSystem                  VARCHAR2(32)           null    ,
    bookingError                   VARCHAR2(512)          null    ,
    bookingId                      VARCHAR2(128)          null    ,
    tradeTimestamp                 TIMESTAMP(3)           null    ,
    statusTimestamp                TIMESTAMP(3)           null    ,
    messageFormat                  VARCHAR2(64)           null    ,
    messageFormatVersion           VARCHAR2(16)           null    ,
    messageHeaderVersion           VARCHAR2(16)           null    ,
    applicationId                  VARCHAR2(32)           null    ,
    replyDownStream                VARCHAR2(512)          null    ,
    source                         VARCHAR2(512)          null    ,
    messageTimestamp               TIMESTAMP(3)           null    ,
    messageType                    VARCHAR2(32)           null    ,
    targetDownStream               VARCHAR2(512)          null    ,
    messageId                      VARCHAR2(256)          null    ,
    applicationSource              VARCHAR2(32)           null    ,
    eventType                      VARCHAR2(32)           null    ,
    subEventType                   VARCHAR2(32)           null    ,
    location                       VARCHAR2(32)           null    ,
    way                            VARCHAR2(1)            null    
        constraint CKC_WAY_TRABOOKH check (way is null or (way in ('2','B','S'))),
    tradeRevision                  INTEGER                null    ,
    inputBy                        VARCHAR2(64)           null    ,
    legalEntity                    VARCHAR2(32)           null    ,
    quantity                       INTEGER                null    ,
    price                          FLOAT                  null    ,
    principal                      FLOAT                  null    ,
    securityId                     VARCHAR2(32)           null    ,
    securityType                   VARCHAR2(32)           null    ,
    settlementCurrency             VARCHAR2(3)            null    ,
    settlementDate                 TIMESTAMP(3)           null    ,
    downStreamStrategy             VARCHAR2(32)           null    ,
    tradeCurrency                  VARCHAR2(3)            null    ,
    localTradeTimeStamp            TIMESTAMP(3)           null    ,
    traderId                       VARCHAR2(64)           null    ,
    tradingArea                    VARCHAR2(64)           null    ,
    dealCondition                  VARCHAR2(64)           null    ,
    accruedInterest                FLOAT                  null    ,
    constraint PK_TRABOOKH primary key (tradeId, tradeLegId, archiveTimestamp)
)
/

-- ============================================================
--   Table : SSMPortfolioMapping                               
-- ============================================================
create table SSMPortfolioMapping
(
    hmmPortfolioId                 VARCHAR2(64)           not null,
    ssmPortfolioId                 VARCHAR2(64)           not null,
    constraint PK_SSMPORTFOLIOMAPPING primary key (hmmPortfolioId)
)
/

-- ============================================================
--   Table : CombinationLegShift                               
-- ============================================================
create table CombinationLegShift
(
    productId                      VARCHAR2(128)          not null,
    legProductId                   VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           not null,
    version                        VARCHAR2(32)           not null,
    shiftUnit                      VARCHAR2(16)           null    ,
    shiftValue                     FLOAT                  null    ,
    constraint PK_COMBINATIONLEGSHIFT primary key (productId, legProductId, context, version)
)
/

-- ============================================================
--   Table : UserFixedVolStep                                  
-- ============================================================
create table UserFixedVolStep
(
    userId                         VARCHAR2(64)           not null,
    productId                      VARCHAR2(128)          not null,
    fixedVolStep                   FLOAT                  null    ,
    constraint PK_USERFIXEDVOLSTEP primary key (userId, productId)
)
/

-- ============================================================
--   Table : MarketStreamSnapHistoric                          
-- ============================================================
create table MarketStreamSnapHistoric
(
    snapId                         VARCHAR2(32)           not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    timestamp                      TIMESTAMP(3)           null    ,
    productId                      VARCHAR2(128)          null    ,
    tradeId                        VARCHAR2(128)          null    ,
    fieldId                        VARCHAR2(128)          null    ,
    value                          FLOAT                  null    ,
    constraint PK_MARKETSTREAMSNAPHISTORIC primary key (snapId, archiveTimestamp)
)
/

-- ============================================================
--   Table : AutofitDynamicSkewPoint                           
-- ============================================================
create table AutofitDynamicSkewPoint
(
    productId                      VARCHAR2(128)          not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    priceType                      VARCHAR2(16)           default 'TRADING' not null
        constraint CKC_PRICETYPE_ADSP check (priceType in ('TRADING','VALIDATION')),
    maturity                       TIMESTAMP(3)           not null,
    forward                        FLOAT                  null    ,
    volAtmF                        FLOAT                  null    ,
    slope                          FLOAT                  null    ,
    putKurtosis                    FLOAT                  null    ,
    callKurtosis                   FLOAT                  null    ,
    maxVol                         FLOAT                  null    ,
    minVol                         FLOAT                  null    ,
    putCurlUp                      FLOAT                  null    ,
    callCurlUp                     FLOAT                  null    ,
    putCutOff                      FLOAT                  null    ,
    callCutOff                     FLOAT                  null    ,
    forwardStickiness              FLOAT                  null    ,
    verticalFactor                 FLOAT                  null    ,
    slopeFactor                    FLOAT                  null    ,
    spotRef                        FLOAT                  null    ,
    constraint PK_ADSP primary key (productId, context, priceType, maturity)
)
/

-- ============================================================
--   Table : ProductMapping                                    
-- ============================================================
create table ProductMapping
(
    productId                      VARCHAR2(128)          not null,
    mappedProductId                VARCHAR2(128)          not null,
    ratio                          FLOAT                  null    ,
    isDefaultMapping               NUMBER(1)              null    ,
    constraint PK_PRODUCTMAPPING primary key (productId, mappedProductId)
)
/

-- ============================================================
--   Table : YesterdayPositionHistoric                         
-- ============================================================
create table YesterdayPositionHistoric
(
    productId                      VARCHAR2(128)          not null,
    portfolioId                    VARCHAR2(64)           not null,
    context                        VARCHAR2(32)           default 'DEFAULT' not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    way                            VARCHAR2(1)            not null
        constraint CKC_WAY_YESTPH check (way in ('2','B','S')),
    quantity                       INTEGER                not null,
    price                          FLOAT                  not null,
    delta                          FLOAT                  null    ,
    gamma                          FLOAT                  null    ,
    vega                           FLOAT                  null    ,
    theta                          FLOAT                  null    ,
    rho                            FLOAT                  null    ,
    mu                             FLOAT                  null    ,
    importTimestamp                TIMESTAMP(3)           null    ,
    generationTimestamp            TIMESTAMP(3)           null    ,
    currencyId                     VARCHAR2(3)            null    ,
    toHedge                        NUMBER(1)              null    ,
    spot                           FLOAT                  null    ,
    hedgeUlId                      VARCHAR2(128)          null    ,
    productToPositionForex         FLOAT                  null    ,
    constraint PK_YESTPH primary key (productId, portfolioId, context, archiveTimestamp)
)
/

-- ============================================================
--   Table : SSMLocate                                         
-- ============================================================
create table SSMLocate
(
    productId                      VARCHAR2(128)          not null,
    ssmPortfolioId                 VARCHAR2(64)           not null,
    locateId                       VARCHAR2(128)          not null,
    locateQty                      INTEGER                not null,
    constraint PK_SSMLOCATE primary key (productId, ssmPortfolioId)
)
/

-- ============================================================
--   Table : MarketOnCloseRuleSource                           
-- ============================================================
create table MarketOnCloseRuleSource
(
    mic                            VARCHAR2(32)           not null,
    sendErrorOffsetInMin           INTEGER                not null,
    cancelWarnOffsetInMin          INTEGER                not null,
    cancelErrorOffsetInMin         INTEGER                not null,
    constraint PK_MARKETONCLOSERULESOURCE primary key (mic)
)
/

-- ============================================================
--   Table : ExecutionInformationHistoric                      
-- ============================================================
create table ExecutionInformationHistoric
(
    tradeId                        VARCHAR2(128)          not null,
    propertyId                     VARCHAR2(256)          not null,
    archiveTimestamp               TIMESTAMP(3)           not null,
    propertyValue                  VARCHAR2(512)          null    ,
    constraint PK_EXECINFOHISTO primary key (tradeId, propertyId, archiveTimestamp)
)
/

-- ============================================================
--   Table : CopGroupUser                                      
-- ============================================================
create table CopGroupUser
(
    userId                         VARCHAR2(64)           not null,
    groupId                        VARCHAR2(64)           not null,
    constraint PK_COPGROUPUSER primary key (userId, groupId)
)
/
