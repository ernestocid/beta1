MACHINE SimpleCostumer

SEES SimplePrice, SimpleGoods

CONSTANTS limit

PROPERTIES limit : GOODS --> NAT1

VARIABLES purchases

INVARIANT purchases <: GOODS

INITIALISATION purchases := {}

OPERATIONS
  buy(gg) =
    PRE  
      price(gg) <= limit(gg) 
    THEN 
      purchases := purchases \/ {gg}
    END
END