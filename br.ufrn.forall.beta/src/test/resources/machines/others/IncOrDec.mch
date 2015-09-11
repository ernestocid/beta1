MACHINE IncOrDec

VARIABLES count

INVARIANT
 count : 1..100

INITIALISATION count := 1

OPERATIONS
  inc = 
    PRE count < 100 
    THEN count := count + 1 
    END;

  dec =
    PRE count > 1
    THEN count := count - 1
    END
END

