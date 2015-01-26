MACHINE counterCBCTest

INCLUDES counter

PROMOTES get, zero

OPERATIONS
inc_test1 =
PRE
0 <= value & value <= MAXINT & value < MAXINT & overflow : BOOL & value : INT & ((overflow = TRUE) => (value = MAXINT))
THEN skip
END

END