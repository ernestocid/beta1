MACHINE SELECTMachine

VARIABLES var1

INVARIANT
 var1 : NAT1

INITIALISATION
 var1 := 1

OPERATIONS
  operation(var2) = 
  PRE 
    var2 : NAT1
  THEN
    SELECT var1 > var2 THEN skip
    WHEN var1 < var2 THEN skip
    WHEN var1 = var2 THEN skip
    ELSE skip
    END
  END
END


