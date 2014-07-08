MACHINE Costumer

SEES Price, Goods

CONSTANTS limit

PROPERTIES limit : GOODS --> NAT1

VARIABLES purchases

INVARIANT purchases <: GOODS

INITIALISATION purchases := {}

OPERATIONS
  buy(gg) =
    PRE 
      gg : GOODS & 
      price(gg) <= limit(gg) 
    THEN 
      purchases := purchases \/ {gg}
    END
END