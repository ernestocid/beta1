MACHINE Baskets

SETS GOODS; CUSTOMER

CONSTANTS price

PROPERTIES price : GOODS --> NAT1

VARIABLES baskets

INVARIANT baskets : CUSTOMER +-> POW(GOODS)

INITIALISATION baskets := {}

OPERATIONS

  enter(cu) =
  PRE cu : CUSTOMER & cu /: dom(baskets)
  THEN baskets(cu) := {}
  END;

  add(cu, gg) =
  PRE cu : dom(baskets) & gg : GOODS
  THEN baskets(cu) := baskets(cu) \/ {gg}
  END;

nn <-- checkout(cu) =
  PRE cu : dom(baskets)
  THEN nn := SIGMA (gg) . (gg : baskets(cu) | price(gg))
       || baskets := {cu} <<| baskets
  END

END
