MACHINE TestsForOp_go_to_From_SimpleLift

OPERATIONS
/* Equivalence Class test data for go_to */
go_to_test1(
floor,
nFloor
) =
PRE
nFloor : 1..5 & floor : 1..5 & not(nFloor /= floor) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for go_to */
go_to_test2(
floor,
nFloor
) =
PRE
nFloor : 5 + 1..MAXINT & floor : 1..5 & not(nFloor /= floor) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for go_to */
go_to_test3(
floor,
nFloor
) =
PRE
nFloor : MININT..1 - 1 & floor : 1..5 & nFloor /= floor /* NEGATIVE */
THEN
skip
END
END