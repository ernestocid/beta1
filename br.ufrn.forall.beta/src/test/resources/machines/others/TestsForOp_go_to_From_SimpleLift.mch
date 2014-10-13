MACHINE TestsForOp_go_to_From_SimpleLift

OPERATIONS
/* Equivalence Class test data for go_to */
go_to_test1(
i__nFloor,
i__floor
) =
PRE
i__nFloor : 1..5 & i__floor : 1..5 & not(i__nFloor /= i__floor) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for go_to */
go_to_test2(
i__nFloor,
i__floor
) =
PRE
i__nFloor : 5 + 1..MAXINT & i__floor : 1..5 & not(i__nFloor /= i__floor) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for go_to */
go_to_test3(
i__nFloor,
i__floor
) =
PRE
i__nFloor : MININT..1 - 1 & i__floor : 1..5 & i__nFloor /= i__floor /* NEGATIVE */
THEN
skip
END
END