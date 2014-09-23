/* TicTacToe
 * Author: 
 * Creation date: 30/07/14
 */
MACHINE
    TicTacToe

SETS
    Player = {blue, red};
    Result = {blue_win, red_win, draw}

ABSTRACT_CONSTANTS
    WinnerRows,
    ThreeInRow

PROPERTIES
    WinnerRows <: POW(0..8) &
    WinnerRows = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {6,4,2}} &
    ThreeInRow : POW(0..8) --> BOOL &
    ThreeInRow = %ps.(ps : POW(0..8) & #wn.(wn : WinnerRows & wn <: ps)|TRUE) \/
                    %ps.(ps : POW(0..8) & not(#wn.(wn : WinnerRows & wn <: ps))|FALSE)

VARIABLES
    bposn, rposn, turn

INVARIANT
    bposn <: 0..8 &
    rposn <: 0..8 &
    bposn /\ rposn = {} &
    turn : Player

INITIALISATION
    bposn := {} ||
    rposn := {} ||
    turn := blue
    
OPERATIONS
    BlueMove(pp) = 
    PRE
        pp : 0..8 &
        pp /: (bposn \/ rposn) &
        turn = blue &
        ThreeInRow(rposn) = FALSE
    THEN
        bposn := bposn \/ {pp} ||
        turn := red
    END;
    RedMove(pp) = 
    PRE
        pp : 0..8 &
        pp /: (bposn \/ rposn) &
        turn = red &
        ThreeInRow(bposn) = FALSE
    THEN
        rposn := rposn \/ {pp} ||
        turn := blue
    END;
    result <-- GameResult =
    PRE
        ThreeInRow(bposn) = TRUE or
        ThreeInRow(rposn) = TRUE or
        (bposn \/ rposn) = 0..8
    THEN
        IF ThreeInRow(bposn) = TRUE THEN
            result := blue_win
        ELSIF ThreeInRow(rposn) = TRUE THEN
            result := red_win
        ELSE
            result := draw
        END
    END
END
