MACHINE Costumer

SEES Price, Goods

CONSTANTS limit

PROPERTIES limit : GOODS --> NAT1

VARIABLES purchases

INVARIANT purchases <: GOODS

INITIALISATION purchases := {}

OPERATIONS
  pp <-- buy(gg) =
    PRE 
      gg : GOODS & 
      price(gg) <= limit(gg) & 
      pp : NAT1
    THEN 
      purchases := purchases \/ {gg}
    END
END