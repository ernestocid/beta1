MACHINE SimpleDeath

USES SimpleLife

VARIABLES dead

INVARIANT
 dead <: PERSON

INITIALISATION dead := {}

OPERATIONS
  newdeath(pp) = 
    PRE pp : born 
    THEN dead := dead \/ {pp}
    END
END

