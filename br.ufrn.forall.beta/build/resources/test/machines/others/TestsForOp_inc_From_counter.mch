MACHINE TestsForOp_inc_From_counter

OPERATIONS
/* Equivalence Class test data for inc */
inc_test1(
overflow,
value
) =
PRE
0 <= value & not(value <= MAXINT) & not(value < MAXINT) & overflow : BOOL & value : INT & ((overflow = TRUE) => (value = MAXINT)) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test2(
overflow,
value
) =
PRE
0 <= value & not(value <= MAXINT) & value < MAXINT & overflow : BOOL & value : INT & ((overflow = TRUE) => (value = MAXINT)) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test3(
overflow,
value
) =
PRE
0 <= value & value <= MAXINT & not(value < MAXINT) & overflow : BOOL & value : INT & ((overflow = TRUE) => (value = MAXINT)) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test4(
overflow,
value
) =
PRE
0 <= value & value <= MAXINT & value < MAXINT & overflow : BOOL & value : INT & ((overflow = TRUE) => (value = MAXINT)) /* POSITIVE */
THEN
skip
END
END