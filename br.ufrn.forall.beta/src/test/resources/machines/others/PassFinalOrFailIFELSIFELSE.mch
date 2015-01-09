MACHINE PassFinalOrFailIFELSIFELSE

SETS RESULT = {pass, final, fail}

OPERATIONS
  result <-- checkResult(averageGrade) = 
    PRE 
      averageGrade : 0..5 &
      averageGrade : INT 
    THEN 
      IF averageGrade >= 4 THEN result := pass
      ELSIF averageGrade >= 2 & averageGrade < 4 THEN result := final
      ELSE result := fail END 
    END
END