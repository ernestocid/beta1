MACHINE TestDataRefinement

SETS
PLAYER

VALUES
PLAYER = 1..22

PROPERTIES
card(PLAYER) > 11

OPERATIONS
  test1(team__, pp__, rr__) = 
  PRE 
    team__ <: PLAYER & 
    card(team__) = 11 & 
    pp__ : PLAYER & 
    pp__ : team__ & 
    rr__ /: team__ & 
    rr__ : PLAYER
  THEN skip
  END
END

