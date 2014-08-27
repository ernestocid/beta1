MACHINE AssertStmt

SETS
 ID={aa,bb,cc,dd}

VARIABLES xx

INVARIANT
 xx <: ID

INITIALISATION ANY zz WHERE zz <: ID & card(zz) > 0 THEN xx := zz END

OPERATIONS
  op(yy) = 
    PRE
      yy : ID 
    THEN 
      ASSERT xx /= {} & yy : xx
      THEN skip
      END 
    END
END

