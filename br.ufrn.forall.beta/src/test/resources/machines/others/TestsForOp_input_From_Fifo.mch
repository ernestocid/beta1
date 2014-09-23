MACHINE TestsForOp_input_From_Fifo

CONSTANTS cap

PROPERTIES
cap : NAT1

OPERATIONS
/* Equivalence Class test data for input */
input_test1(
ee,
contents
) =
PRE
size(contents) <= cap & not(size(contents) < cap) & contents : seq(INT) & ee : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for input */
input_test2(
ee,
contents
) =
PRE
size(contents) <= cap & size(contents) < cap & contents : seq(INT) & ee : INT /* POSITIVE */
THEN
skip
END
END