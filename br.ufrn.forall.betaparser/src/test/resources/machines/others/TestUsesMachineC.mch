MACHINE TestUsesMachineC

USES TestUsesMachineB

SETS SETC = {ee,ff}

CONSTANTS constantC

PROPERTIES constantC : SETC

VARIABLES varC

INVARIANT varC : INT

INITIALISATION varC := 1

OPERATIONS
  opC(yy) = PRE yy = varC THEN skip END
END
