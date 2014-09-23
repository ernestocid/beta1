MACHINE TestsForOp_output_From_Fifo

CONSTANTS cap

PROPERTIES
cap : NAT1

OPERATIONS
/* Equivalence Class test data for output */
output_test1(
contents
) =
PRE
not(size(contents) > 0) & size(contents) <= cap & contents : seq(INT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for output */
output_test2(
contents
) =
PRE
size(contents) > 0 & size(contents) <= cap & contents : seq(INT) /* POSITIVE */
THEN
skip
END
END