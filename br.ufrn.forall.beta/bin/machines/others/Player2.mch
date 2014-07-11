MACHINE Player2

SETS ANSWER = {in, out}

CONSTANTS players

PROPERTIES 
     players <: NATURAL & 
     card(players) > 11

VARIABLES team

INVARIANT
     team <: players & 
     card(team) = 11

INITIALISATION
     ANY tt WHERE tt <: players & card(tt) = 11 THEN team := tt END
     

OPERATIONS
     substitute(pp,rr) =
         PRE pp : team & rr : players & rr /: team
         THEN team := (team \/ {rr}) - {pp}
         END;

     aa <-- query(pp) =
         PRE pp : players
         THEN IF pp : team 
              THEN aa := in
              ELSE aa := out
         END
END
END

