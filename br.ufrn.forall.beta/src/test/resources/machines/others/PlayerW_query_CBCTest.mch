MACHINE PlayerWCBCTest

INCLUDES PlayerW

PROMOTES add_player, substitute

OPERATIONS
query_test =
PRE
card(team) = 11
THEN skip
END

END