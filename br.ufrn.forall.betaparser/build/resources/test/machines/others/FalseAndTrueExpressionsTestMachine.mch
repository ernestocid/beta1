MACHINE
    Test

ABSTRACT_VARIABLES
    test_variable,
    another_test_variable

INVARIANT
    test_variable : BOOL &
    another_test_variable : BOOL

INITIALISATION
    test_variable := 0 ||
    another_test_variable := 0

OPERATIONS
    
    test_operation =
    PRE
        test_variable = TRUE or another_test_variable = FALSE
    THEN
    	test_variable := 11
    END

END
