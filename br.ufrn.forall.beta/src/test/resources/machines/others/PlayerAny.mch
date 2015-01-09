MACHINE PlayerAny

SETS PLAYER

PROPERTIES card(PLAYER) > 11

VARIABLES team

INVARIANT
 team <: PLAYER

INITIALISATION team := {}

OPERATIONS

  tt <-- subteam(ss) = 
    PRE ss : INT
    THEN
      ANY xx
      WHERE xx <: PLAYER & card(xx) = ss
      THEN tt := xx
      END
    END  

END

