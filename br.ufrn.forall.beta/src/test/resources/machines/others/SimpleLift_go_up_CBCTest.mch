MACHINE SimpleLiftCBCTest

INCLUDES SimpleLift

PROMOTES go_down, go_to, query_floor

OPERATIONS
go_up_test1 =
PRE
not(floor < 5) & floor : 1..5
THEN skip
END

END