MACHINE TestsForOp_substitute_From_Player

SETS
ANSWER = {in, out};
PLAYER

PROPERTIES
card(PLAYER) > 11

OPERATIONS
/* Equivalence Class test data for substitute */
substitute_test1(
rr,
team,
pp
) =
PRE
pp : team & team <: PLAYER & rr : PLAYER & rr /: team & card(team) = 11
THEN
skip
END
;
/* Equivalence Class test data for substitute */
substitute_test2(
rr,
team,
pp
) =
PRE
not(pp : team) & team <: PLAYER & rr : PLAYER & rr /: team & card(team) = 11
THEN
skip
END
;
/* Equivalence Class test data for substitute */
substitute_test3(
rr,
team,
pp
) =
PRE
not(pp : team) & team <: PLAYER & rr : PLAYER & not(rr /: team) & card(team) = 11
THEN
skip
END
;
/* Equivalence Class test data for substitute */
substitute_test4(
rr,
team,
pp
) =
PRE
pp : team & team <: PLAYER & rr : PLAYER & not(rr /: team) & card(team) = 11
THEN
skip
END
END