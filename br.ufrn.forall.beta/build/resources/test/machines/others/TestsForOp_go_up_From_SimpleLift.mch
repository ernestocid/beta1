MACHINE TestsForOp_go_up_From_SimpleLift

OPERATIONS
/* Equivalence Class test data for go_up */
go_up_test1(
floor
) =
PRE
floor < 5 & floor : 1..5 /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for go_up */
go_up_test2(
floor
) =
PRE
not(floor < 5) & floor : 1..5 /* NEGATIVE */
THEN
skip
END
END