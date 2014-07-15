MACHINE NonRelatedStateVariables

VARIABLES xx, yy

INVARIANT
  xx : 1..5 &
  yy : 6..10

INITIALISATION xx := 1 || yy := 6

OPERATIONS
  inc_xx =
    PRE
      xx < 5
    THEN
      xx := xx + 1
    END;
  inc_yy =
    PRE
      yy < 10
    THEN
      yy := yy + 1
    END;
  inc_both = BEGIN xx := xx + 1 || yy := yy + 1 END;
  inc_both2 =
    PRE
      xx < 5
    THEN
      xx := xx + 1
    END
END

