MACHINE NestedIF

OPERATIONS
  aa <-- test(xx, yy) = 
    PRE xx : INT &
        yy : INT &
        aa : BOOL
    THEN
      IF yy > 0
      THEN IF xx > 1
           THEN IF xx = 2
                THEN aa := TRUE
                ELSE aa := FALSE
                END
           ELSE aa := FALSE
           END
      ELSE aa := FALSE
      END
    END
END

