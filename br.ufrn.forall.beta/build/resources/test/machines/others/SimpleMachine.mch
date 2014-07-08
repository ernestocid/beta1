MACHINE SimpleMachine

OPERATIONS
  rr <-- sum(xx, yy) =
  PRE xx : NAT & yy : NAT & rr : NAT 
  THEN rr := xx + yy
  END;

  do_nothing(zz) =
  PRE zz <<: NAT1
  THEN skip
  END
END

