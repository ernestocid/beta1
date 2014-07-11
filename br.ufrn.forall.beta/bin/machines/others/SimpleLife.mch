MACHINE SimpleLife
SETS
  PERSON

VARIABLES born

INVARIANT
  born <: PERSON

INITIALISATION
  born := {}

OPERATIONS
  newborn(pp) =
    PRE
      pp : PERSON
    THEN
      born := born \/ {pp}
  END
END

