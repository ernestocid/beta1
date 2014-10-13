MACHINE TestsForOp_inc_From_counter

OPERATIONS
/* Equivalence Class test data for inc */
inc_test1(
i__value,
i__overflow
) =
PRE
0 <= i__value & not(i__value <= MAXINT) & not(i__value < MAXINT) & i__overflow : BOOL & i__value : INT & ((i__overflow = TRUE) => (i__value = MAXINT)) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test2(
i__value,
i__overflow
) =
PRE
0 <= i__value & not(i__value <= MAXINT) & i__value < MAXINT & i__overflow : BOOL & i__value : INT & ((i__overflow = TRUE) => (i__value = MAXINT)) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test3(
i__value,
i__overflow
) =
PRE
0 <= i__value & i__value <= MAXINT & not(i__value < MAXINT) & i__overflow : BOOL & i__value : INT & ((i__overflow = TRUE) => (i__value = MAXINT)) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test4(
i__value,
i__overflow
) =
PRE
0 <= i__value & i__value <= MAXINT & i__value < MAXINT & i__overflow : BOOL & i__value : INT & ((i__overflow = TRUE) => (i__value = MAXINT)) /* POSITIVE */
THEN
skip
END
END