MACHINE ExpressionsTest

SETS PEOPLE

OPERATIONS
  test(aa, bb, cc, dd, ee, ff, gg, hh, ii, jj, kk, ll, mm, nn, oo, pp, qq, rr, ss, ss1, ss2, ss3, ss4, ss5, ss6, ss7, tt) = 
    PRE 
      aa + bb = cc &
      bb = cc - aa &
      dd : BOOL &
      ee : BOOL &
      dd = TRUE &
      ee = FALSE &
      ff <: NAT &
      card(ff) = 2 &
      gg : ff --> BOOL &
      dom(gg) = {0,1} &
      hh <: NAT1 &
      hh = {} &
      gg(0) = TRUE &
      ran(gg) = {TRUE, FALSE} &
      ff /\ hh = {} &
      ii : 1..10 &
      jj : INT &
      jj < MAXINT &
      jj > aa * bb &
      kk : STRING &
      ff \/ hh /= {} &
      ll : hh +-> BOOL &
      mm : hh >+> BOOL &
      nn : POW(PEOPLE) &
      oo : ff -->> BOOL &
      pp : ff >-> BOOL &
      qq : ff >+>> BOOL &
      rr : ff >->> BOOL &
      ss : NAT <-> NAT &
      ss1 : {1} <<| ss &
      ss2 : ss |>> {1} &
      ss3 : {1} <| ss &
      ss4 : ss |> {1} &
      ss5 : ss - {1|->2} &
      ss6 : rr~ &      
      ss7 : ss <+ {5 |-> 5} &
      tt : -1..3 &
      uu : NAT --> iseq(nn) &
      vv = [] &
      xx = first(uu) &
      zz : union(uu) &
      yy = prj1(NAT, PEOPLE) &
      ww = prj2(NAT, PEOPLE) &
      aa1 = (NAT; PEOPLE) &
      aa2 = {vv | vv : NAT} &
      aa3 : (NAT --> seq(nn)) &
      aa4 = last(uu) &
      aa5 = size(vv) &
      aa6 = front(vv) &
      aa7 = tail(vv) &
      aa8 = rev(vv) &
      aa9 = 4/2 &
      aa10 = 10 mod 2
    THEN skip 
    END

END

