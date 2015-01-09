MACHINE TestsForOp_checkResult_From_PassFinalOrFailIFELSIFELSE

SETS
RESULT = {pass, final, fail}

OPERATIONS
/* Equivalence Class test data for checkResult */
checkResult_test1(
i__averageGrade
) =
PRE
i__averageGrade : 0..5 & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for checkResult */
checkResult_test2(
i__averageGrade
) =
PRE
i__averageGrade : 0..5 & i__averageGrade < 4 & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for checkResult */
checkResult_test3(
i__averageGrade
) =
PRE
i__averageGrade : 0..5 & i__averageGrade >= 2 & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for checkResult */
checkResult_test4(
i__averageGrade
) =
PRE
i__averageGrade : 0..5 & i__averageGrade >= 4 & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for checkResult */
checkResult_test5(
i__averageGrade
) =
PRE
i__averageGrade : 0..5 & not(i__averageGrade < 4) & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for checkResult */
checkResult_test6(
i__averageGrade
) =
PRE
i__averageGrade : 0..5 & not(i__averageGrade >= 2) & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for checkResult */
checkResult_test7(
i__averageGrade
) =
PRE
i__averageGrade : 0..5 & not(i__averageGrade >= 4) & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for checkResult */
checkResult_test8(
i__averageGrade
) =
PRE
not(i__averageGrade : 0..5) & i__averageGrade : INT /* POSITIVE */
THEN
skip
END
END