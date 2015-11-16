MACHINE
    swap
VARIABLES
    v1, v2
INVARIANT
    v1 : INT & v2 : INT
INITIALISATION
    v1 :: INT || v2 :: INT
OPERATIONS
    step = v1, v2 := v2, v1;
    
    set(av1, av2) =
    PRE av1: INT & av2: INT THEN
        v1, v2 := av1, av2
    END;
    
    r1, r2 <-- get = r1, r2 := v1, v2
END
