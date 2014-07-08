MACHINE PredicateTest

OPERATIONS
  test_predicate(aa, bb, cc, dd, ee, ff, gg, hh, ii, jj, kk, ll, mm) = 
    PRE
      aa : INT &
      (bb > 10 or cc >= 10) &
      dd = 20 &
      (ee < 10 => ff <= 20) &
      gg <: NAT &
      hh <<: NAT1 &
      not(ii < 20) &
      jj /: {1, 2, 3} &
      kk /= 1 &
      ll /<: NAT &
      mm /<<: NAT1
    THEN skip 
    END
END

