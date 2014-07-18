MACHINE TestsForOp_inc_From_Inc

OPERATIONS
/* Equivalence Class test data for inc */
inc_test1(
nn,
value
) =
PRE
nn : NAT & (nn + value) <= MAXINT & not(value < MAXINT) & value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test2(
nn,
value
) =
PRE
nn : NAT & (nn + value) <= MAXINT & value < MAXINT & value : INT /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test3(
nn,
value
) =
PRE
nn : NAT & not((nn + value) <= MAXINT) & not(value < MAXINT) & value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test4(
nn,
value
) =
PRE
nn : NAT & not((nn + value) <= MAXINT) & value < MAXINT & value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test5(
nn,
value
) =
PRE
not(nn : NAT) & (nn + value) <= MAXINT & not(value < MAXINT) & value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test6(
nn,
value
) =
PRE
not(nn : NAT) & (nn + value) <= MAXINT & value < MAXINT & value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test7(
nn,
value
) =
PRE
not(nn : NAT) & not((nn + value) <= MAXINT) & not(value < MAXINT) & value : INT /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for inc */
inc_test8(
nn,
value
) =
PRE
not(nn : NAT) & not((nn + value) <= MAXINT) & value < MAXINT & value : INT /* NEGATIVE */
THEN
skip
END
END