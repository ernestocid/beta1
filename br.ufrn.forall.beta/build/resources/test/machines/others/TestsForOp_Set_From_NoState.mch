MACHINE TestsForOp_Set_From_NoState

OPERATIONS
/* Equivalence Class test data for Set */
Set_test1(
yy,
xx
) =
PRE
not(yy : NAT) & not(yy > xx) & not(xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test2(
yy,
xx
) =
PRE
not(yy : NAT) & not(yy > xx) & xx : NAT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test3(
yy,
xx
) =
PRE
not(yy : NAT) & yy > xx & not(xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test4(
yy,
xx
) =
PRE
not(yy : NAT) & yy > xx & xx : NAT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test5(
yy,
xx
) =
PRE
yy : NAT & not(yy > xx) & not(xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test6(
yy,
xx
) =
PRE
yy : NAT & not(yy > xx) & xx : NAT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test7(
yy,
xx
) =
PRE
yy : NAT & yy > xx & not(xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test8(
yy,
xx
) =
PRE
yy : NAT & yy > xx & xx : NAT /* POSITIVE */
THEN
skip
END
END