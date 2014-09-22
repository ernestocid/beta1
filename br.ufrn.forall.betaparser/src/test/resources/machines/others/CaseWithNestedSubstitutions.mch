MACHINE CaseWithNestedSubstitutions

VARIABLES aa, bb

INVARIANT
  aa : NAT &
  bb : NAT

INITIALISATION
  aa := 0 || bb := 0

OPERATIONS
  
  op1(xx) = 
  PRE xx : NAT 
  THEN 
    CASE xx OF
    EITHER 1 THEN IF aa > bb THEN skip END
    OR 2 THEN IF bb >= aa THEN skip END
    ELSE skip END
    END
  END

END

