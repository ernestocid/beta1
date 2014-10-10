MACHINE RelationalOperations

VARIABLES xx, yy

INVARIANT xx : NAT & yy : NAT

INITIALISATION xx := 1 || yy := 1

OPERATIONS
  equal = PRE xx = yy THEN skip END;

  notequal = PRE xx /= yy THEN skip END;

  greater = PRE xx > yy THEN skip END;

  greaterEqual = PRE xx >= yy THEN skip END;

  less = PRE xx < yy THEN skip END;

  lessEqual = PRE xx <= yy THEN skip END
END

