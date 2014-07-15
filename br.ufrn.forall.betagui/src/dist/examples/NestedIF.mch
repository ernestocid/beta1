MACHINE NestedIF

OPERATIONS
  aa <-- test(xx) = 
    PRE xx : INT &
        aa : BOOL
    THEN
      IF xx > 0
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

