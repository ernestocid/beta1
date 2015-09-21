MACHINE           PlayerWithEnumeratedSets

SETS              ANSWER = {in, out}; players = {p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21}

VARIABLES         team

INVARIANT         team <: players & card(team) <= 11

INITIALISATION    team := {}

OPERATIONS

  added <-- add_player(pp) =
  PRE pp : players & pp /: team & added : BOOL
  THEN IF card(team) < 11 THEN team := team \/ {pp} || added := TRUE
       ELSE added := FALSE
       END
  END;

  p_out <-- substitute(pp,rr) =
  PRE pp : team & rr : players & rr /: team & p_out : players
  THEN team := (team \/ {rr}) - {pp} || p_out := pp
  END;

  aa <-- query(pp) =
  PRE pp : players
  THEN IF pp : team 
       THEN aa := in
       ELSE aa := out
       END
  END

END
