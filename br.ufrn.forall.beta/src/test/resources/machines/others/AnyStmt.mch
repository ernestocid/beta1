MACHINE AnyStmt

VARIABLES xx

INVARIANT xx : NAT

INITIALISATION xx := 0

OPERATIONS
  add_a_random_value = 
    ANY yy 
    WHERE yy : NAT & yy : 1..20 
    THEN xx := xx + yy 
    END
END

