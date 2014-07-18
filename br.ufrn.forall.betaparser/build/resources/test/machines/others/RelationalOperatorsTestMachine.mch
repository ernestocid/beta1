MACHINE
    RelationalOperators

ABSTRACT_VARIABLES
    var1,
    var2,
    var3

INVARIANT
    var1 : NAT &
    var2 : NAT &
    var3 : NAT

INITIALISATION
    var1 := 0 ||
    var2 := 0 ||
    var3 := 0

OPERATIONS
    
    test_operation =
    PRE
        var1 = var2 &
        var2 /= var3 &
        var1 >= var3 & 
        var2 <= var3 &
        var1 > var3 &
        var2 < var3
    THEN
    	test_variable := 11
    END

END
