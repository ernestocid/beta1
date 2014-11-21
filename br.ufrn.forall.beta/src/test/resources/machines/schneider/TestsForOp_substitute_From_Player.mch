MACHINE TestsForOp_substitute_From_Player

SETS
ANSWER = {in, out};
PLAYER

PROPERTIES
card(PLAYER) = 25

OPERATIONS
/* Equivalence Class test data for substitute */
substitute_test1(
i__rr,
i__pp,
i__team
) =
PRE
not(i__pp : i__team) & i__team <: PLAYER & not(i__rr /: i__team) & i__rr : PLAYER & card(i__team) = 11 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for substitute */
substitute_test2(
i__rr,
i__pp,
i__team
) =
PRE
not(i__pp : i__team) & i__team <: PLAYER & i__rr /: i__team & i__rr : PLAYER & card(i__team) = 11 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for substitute */
substitute_test3(
i__rr,
i__pp,
i__team
) =
PRE
i__pp : i__team & i__team <: PLAYER & not(i__rr /: i__team) & i__rr : PLAYER & card(i__team) = 11 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for substitute */
substitute_test4(
i__rr,
i__pp,
i__team
) =
PRE
i__pp : i__team & i__team <: PLAYER & i__rr /: i__team & i__rr : PLAYER & card(i__team) = 11 /* POSITIVE */
THEN
skip
END
END