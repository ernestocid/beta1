MACHINE SelectStmt

SETS
  ID={aa,bb,cc}

VARIABLES xx

INVARIANT
  xx <: ID

INITIALISATION 
  ANY zz WHERE zz <: ID & card(zz) = 2 THEN xx := zz END

OPERATIONS
  rr <-- op(yy) = 
    PRE 
      yy : ID 
    THEN 
      SELECT xx = {} THEN rr := 1
      WHEN xx /= {} & yy : xx THEN rr := 2
      WHEN xx /= {} & yy /: xx THEN rr := 3
      ELSE rr := 4 END
    END
END

