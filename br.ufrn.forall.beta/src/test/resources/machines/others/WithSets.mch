MACHINE WithSets

VARIABLES temp

INVARIANT temp <: NAT1

INITIALISATION temp := {1, 2, 3}

OPERATIONS
  test =
    PRE  
      temp = {1, 2, 3}
    THEN 
      skip
    END
END
