MACHINE TestsForOp_buy_From_Costumer

SEES Price, Goods

CONSTANTS limit

PROPERTIES
limit : (GOODS --> NAT1)

OPERATIONS
/* Equivalence Class test data for buy */
buy_test1(
i__purchases,
i__price,
i__gg
) =
PRE
not(i__price(i__gg) <= limit(i__gg)) & i__gg : GOODS & i__purchases <: GOODS & i__price : (GOODS --> NAT1) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for buy */
buy_test2(
i__purchases,
i__price,
i__gg
) =
PRE
i__price(i__gg) <= limit(i__gg) & i__gg : GOODS & i__purchases <: GOODS & i__price : (GOODS --> NAT1) /* POSITIVE */
THEN
skip
END
END