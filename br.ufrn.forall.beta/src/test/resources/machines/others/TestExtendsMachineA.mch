MACHINE TestExtendsMachineA
SETS
 SETA={aa,bb}

CONSTANTS constantA

PROPERTIES
 constantA : SETA

OPERATIONS
  opA(yy) = PRE yy : SETA THEN skip END
END

