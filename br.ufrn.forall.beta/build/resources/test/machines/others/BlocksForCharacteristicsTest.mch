MACHINE BlocksForCharacteristicsTest

SETS NAME; awnser = {yes, no}

OPERATIONS
  member_of(aa, bb, cc, dd, ee, ff, gg, hh, ii, jj, kk, ll, mm, nn) = 
    PRE 
      aa : NAT &
      bb : NAT1 &
      cc : INT &
      dd : BOOL &
      ee : {1,2,3} &
      ff : 1..5 &
      gg : NAME &
      hh : awnser &
      ii : STRING &
      jj : NAT --> NAT &
      kk : NAT >-> NAT &
      ll : NAT -->> NAT &
      mm : NAT >->> NAT &
      nn + 2 : {1, 2} \/ {3, 4}
    THEN skip 
    END;

  not_member_of(aa, bb, cc, dd, ee, ff, gg, hh) = 
    PRE 
      aa /: NAT &
      bb /: NAT1 &
      cc /: INT &
      dd /: BOOL &
      ee /: {1,2,3} &
      ff /: 1..5 &
      gg /: NAME &
      hh /: awnser &
      1 + 1 /: {0,1,3}
    THEN skip 
    END;

  subset(aa, bb, cc, dd, ee, ff, gg, hh, ii) = 
    PRE 
      aa <: NAT &
      bb <: NAT1 &
      cc <: INT &
      dd <: BOOL &
      ee <: {1,2,3} &
      ff <: 1..5 &
      gg <: NAME &
      hh <: awnser &
      ii \/ {2} <: {1, 2} \/ {3, 4}
    THEN skip 
    END;

  not_subset(aa, bb, cc, dd, ee, ff, gg, hh, ii) = 
    PRE 
      aa /<: NAT &
      bb /<: NAT1 &
      cc /<: INT &
      dd /<: BOOL &
      ee /<: {1,2,3} &
      ff /<: 1..5 &
      gg /<: NAME &
      hh /<: awnser  &
      ii \/ {5} /<: {1, 2} \/ {3, 4}
    THEN skip 
    END;

  subset_strict(aa, bb, cc, dd, ee, ff, gg, hh, ii) = 
    PRE 
      aa <<: NAT &
      bb <<: NAT1 &
      cc <<: INT &
      dd <<: BOOL &
      ee <<: {1,2,3} &
      ff <<: 1..5 &
      gg <<: NAME &
      hh <<: awnser &
      ii \/ {5} <<: {1, 2} \/ {3, 4}
    THEN skip 
    END;

  not_subset_strict(aa, bb, cc, dd, ee, ff, gg, hh, ii) = 
    PRE 
      aa /<<: NAT &
      bb /<<: NAT1 &
      cc /<<: INT &
      dd /<<: BOOL &
      ee /<<: {1,2,3} &
      ff /<<: 1..5 &
      gg /<<: NAME &
      hh /<<: awnser &
      ii \/ {5} /<<: {1, 2} \/ {3, 4}
    THEN skip 
    END;

  relational(aa, bb, cc, dd, ee, ff, gg, hh, ii, jj, kk, ll) = 
    PRE
      aa > bb &
      cc < dd &
      ee >= ff &
      gg <= hh &
      ii : NAT &
      jj : NAT &
      ii = jj &
      kk : NAT &
      ll : NAT &
      kk /= ll
    THEN skip 
    END;

  negation(aa, bb) =
    PRE
      aa : NAT &
      bb : NAT &
      not(bb : {1,2,3})
    THEN skip
    END;

  implication(aa, bb) =
    PRE
      aa = 10 => bb > 5
    THEN
      skip
    END;

  forall(aa, bb) =
    PRE
      !(xx) . (xx : NAT => aa = xx & bb = xx + 1)
    THEN
      skip
    END;

  exists(aa) =
    PRE
      #(xx) . (xx : NAT => xx > aa)
    THEN
      skip
    END;

  iff(aa, bb) =
    PRE
      (aa = 1 <=> bb = 0)
    THEN
      skip
    END;
 
  equals(aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) =
    PRE
      aa = NAME &
      bb = 1 &
      cc = TRUE &
      dd = NAME * {0} &
      ee = {(0 |-> FALSE), (1 |-> FALSE)} &
      ff = 1..5 &
      gg = NAT &
      hh = NAT1 &
      ii = INT &
      jj = BOOL
    THEN
      skip
    END;

  not_equals(aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) =
    PRE
      aa /= NAME &
      bb /= 1 &
      cc /= TRUE &
      dd /= NAME * {0} &
      ee /= {(0 |-> FALSE), (1 |-> FALSE)} &
      ff /= 1..5 &
      gg /= NAT &
      hh /= NAT1 &
      ii /= INT &
      jj /= BOOL
    THEN
      skip
    END;

  disjuction(aa, bb, cc) = 
    PRE
      aa : BOOL &
      bb : BOOL &
      cc : NAT &
      aa = TRUE or bb = FALSE or cc = 10
    THEN
      skip
    END
END

