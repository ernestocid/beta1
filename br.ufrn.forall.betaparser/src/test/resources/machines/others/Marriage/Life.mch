MACHINE Life
SETS
  person = {p1,p2,p3,p4,p5,p6,p7,p8,p9,p10};
  SEX = {boy, girl}

VARIABLES male, female

INVARIANT
  male <: person &
  female <: person &
  male /\ female = {}

INITIALISATION
  male := {} ||
  female := {}


OPERATIONS
  born(nn, ss) =
    PRE
      nn : person &
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
      nn : person &
      nn : male \/ female
    THEN
      IF nn : male
      THEN male := male - {nn}
      ELSE female := female - {nn}
      END   
  END
END

