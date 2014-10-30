MACHINE TypingPredicates

SETS ASET

VARIABLES xx, yy, ww

INVARIANT xx : NAT & yy : NAT & ww <: NAT

INITIALISATION xx := 1 || yy := 1 || ww := {1,2,3}

OPERATIONS

  belongsToNAT(aa) = PRE aa : NAT THEN skip END;

  belongsToNAT1(aa) = PRE aa : NAT1 THEN skip END;

  belongsToINT(aa) = PRE aa : INT THEN skip END;

  belongsToBOOL(aa) = PRE aa : BOOL THEN skip END;

  belongsToRange(aa) = PRE aa : 1..5 THEN skip END;

  belongsToAbstractSet(aa) = PRE aa : ASET THEN skip END;

  belongsToId(aa) = PRE aa : ww THEN skip END;

  totalFunction(aa) = PRE aa : NAT --> NAT THEN skip END;

  partialFunction(aa) = PRE aa : NAT +-> NAT THEN skip END;

  injectiveFunction(aa) = PRE aa : NAT >+> NAT THEN skip END;

  totalSurjectiveFunction(aa) = PRE aa : NAT -->> NAT THEN skip END;

  partialSurjectiveFunction(aa) = PRE aa : NAT +->> NAT THEN skip END;

  bijectiveFunction(aa) = PRE aa : NAT >->> NAT THEN skip END;

  memberOfEnumeratedSet(aa) = PRE aa : {1,2,3} THEN skip END;

  belongsToSTRING(aa) = PRE aa : STRING THEN skip END

END



