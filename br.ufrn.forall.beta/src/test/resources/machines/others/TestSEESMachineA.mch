MACHINE TestSEESMachineA

SETS SETA = {aa,bb}; ID

CONSTANTS constantA

PROPERTIES constantA : SETA

OPERATIONS
  Set(yy) = PRE yy : SETA THEN skip END
END

