MACHINE TestsForOp_GameResult_From_TicTacToe

SETS
Player = {blue, red};
Result = {blue_win, red_win, draw}

CONSTANTS WinnerRows, ThreeInRow

PROPERTIES
ThreeInRow : (POW(0..8) --> BOOL) &
WinnerRows <: POW(0..8) &
ThreeInRow = %(ps).(ps : POW(0..8) & #(wn).(wn : WinnerRows & wn <: ps) | TRUE) \/ %(ps).(ps : POW(0..8) & not(#(wn).(wn : WinnerRows & wn <: ps)) | FALSE) &
WinnerRows = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {6, 4, 2}}

OPERATIONS
/* Equivalence Class test data for GameResult */
GameResult_test1(
turn,
bposn,
rposn
) =
PRE
bposn \/ rposn = 0..8 & ThreeInRow(bposn) = TRUE & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & ThreeInRow(rposn) = TRUE & bposn <: 0..8 /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for GameResult */
GameResult_test2(
turn,
bposn,
rposn
) =
PRE
bposn \/ rposn = 0..8 & ThreeInRow(bposn) = TRUE & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & not(ThreeInRow(rposn) = TRUE) & bposn <: 0..8 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for GameResult */
GameResult_test3(
turn,
bposn,
rposn
) =
PRE
bposn \/ rposn = 0..8 & not(ThreeInRow(bposn) = TRUE) & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & ThreeInRow(rposn) = TRUE & bposn <: 0..8 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for GameResult */
GameResult_test4(
turn,
bposn,
rposn
) =
PRE
bposn \/ rposn = 0..8 & not(ThreeInRow(bposn) = TRUE) & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & not(ThreeInRow(rposn) = TRUE) & bposn <: 0..8 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for GameResult */
GameResult_test5(
turn,
bposn,
rposn
) =
PRE
not(bposn \/ rposn = 0..8) & ThreeInRow(bposn) = TRUE & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & ThreeInRow(rposn) = TRUE & bposn <: 0..8 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for GameResult */
GameResult_test6(
turn,
bposn,
rposn
) =
PRE
not(bposn \/ rposn = 0..8) & ThreeInRow(bposn) = TRUE & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & not(ThreeInRow(rposn) = TRUE) & bposn <: 0..8 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for GameResult */
GameResult_test7(
turn,
bposn,
rposn
) =
PRE
not(bposn \/ rposn = 0..8) & not(ThreeInRow(bposn) = TRUE) & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & ThreeInRow(rposn) = TRUE & bposn <: 0..8 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for GameResult */
GameResult_test8(
turn,
bposn,
rposn
) =
PRE
not(bposn \/ rposn = 0..8) & not(ThreeInRow(bposn) = TRUE) & bposn /\ rposn = {} & rposn <: 0..8 & turn : Player & not(ThreeInRow(rposn) = TRUE) & bposn <: 0..8 /* NEGATIVE */
THEN
skip
END
END