MACHINE OperationWithReturnMachine

OPERATIONS
  returnme(yy) = 
    PRE yy : NAT
    THEN skip
  END;

  xx <-- returnme1(yy) = 
    PRE yy : NAT
    THEN xx := yy
  END;

  aa, bb, cc <-- returnme2(yy) = 
    PRE yy : NAT
    THEN aa := yy || bb := yy + 1 || cc := yy + 2
  END
END