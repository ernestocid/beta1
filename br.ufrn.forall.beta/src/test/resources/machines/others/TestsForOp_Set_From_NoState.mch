MACHINE TestsForOp_Set_From_NoState

OPERATIONS
/* Equivalence Class test data for Set */
Set_test1(
i__xx,
i__yy
) =
PRE
not(i__yy : NAT) & not(i__yy > i__xx) & not(i__xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test2(
i__xx,
i__yy
) =
PRE
not(i__yy : NAT) & not(i__yy > i__xx) & i__xx : NAT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test3(
i__xx,
i__yy
) =
PRE
not(i__yy : NAT) & i__yy > i__xx & not(i__xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test4(
i__xx,
i__yy
) =
PRE
not(i__yy : NAT) & i__yy > i__xx & i__xx : NAT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test5(
i__xx,
i__yy
) =
PRE
i__yy : NAT & not(i__yy > i__xx) & not(i__xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test6(
i__xx,
i__yy
) =
PRE
i__yy : NAT & not(i__yy > i__xx) & i__xx : NAT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test7(
i__xx,
i__yy
) =
PRE
i__yy : NAT & i__yy > i__xx & not(i__xx : NAT) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for Set */
Set_test8(
i__xx,
i__yy
) =
PRE
i__yy : NAT & i__yy > i__xx & i__xx : NAT /* POSITIVE */
THEN
skip
END
END