MACHINE NoStateCBCTest

INCLUDES NoState



OPERATIONS
Set_test1(xx, yy) =
PRE
yy : NAT & yy > xx & xx : NAT
THEN skip
END

END