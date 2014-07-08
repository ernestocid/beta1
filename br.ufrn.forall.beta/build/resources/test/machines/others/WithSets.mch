MACHINE WithSets

VARIABLES temp

INVARIANT temp <: NAT1

INITIALISATION temp := {}

OPERATIONS
  test =
    PRE  
      temp = {1, 2, 3}
    THEN 
      skip
    END
END
