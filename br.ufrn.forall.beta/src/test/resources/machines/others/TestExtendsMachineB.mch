MACHINE TestExtendsMachineB

EXTENDS TestExtendsMachineA

SETS
 SETB={cc,dd}

CONSTANTS constantB

PROPERTIES
 constantB : SETB

OPERATIONS
  opB(yy) = PRE yy : SETB THEN skip END
END

