MACHINE SimpleLiftCBCTest

INCLUDES SimpleLift

PROMOTES go_down, go_up, query_floor

OPERATIONS
go_to_test1(nFloor) =
PRE
nFloor : MININT..((1 - 1)) & floor : 1..5 & nFloor /= floor
THEN skip
END

END