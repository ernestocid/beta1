MACHINE
    RelationalOperators

ABSTRACT_VARIABLES
    var1,
    var2

INVARIANT
    var1 : BOOL &
    var2 : BOOL

INITIALISATION
    var1 := FALSE ||
    var2 := FALSE

OPERATIONS
    
    test_operation =
    PRE
        var1 = TRUE => var2 = FALSE
    THEN
    	var2 := TRUE
    END

END
