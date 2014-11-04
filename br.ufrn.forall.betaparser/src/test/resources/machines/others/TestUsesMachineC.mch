MACHINE TestUsesMachineC

USES TestUsesMachineB

SETS SETC = {ee,ff}

CONSTANTS constantC

PROPERTIES constantC : SETC

OPERATIONS
  opC(yy) = PRE yy : SETC THEN skip END
END
