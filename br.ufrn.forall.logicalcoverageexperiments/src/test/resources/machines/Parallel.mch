MACHINE Parallel

SETS
 ID={aa,bb,cc,dd}

VARIABLES xx, yy

INVARIANT
 xx <: ID &
 yy <: ID

INITIALISATION xx := {} || yy := {}

OPERATIONS
  rr1, rr2, rr3 <-- op(zz) = 
    PRE zz : ID 
    THEN
	 ANY tt WHERE tt <: ID & zz : tt THEN rr1 := tt END ||
         ANY tt WHERE tt <: ID & zz /: tt THEN rr2 := tt END ||
         ANY tt WHERE tt <: ID & card(tt) > 2 THEN rr3 := tt END
    END
END

