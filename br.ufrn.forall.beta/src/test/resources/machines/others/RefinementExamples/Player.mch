MACHINE
    Player
SETS
    PLAYER
PROPERTIES
    card(PLAYER) > 11
VARIABLES
    team
INVARIANT
    team <: PLAYER & card(team) = 11
INITIALISATION
    ANY 
        tt 
    WHERE
        tt <: PLAYER & card(tt) = 11
    THEN
        team := tt
    END
OPERATIONS
    substitute(pp, rr) = 
    PRE 
        pp : PLAYER & pp : team & rr : PLAYER & rr /: team
    THEN
        team := (team \/ {rr}) - {pp}
    END;
    aa <-- in_team(pp) = 
    PRE
        pp : PLAYER
    THEN
        IF pp : team THEN
            aa := TRUE
        ELSE
            aa := FALSE
        END
    END
END

