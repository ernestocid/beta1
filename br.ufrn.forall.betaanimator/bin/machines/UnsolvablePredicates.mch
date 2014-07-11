MACHINE UnsolvablePredicates

OPERATIONS
  op0(aa, bb) = 
    PRE 
      aa : NAT &
      bb : NAT &
      aa > bb &
      bb > aa 
    THEN 
      skip 
    END;

  op1(aa, bb) = 
    PRE 
      aa : NAT &
      bb : NAT
    THEN 
      skip 
    END;

  op2(aa) = 
    PRE 
      aa > 10 &
      aa < 5 
    THEN 
      skip 
    END
END

