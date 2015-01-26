MACHINE PassFinalOrFailIFELSIFELSECBCTest

INCLUDES PassFinalOrFailIFELSIFELSE



OPERATIONS
checkResult_test1(averageGrade) =
PRE
averageGrade : 0..5 & not(averageGrade >= 4) & averageGrade : INT
THEN skip
END

END