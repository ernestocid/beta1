MACHINE
    RelationalOperators

ABSTRACT_VARIABLES
    var1,
    var2

INVARIANT
    var1 : NAT &
    var2 : NAT

INITIALISATION
    var1 := 0 ||
    var2 := 0

OPERATIONS
    
    test_operation =
    PRE
        var1 : ASET &
        var2 /: ASET
    THEN
    	var1 := 1
    END

END
