MACHINE Simple
VARIABLES xx , yy
INVARIANT
 xx: 0..10 & yy:0..10
INITIALISATION xx,yy := 2,3
OPERATIONS
  Either10 = SELECT xx=10 or yy=10 THEN skip END;
  All10 = SELECT xx=10 & yy=10 THEN skip END;
  BothEIther10or0 = SELECT (xx=10 & yy=10) or (xx=0 & yy=0) THEN skip END
END

