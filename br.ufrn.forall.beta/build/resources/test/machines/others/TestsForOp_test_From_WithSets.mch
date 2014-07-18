MACHINE TestsForOp_test_From_WithSets

OPERATIONS
/* Equivalence Class test data for test */
test_test1(
temp
) =
PRE
not(temp = {1, 2, 3}) & temp <: NAT1 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for test */
test_test2(
temp
) =
PRE
temp = {1, 2, 3} & temp <: NAT1 /* POSITIVE */
THEN
skip
END
END