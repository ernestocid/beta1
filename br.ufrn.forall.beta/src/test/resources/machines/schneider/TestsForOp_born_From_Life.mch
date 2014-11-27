MACHINE TestsForOp_born_From_Life

SETS
SEX = {boy, girl};
PERSON

OPERATIONS
/* Equivalence Class test data for born */
born_test1(
i__ss,
i__female,
i__male,
i__nn
) =
PRE
i__ss : SEX & i__nn : PERSON & i__female <: PERSON & i__male <: PERSON & i__male /\ i__female = {} & i__nn /: i__male \/ i__female & not(i__ss = boy) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for born */
born_test2(
i__ss,
i__female,
i__male,
i__nn
) =
PRE
i__ss : SEX & i__nn : PERSON & i__female <: PERSON & i__male <: PERSON & i__male /\ i__female = {} & i__nn /: i__male \/ i__female & i__ss = boy /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for born */
born_test3(
i__ss,
i__female,
i__male,
i__nn
) =
PRE
i__ss : SEX & i__nn : PERSON & i__female <: PERSON & i__male <: PERSON & i__male /\ i__female = {} & not(i__nn /: i__male \/ i__female) & not(i__ss = boy) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for born */
born_test4(
i__ss,
i__female,
i__male,
i__nn
) =
PRE
i__ss : SEX & i__nn : PERSON & i__female <: PERSON & i__male <: PERSON & i__male /\ i__female = {} & not(i__nn /: i__male \/ i__female) & i__ss = boy /* NEGATIVE */
THEN
skip
END
END