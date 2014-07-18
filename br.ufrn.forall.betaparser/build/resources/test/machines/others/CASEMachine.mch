MACHINE CASEMachine

VARIABLES var1

INVARIANT
 var1 : NAT

INITIALISATION
 var1 := 0

OPERATIONS
  operation(var2) = 
  PRE 
    var2 : NAT
  THEN
    CASE var2 OF
    EITHER 0 THEN skip
    ELSE skip
    END END
  END
END
