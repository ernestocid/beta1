MACHINE Locks

INCLUDES SimpleDoors

PROMOTES closedoor

SETS
  STATUS = {locked, unlocked}

VARIABLES
  status

INVARIANT
  status : DOOR --> STATUS

INITIALISATION
  status := DOOR * {locked}

OPERATIONS

  opendoor(dd) =
    PRE status(dd) = unlocked
    THEN opening(dd)
    END;

  unlockdoor(dd) =
    PRE dd : DOOR
    THEN status(dd) := unlocked
    END;

  lockdoor(dd) = 
    PRE position(dd) = closed
    THEN status(dd) := locked
    END
END