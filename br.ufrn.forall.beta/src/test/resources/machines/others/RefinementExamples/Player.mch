MACHINE
    Player

VARIABLES
    team

INVARIANT
    team <: 1..22 & card(team) = 11

INITIALISATION
    ANY 
        tt 
    WHERE
        tt <: 1..22 & card(tt) = 11
    THEN
        team := tt
    END
OPERATIONS
    substitute(pp, rr) = 
    PRE 
        pp : 1..22 & pp : team & rr : 1..22 & rr /: team
    THEN
        team := (team \/ {rr}) - {pp}
    END;
    aa <-- in_team(pp) = 
    PRE
        pp : 1..22
    THEN
        IF pp : team THEN
            aa := TRUE
        ELSE
            aa := FALSE
        END
    END
END

