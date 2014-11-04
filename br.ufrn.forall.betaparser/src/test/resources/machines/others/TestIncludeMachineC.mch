MACHINE TestIncludeMachineC

INCLUDES TestIncludeMachineB

SETS
 SETC={ee,ff}

CONSTANTS constantC

PROPERTIES
 constantC : SETC

VARIABLES varC

INVARIANT varC : INT

INITIALISATION varC := 1

OPERATIONS
  opC(yy) = PRE yy = varD THEN skip END
END

