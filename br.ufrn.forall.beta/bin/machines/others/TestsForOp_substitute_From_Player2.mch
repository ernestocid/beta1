MACHINE TestsForOp_substitute_From_Player2

SETS
ANSWER = {in, out}

CONSTANTS players

PROPERTIES
players <: NATURAL &
card(players) > 11

OPERATIONS
/* Equivalence Class test data for substitute */
substitute_test1(
rr,
team,
pp
) =
PRE
not(pp : team) & not(rr /: team) & not(rr : players) & team <: players & card(team) = 11 /* NEGATIVE */
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
pp : team & rr /: team & rr : players & team <: players & card(team) = 11 /* POSITIVE */
THEN
skip
END
END