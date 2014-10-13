MACHINE TestsForOp_inc_From_Inc

OPERATIONS
/* Equivalence Class test data for inc */
inc_test1(
i__value,
i__nn
) =
PRE
i__nn : NAT & (i__nn + i__value) <= MAXINT & not(i__value < MAXINT) & i__value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test2(
i__value,
i__nn
) =
PRE
i__nn : NAT & (i__nn + i__value) <= MAXINT & i__value < MAXINT & i__value : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test3(
i__value,
i__nn
) =
PRE
i__nn : NAT & not((i__nn + i__value) <= MAXINT) & not(i__value < MAXINT) & i__value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test4(
i__value,
i__nn
) =
PRE
i__nn : NAT & not((i__nn + i__value) <= MAXINT) & i__value < MAXINT & i__value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test5(
i__value,
i__nn
) =
PRE
not(i__nn : NAT) & (i__nn + i__value) <= MAXINT & not(i__value < MAXINT) & i__value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test6(
i__value,
i__nn
) =
PRE
not(i__nn : NAT) & (i__nn + i__value) <= MAXINT & i__value < MAXINT & i__value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test7(
i__value,
i__nn
) =
PRE
not(i__nn : NAT) & not((i__nn + i__value) <= MAXINT) & not(i__value < MAXINT) & i__value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test8(
i__value,
i__nn
) =
PRE
not(i__nn : NAT) & not((i__nn + i__value) <= MAXINT) & i__value < MAXINT & i__value : INT /* NEGATIVE */
THEN
skip
END
END