MACHINE SimplePredicates

VARIABLES xx, yy, ww

INVARIANT xx : NAT & yy : NAT & ww <: NAT

INITIALISATION xx := 1 || yy := 1 || ww := {}

OPERATIONS

  notPredicate = PRE not(xx = yy) THEN skip END;
  
  implicationPredicate = PRE xx : NATURAL & yy : NATURAL => xx + yy : NATURAL THEN skip END;

  equivalencePredicate = PRE xx : NAT <=> xx + 1 : NAT THEN skip END;

  universalQuantiferPredicate = PRE !zz.(zz : NAT => zz + 1 : NAT) THEN skip END;

  belongsPredicate(aa) = PRE aa : ww  THEN skip END;

  notBelongsPredicate(aa) = PRE aa /: ww  THEN skip END;

  includesPredicate(aa) = PRE aa <: ww  THEN skip END;

  notIncludesPredicate(aa) = PRE aa /<: ww  THEN skip END;

  includesStrictPredicate(aa) = PRE aa <<: ww  THEN skip END;

  notIncludesStrictPredicate(aa) = PRE aa /<<: ww  THEN skip END;

  existentialQuantifierPredicate = PRE #zz.(zz : NAT & zz > xx) THEN skip END;

  memberOfExpressionThatResultsInASet(aa) = PRE aa : ww \/ {3,4,5} THEN skip END

END
