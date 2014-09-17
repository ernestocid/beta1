MACHINE NestedSubstitutions

OPERATIONS
    op1(xx, yy) = 
    PRE 
      xx : NAT & 
      yy : NAT 
    THEN 
      SELECT xx /= yy THEN
        IF xx > yy THEN
          skip
        ELSIF xx = 2 THEN 
          IF yy = 1 THEN skip END
        ELSIF yy = 2 THEN skip
        ELSE skip END
      END
    END
END

