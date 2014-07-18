MACHINE Inc

VARIABLES value

INVARIANT
  value : INT

INITIALISATION
  value := 0
OPERATIONS
  inc(nn) = 
    PRE
      nn : NAT &
      (nn + value) <= MAXINT &
      value < MAXINT 
    THEN 
      value := value + nn 
    END
END

