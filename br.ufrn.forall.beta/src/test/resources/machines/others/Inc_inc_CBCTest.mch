MACHINE IncCBCTest

INCLUDES Inc



OPERATIONS
inc_test1(nn) =
PRE
not(nn : NAT) & not((nn + value) <= MAXINT) & value < MAXINT & value : INT
THEN skip
END

END