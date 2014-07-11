MACHINE IFMachine

VARIABLES var1

INVARIANT
 var1 : NAT1

INITIALISATION
 var1 := 10

OPERATIONS
  operation(var2) = 
  PRE 
    var2 : NAT1
  THEN
    IF var1 = var2
    THEN skip
    END
  END
END

