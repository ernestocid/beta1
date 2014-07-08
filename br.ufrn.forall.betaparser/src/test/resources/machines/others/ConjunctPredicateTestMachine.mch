MACHINE
    Test

ABSTRACT_VARIABLES
    test_variable,
    another_test_variable

INVARIANT
    test_variable : NAT &
    another_test_variable : NAT

INITIALISATION
    test_variable := 0 ||
    another_test_variable := 0

OPERATIONS
    
    test_operation =
    PRE
        test_variable = 10 & test_variable = another_test_variable
    THEN
    	test_variable := 11
    END

END
