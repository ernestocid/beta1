MACHINE Life
SETS
  PERSON = {p1,p2,p3,p4,p5,p6,p7,p8};
  SEX = {boy, girl}

VARIABLES male, female

INVARIANT
  male <: PERSON &
  female <: PERSON &
  male /\ female = {}

INITIALISATION
  male := {} ||
  female := {}


OPERATIONS
  born(nn, ss) =
    PRE
      nn : PERSON &
      nn /: (male \/ female) &
      ss : SEX
    THEN
      IF ss = boy
      THEN male := male \/ {nn}
      ELSE female := female \/ {nn}
      END
  END;

  die(nn) = 
    PRE
      nn : PERSON &
      nn : male \/ female
    THEN
      IF nn : male
      THEN male := male - {nn}
      ELSE female := female - {nn}
      END   
  END
END

