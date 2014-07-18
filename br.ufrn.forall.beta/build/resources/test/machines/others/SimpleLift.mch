MACHINE SimpleLift

VARIABLES floor

INVARIANT
 floor : 1..5

INITIALISATION
 floor := 1
OPERATIONS
  go_up =
    PRE floor < 5
    THEN floor := floor + 1
    END;
  go_down = 
    PRE floor > 1
    THEN floor := floor - 1
    END;
  go_to(nFloor) =
    PRE
      nFloor : 1..5 &
      nFloor /= floor
    THEN
      floor := nFloor
    END;
  ff <-- query_floor =
    BEGIN ff := floor
    END
END

