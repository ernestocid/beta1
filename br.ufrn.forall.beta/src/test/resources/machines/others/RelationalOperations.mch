MACHINE RelationalOperations

OPERATIONS
  equal(xx, yy) = PRE xx : NAT & yy : NAT & xx = yy THEN skip END;

  notequal(xx, yy) = PRE xx : NAT & yy : NAT & xx /= yy THEN skip END;

  greater(xx, yy) = PRE xx : NAT & yy : NAT & xx > yy THEN skip END;

  greaterEqual(xx, yy) = PRE xx : NAT & yy : NAT & xx >= yy THEN skip END;

  less(xx, yy) = PRE xx : NAT & yy : NAT & xx < yy THEN skip END;

  lessEqual(xx, yy) = PRE xx : NAT & yy : NAT & xx <= yy THEN skip END
END

