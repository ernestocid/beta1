MACHINE TestsForOp_go_up_From_SimpleLift

OPERATIONS
/* Equivalence Class test data for go_up */
go_up_test1(
i__floor
) =
PRE
i__floor < 5 & i__floor : 1..5 /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for go_up */
go_up_test2(
i__floor
) =
PRE
not(i__floor < 5) & i__floor : 1..5 /* NEGATIVE */
THEN
skip
END
END