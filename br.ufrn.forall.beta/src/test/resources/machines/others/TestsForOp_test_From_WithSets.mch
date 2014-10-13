MACHINE TestsForOp_test_From_WithSets

OPERATIONS
/* Equivalence Class test data for test */
test_test1(
i__temp
) =
PRE
not(i__temp = {1, 2, 3}) & i__temp <: NAT1 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for test */
test_test2(
i__temp
) =
PRE
i__temp = {1, 2, 3} & i__temp <: NAT1 /* POSITIVE */
THEN
skip
END
END