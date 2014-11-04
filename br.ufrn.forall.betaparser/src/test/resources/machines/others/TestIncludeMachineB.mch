MACHINE TestIncludeMachineB

INCLUDES TestIncludeMachineA

EXTENDS TestIncludeMachineD

SETS
 SETB={cc,dd}

CONSTANTS constantB

PROPERTIES
 constantB : SETB

VARIABLES varB

INVARIANT varB : INT

INITIALISATION varB := 1

OPERATIONS
  opB(yy) = PRE yy : SETB THEN skip END
END

