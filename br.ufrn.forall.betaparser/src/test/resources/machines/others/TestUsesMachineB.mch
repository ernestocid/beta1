MACHINE TestUsesMachineB

USES TestUsesMachineA

SETS SETB = {cc,dd}

CONSTANTS constantB

PROPERTIES constantB : SETB

VARIABLES varB

INVARIANT varB : INT

INITIALISATION varB := 1

OPERATIONS
  opB(yy) = PRE yy : SETB THEN skip END
END