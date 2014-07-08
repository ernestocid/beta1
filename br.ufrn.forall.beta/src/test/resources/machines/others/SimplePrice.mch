MACHINE SimplePrice

SEES SimpleGoods

VARIABLES price

INVARIANT price : GOODS --> NAT1

INITIALISATION price :: GOODS --> NAT1

OPERATIONS

  pp <-- pricequery(gg) = 
    PRE gg : GOODS
    THEN pp := price(gg)
  END

END

